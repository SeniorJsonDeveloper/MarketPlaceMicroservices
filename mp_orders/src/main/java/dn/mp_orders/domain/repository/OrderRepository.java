package dn.mp_orders.domain.repository;
import dn.mp_orders.domain.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, String> {

    List<OrderEntity> findAllByUserId(String userId);

    List<OrderEntity> findAllByStatus(String status);

    List<OrderEntity> findAllByStatusAndUserId(String status, String userId);

    @Override
    List<OrderEntity> findAll();
}
