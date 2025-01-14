package dn.mp_orders.domain.repository;
import dn.mp_orders.domain.entity.CommentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;

@Repository
public interface CommentRepository  extends CrudRepository<CommentEntity,String> {

    Set<CommentEntity> findAllById(String orderId);

    List<CommentEntity> findAllByOrderId(String orderId);
}
