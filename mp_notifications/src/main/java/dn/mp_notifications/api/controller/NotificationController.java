package dn.mp_notifications.api.controller;

import dn.mp_notifications.domain.entity.Notification;

import dn.mp_notifications.domain.entity.NotificationDto;
import dn.mp_notifications.domain.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final SenderService service;

    @GetMapping()
    public Iterable<Notification> getNotifications() {
        return service.findAllNotifications();
    }

    @GetMapping("/{id}")
    public NotificationDto getNotificationById(@PathVariable String id) {
        return service.findNotificationById(id);
    }



}
