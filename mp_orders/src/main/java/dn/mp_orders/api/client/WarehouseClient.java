package dn.mp_orders.api.client;
import dn.mp_orders.domain.exception.OrderNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class WarehouseClient {

    @Value("${web.integration.warehouseUrl}")
    private String warehouseUrl;

    private final RestClient restClient;

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
