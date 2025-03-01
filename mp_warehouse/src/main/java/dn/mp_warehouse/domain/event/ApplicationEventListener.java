package dn.mp_warehouse.domain.event;
import dn.mp_warehouse.domain.entity.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class ApplicationEventListener{


    @EventListener
    public void handleEvent(Event event){
    }
}
