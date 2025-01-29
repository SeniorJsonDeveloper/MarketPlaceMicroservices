package dn.mp_notifications.api.controller;
import dn.mp_notifications.api.dto.MessageDto;
import dn.mp_notifications.api.dto.NotificationDto;
import dn.mp_notifications.domain.entity.Notification;
import dn.mp_notifications.domain.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final SenderService service;

    @GetMapping("/page/")
    @ResponseStatus(HttpStatus.OK)

    public Page<NotificationDto> getNotifications(@RequestParam(required = false) int pageNumber,
                                                  @RequestParam(required = false) int pageSize ) {
        return service.getPagedData(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NotificationDto getNotificationById(@PathVariable String id) {
        return service.findNotificationById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Notification> getNotificationList() {
        return service.findAllNotifications();
    }

    @PostMapping("/send/")
    public NotificationDto sendNotification(@RequestBody MessageDto messageDto,
                                            @RequestParam String orderId) {
        return service.sendNotification(messageDto, orderId);
    }



}
