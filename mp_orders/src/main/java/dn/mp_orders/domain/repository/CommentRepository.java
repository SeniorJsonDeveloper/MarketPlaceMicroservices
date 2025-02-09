package dn.mp_orders.domain.repository;

import dn.mp_orders.domain.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository  extends JpaRepository<CommentEntity,String> {


}
