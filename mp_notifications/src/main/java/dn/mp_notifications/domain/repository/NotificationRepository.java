package dn.mp_notifications.domain.repository;

import dn.mp_notifications.domain.entity.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, String> {
}
