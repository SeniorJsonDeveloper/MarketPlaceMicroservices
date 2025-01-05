package dn.mp_notifications.domain.repository;

import dn.mp_notifications.api.dto.NotificationDto;
import dn.mp_notifications.api.dto.PageOutDto;
import dn.mp_notifications.domain.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, String> {

    void deleteByStatus(String status);

    Page<Notification> findWithPage(Pageable pageable);
}
