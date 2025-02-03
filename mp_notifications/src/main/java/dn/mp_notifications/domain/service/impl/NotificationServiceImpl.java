package dn.mp_notifications.domain.service.impl;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import dn.mp_notifications.api.dto.EmailDto;
import dn.mp_notifications.api.dto.MessageDto;
import dn.mp_notifications.api.exception.NotFoundException;
import dn.mp_notifications.api.dto.mapper.CustomNotificationMapper;
import dn.mp_notifications.domain.configuration.EmailConfig;
import dn.mp_notifications.domain.entity.Notification;
import dn.mp_notifications.api.dto.NotificationDto;
import dn.mp_notifications.domain.repository.NotificationRepository;
import dn.mp_notifications.domain.service.SenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements SenderService{

    private static final String DELIVERED = "ДОСТАВЛЕН";

    private static final String ON_THE_WAY = "В ПУТИ";

    private static final String PROCESSING = "В ОБРАБОТКЕ";


    private final NotificationRepository notificationRepository;

    private final CustomNotificationMapper customNotificationMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    private final EmailConfig emailConfig;



    @Value("${integration.twilio.sid}")
    private String accountSid;

    @Value("${integration.twilio.token}")
    private String authToken;

    @Value("${integration.twilio.number}")
    private String number;



    @Override
    public List<Notification> findAllNotifications() {
        return (List<Notification>) notificationRepository.findAll();
    }

    @Override
    public Page<Notification> findAllNotificationsWithPaging(NotificationDto notificationDto) {
        return notificationRepository.findWithPage(notificationDto.getPageOut().getPageRequest());
    }

    @Override
    public Page<NotificationDto> getPagedData(int pageNumber, int pageSize) {
        long totalElements = redisTemplate.opsForList().size("notifications");
        long startIndex = (long) (pageNumber - 1) * pageSize;
        long endIndex = Math.min(startIndex + pageSize-1, totalElements-1);
        List<Object> listRange = redisTemplate.opsForList()
                .range("notifications",startIndex,endIndex);
        assert listRange != null;
        List<Notification> notifications = listRange.stream()
                .map(e->(Notification) e)
                .toList();
        var mappedNotifications = customNotificationMapper.mapToDtoList(notifications);
        return new PageImpl<>(mappedNotifications, PageRequest.of(pageNumber-1, pageSize), totalElements);
    }

    @Override
    public NotificationDto findNotificationById(String id) {
        return customNotificationMapper
                .mapToDto(notificationRepository.findById(id)
                .orElseThrow(()->new NotFoundException
                                (MessageFormat.format("Notification with id: {0} not found",id))));
    }

    @Override
    public void addToList(Notification notification) {
        redisTemplate.opsForList().rightPush("notifications", notification);
    }

    @Override
    public Long getNotificationsCount() {
        var notifications = (List<Notification>) notificationRepository.findAll();
        log.info("Notifications count: {}", notifications.size());
        return (Long) (long) notifications.size();
    }

    @Override
    public NotificationDto sendNotification(MessageDto messageDto, String orderId) {
        MessageDto message = new MessageDto();
        message.setId(UUID.randomUUID().toString());
        message.setMessage(messageDto.getMessage());
        message.setName(messageDto.getName());
        message.setSecretCode(generateCode());
        message.setStatus(messageDto.getStatus());
        message.setPrice(BigDecimal.ZERO);
        message.setRating(messageDto.getRating());
        EmailDto emailDto = new EmailDto();
        emailDto.setPhoneNumber(message.getName());
        emailDto.setSenderId(message.getName());
        emailDto.setMessage(message.getMessage());
        log.info("Sending notification: {}", message.getMessage());

        Notification notification = new Notification();
        notification.setId(UUID.randomUUID().toString());
        notification.setMessage(message.getMessage());
        notification.setCreatedAt(LocalDateTime.now());
        notification.setStatus(message.getStatus());
        notification.setTitle(message.getName());
        notification.setOrderId(orderId);
        notificationRepository.save(notification);
        
        ExecutorService executorService = Executors.newFixedThreadPool(3);
                executorService.execute(() -> {
                getNotificationsCount();
                emailConfig.sendEmail(emailDto);
                executorService.shutdown();
                log.info("Notification sent successfully: {}", notification);
            });
                return customNotificationMapper.mapToDto(notification);
    }



    @Override
    public String sendSmsCode(String phone) {
        Twilio.init(accountSid, authToken);
        String formattedContact = phone.replaceAll("[( )-]", "");
        String code = generateCode();
        Message.creator(new PhoneNumber(formattedContact),
                new PhoneNumber(number),
                "Ваш секретный код: "+code)
                .create();
        return code;
    }

    @Override
    public String generateCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 6) {
            sb.append(random.nextInt(10));
        }
        sb.insert(3,"");
        return sb.toString();
    }


    @Override
    public void deleteNotifications(List<Notification> notifications) {
        List<Notification> filteredNotifications = findAllNotifications()
                .stream()
                .filter(o -> o != null && o.getStatus().equals(DELIVERED))
                .toList();
        notificationRepository.deleteAll(filteredNotifications);
        log.info("Notifications: {}", filteredNotifications);

    }






    public void sendAsyncNotification(MessageDto messageDto, String orderId) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            CompletableFuture<NotificationDto> future = CompletableFuture.completedFuture(sendNotification(messageDto, orderId));
            future.whenComplete((notificationDto, throwable) -> {
                if (throwable != null) {
                    log.error(throwable.getMessage(), throwable);
                }
                else {
                    log.info("Notification was sent successfully: {}", notificationDto);
                }
            });
        });
    }


}
