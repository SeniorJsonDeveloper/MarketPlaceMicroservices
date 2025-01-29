package dn.mp_notifications.domain.repository;

import dn.mp_notifications.domain.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotificationRepository extends CrudRepository<Notification, String> {

    Page<Notification> findWithPage(Pageable pageable);

}
