package dn.mp_orders.api.client;
import dn.mp_orders.api.exception.OrderNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class WarehouseHttpClient {

    @Value("${web.integration.warehouseUrl}")
    private String warehouseUrl;

    private final RestClient restClient;



    private final RedisTemplate<String,WarehouseResponse> redisTemplate;

    public WarehouseResponse getWarehouseId(String developerName) {
        String uri = UriComponentsBuilder.fromUri(URI.create(warehouseUrl))
                .path("/")
                .path(developerName)
                .toUriString();

        WarehouseResponse warehouseResponse = restClient
                .get()
                .uri(uri)
                .retrieve()
                .toEntity(WarehouseResponse.class)
                .getBody();
        Objects.requireNonNull(warehouseResponse);


        log.info(Objects.requireNonNull(warehouseResponse).toString());

        if(warehouseResponse.getId() == null) {
            throw new OrderNotFound("Заказ не найден на складе");
        }
        warehouseResponse.setId(warehouseResponse.getId());
        warehouseResponse.setIsExists(true);
        warehouseResponse.setCountOfProducts(warehouseResponse.getCountOfProducts());
        return warehouseResponse;
    }



}
