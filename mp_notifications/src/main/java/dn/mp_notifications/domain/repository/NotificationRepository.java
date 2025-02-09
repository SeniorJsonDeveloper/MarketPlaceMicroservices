package dn.mp_notifications.domain.repository;

import dn.mp_notifications.domain.entity.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotificationRepository extends CrudRepository<NotificationEntity, String> {

    Page<NotificationEntity> findWithPage(Pageable pageable);

}
