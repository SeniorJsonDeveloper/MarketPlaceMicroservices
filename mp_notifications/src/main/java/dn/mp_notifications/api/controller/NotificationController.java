package dn.mp_notifications.api.controller;
import dn.mp_notifications.api.dto.NotificationDto;
import dn.mp_notifications.domain.entity.NotificationEntity;
import dn.mp_notifications.domain.event.MessageEvent;
import dn.mp_notifications.domain.service.SenderService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
@Tag(name = "Notification",description = "Действия с уведомлениями")
public class NotificationController {

    private final SenderService service;

    @GetMapping("/page/")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(description = "Получение списка уведомлений с пагинацией",responseCode = "200")
    public Page<NotificationDto> getNotifications(@RequestParam(required = false) int pageNumber,
                                                  @RequestParam(required = false) int pageSize ) {
        return service.getPagedData(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(description = "Получение уведомления по уникальному идентификатору",responseCode = "200")
    public NotificationDto getNotificationById(@PathVariable Long id) {
        return service.findNotificationById(id);
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(description = "Получение всех уведомлений",responseCode = "200")
    public List<NotificationEntity> getNotificationList() {
        return service.findAllNotifications();
    }

    @PostMapping("/send/")
    @ApiResponse(description = "Отправка уведомления",responseCode = "201")
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationDto sendNotification(@RequestBody MessageEvent messageDto,
                                            @RequestParam String orderId) {
        return service.sendNotification(messageDto, orderId);
    }



}
