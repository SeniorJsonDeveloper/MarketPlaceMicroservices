package dn.mp_notifications.api.controller;

import dn.mp_notifications.api.dto.PageResponseDTO;
import dn.mp_notifications.api.dto.mapper.NotificationMapper;
import dn.mp_notifications.domain.entity.Notification;

import dn.mp_notifications.api.dto.NotificationDto;
import dn.mp_notifications.domain.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final SenderService service;

    @GetMapping("/page/")
    @ResponseStatus(HttpStatus.OK)
    public Page<NotificationDto> getNotifications(@RequestParam int pageNumber,
                                                  @RequestParam int pageSize ) {
        return service.getPagedData(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NotificationDto getNotificationById(@PathVariable String id) {
        return service.findNotificationById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NotificationDto> getNotificationList(){
        return service.getNotificationList();
    }



}
