package dn.mp_notifications.domain.configuration;
import com.google.gson.JsonParser;
import dn.mp_notifications.api.dto.MessageDto;
import dn.mp_notifications.domain.service.SenderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestClient;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class KafkaListenerConfig {

    private final SenderService senderService;

    private final RestClient restClient;


    @SneakyThrows
    @KafkaListener(topics = "OrderToNotifications", groupId = "1")
    public void listen(final String payload) {
        log.info("Received payload: {}", payload);
        try {
            String message = JsonParser.parseString(payload)
                    .getAsJsonObject()
                    .get("message")
                    .getAsString();

            String name = JsonParser.parseString(payload)
                    .getAsJsonObject()
                    .get("name")
                    .getAsString();

            String status = JsonParser.parseString(payload)
                    .getAsJsonObject()
                    .get("status")
                    .getAsString();
            String id = JsonParser.parseString(payload)
                    .getAsJsonObject()
                    .get("id")
                    .getAsString();

            MessageDto dto = new MessageDto();
            dto.setId(id);
            dto.setMessage(message);
            dto.setName(name);
            dto.setStatus(status);
            senderService.sendNotification(dto,dto.getId());

            log.info("Received message: {}, " +
                     "Received name: {}, " +
                     "Received status: {} "+
                     "ReceivedId:{} ", dto.getMessage(),
                    dto.getName(),
                    dto.getStatus(),
                    dto.getId());

        }catch (Exception e){
            log.error("Error parsing json payload", e);
        }


    }

}

