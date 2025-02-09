package dn.mp_notifications.domain.service.impl;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import dn.mp_notifications.api.dto.EmailDto;
import dn.mp_notifications.api.dto.NotificationDto;
import dn.mp_notifications.api.dto.mapper.NotificationMapper;
import dn.mp_notifications.api.exception.NotFoundException;
import dn.mp_notifications.domain.configuration.EmailConfig;
import dn.mp_notifications.domain.entity.NotificationEntity;
import dn.mp_notifications.domain.event.MessageEvent;
import dn.mp_notifications.domain.repository.NotificationRepository;
import dn.mp_notifications.domain.service.SenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements SenderService{

    private static final String DELIVERED = "ДОСТАВЛЕН";

    private static final String ON_THE_WAY = "В ПУТИ";

    private static final String PROCESSING = "В ОБРАБОТКЕ";

    private final OkHttpClient okHttpClient;

    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    private final EmailConfig emailConfig;



    @Value("${integration.twilio.sid}")
    private String accountSid;

    @Value("${integration.twilio.token}")
    private String authToken;

    @Value("${integration.twilio.number}")
    private String number;



    @Override
    public List<NotificationEntity> findAllNotifications() {
        return (List<NotificationEntity>) notificationRepository.findAll();
    }

    @Override
    public Page<NotificationEntity> findAllNotificationsWithPaging(NotificationDto notificationDto) {
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
        List<NotificationEntity> notificationEntities = listRange.stream()
                .map(e->(NotificationEntity) e)
                .toList();
        var mappedNotifications = notificationMapper.toDtoList(notificationEntities);
        return new PageImpl<>(mappedNotifications, PageRequest.of(pageNumber-1, pageSize), totalElements);
    }

    @Override
    public NotificationDto findNotificationById(String id) {
        return notificationMapper
                .toDto(notificationRepository.findById(id)
                .orElseThrow(()->new NotFoundException
                                (MessageFormat.format("Notification with id: {0} not found",id))));
    }

    @Override
    public void addToList(NotificationEntity notificationEntity) {
        redisTemplate.opsForList().rightPush("notifications", notificationEntity);
    }

    @Override
    public Long getNotificationsCount() {
        var notifications = (List<NotificationEntity>) notificationRepository.findAll();
        log.info("Notifications count: {}", notifications.size());
        return (Long) (long) notifications.size();
    }

    @Override
    public NotificationDto sendNotification(MessageEvent event , String orderId) {
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setId(UUID.randomUUID().toString());
        notificationEntity.setCreatedAt(LocalDateTime.now());
        notificationEntity.setStatus(event.getStatus());
        notificationEntity.setTitle(event.getMessage());
        notificationEntity.setOrderId(orderId);
        notificationRepository.save(notificationEntity);

        EmailDto emailDto = new EmailDto();
        emailDto.setMessage(event.getMessage());
        ExecutorService executorService = Executors.newFixedThreadPool(3);
                executorService.execute(() -> {
                getNotificationsCount();
                emailConfig.sendEmail(emailDto);
                executorService.shutdown();
                log.info("Notification sent successfully: {}", notificationEntity);
            });
                return notificationMapper.toDto(notificationEntity);
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
    public void deleteNotifications(List<NotificationEntity> notificationEntities) {
        List<NotificationEntity> filteredNotificationEntities = findAllNotifications()
                .stream()
                .filter(o -> o != null && o.getStatus().equals(DELIVERED))
                .toList();
        notificationRepository.deleteAll(filteredNotificationEntities);
        log.info("Notifications: {}", filteredNotificationEntities);

    }








}
