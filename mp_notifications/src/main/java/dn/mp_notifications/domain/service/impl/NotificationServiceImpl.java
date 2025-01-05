package dn.mp_notifications.domain.service.impl;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import dn.mp_notifications.api.dto.MessageDto;
import dn.mp_notifications.api.dto.PageOutDto;
import dn.mp_notifications.api.dto.PageResponseDTO;
import dn.mp_notifications.api.dto.mapper.NotificationMapper;
import dn.mp_notifications.api.exception.NotFoundException;
import dn.mp_notifications.domain.entity.Notification;
import dn.mp_notifications.api.dto.NotificationDto;
import dn.mp_notifications.domain.repository.NotificationRepository;
import dn.mp_notifications.domain.service.SenderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    private final RedisTemplate<String, Object> redisTemplate;



    @Value("${integration.twilio.sid}")
    private String accountSid;

    @Value("${integration.twilio.token}")
    private String authToken;

    @Value("${integration.twilio.number}")
    private String number;


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
    public NotificationDto sendNotification(MessageDto messageDto, String orderId) {
        MessageDto order = new MessageDto();
        order.setId(UUID.randomUUID().toString());
        order.setMessage(messageDto.getMessage());
        order.setName(messageDto.getName());
        order.setSecretCode(generateCode());
        order.setStatus(messageDto.getStatus());
        order.setPrice(BigDecimal.ZERO);
        order.setRating(messageDto.getRating());
        Notification notification = new Notification();
        notification.setId(UUID.randomUUID().toString());
        notification.setMessage(order.getMessage());
        notification.setCreatedAt(LocalDateTime.now());
        notification.setStatus(order.getStatus());
        notification.setTitle(order.getName());
        notification.setOrderId(orderId);
        notificationRepository.save(notification);
        log.info("Notification sent successfully: {}", notification);
        return notificationMapper.mapToDto(notification);
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
    public Page<Notification> findAllNotifications(NotificationDto notificationDto) {
        return notificationRepository.findWithPage(notificationDto.getPageOut().getPageRequest());
    }

    @Override
    public NotificationDto findNotificationById(String id) {
        return notificationMapper.mapToDto(
                notificationRepository.findById(id)
                        .orElseThrow(()->new NotFoundException
                                (MessageFormat.format("Notification with id: {0} not found",id))));
    }

    @Override
    public void deleteNotification(List<Notification> notifications) {


        notifications = (List<Notification>) notificationRepository.findAll();
        var result  =  notifications.stream()
                .filter(notification -> !notification.getMessage()
                .equals("Заказ создан!"))
                .toList();
        log.info("Notifications deleted: {}", result);
        notificationRepository.deleteAll(result);
    }

    @Override
    public void addToList(Notification notification) {
        redisTemplate.opsForList().rightPush("notifications", notification);
    }

    @Override
    @SneakyThrows
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
        var mappedNotifications = notificationMapper.mapToDtoList(notifications);
        return new PageImpl<>(mappedNotifications, PageRequest.of(pageNumber-1, pageSize), totalElements);
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
