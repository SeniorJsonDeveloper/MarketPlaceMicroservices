package dn.mp_orders.api.client;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dn.mp_orders.api.exception.OrderNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class WarehouseHttpClient {

    @Value("${web.integration.warehouseUrl}")
    private String warehouseUrl;

    private final RestClient restClient;

    private final OkHttpClient okHttpClient;

    private final ObjectMapper objectMapper;





    private final RedisTemplate<String,WarehouseResponse> redisTemplate;

    public WarehouseResponse getWarehouseId(String developerName) throws IOException {
        String uri = UriComponentsBuilder.fromUri(URI.create(warehouseUrl))
                .path("/")
                .path(developerName)
                .toUriString();

        Request request = new Request.Builder()
                .url(uri)
                .get()
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new OrderNotFound("Заказ на складе не найден");
            }
            var responseBody = response.body().string();
            log.info("Response body: {}", responseBody);


            var warehouseResponse = objectMapper.readValue(responseBody, WarehouseResponse.class);
            log.info("Response: {}", warehouseResponse);
            return warehouseResponse;

        } catch (Exception e) {
            log.error("Failed to get warehouse id: {}", e.getMessage(), e);
            return null;
        }


    }

    private static JsonObject getAsJsonObject(WarehouseResponse warehouseResponse) {
        WarehouseResponse response = new WarehouseResponse();
        response.setId(warehouseResponse.getId());
        response.setDeveloperName(warehouseResponse.getDeveloperName());
        response.setCountOfProducts(warehouseResponse.getCountOfProducts());
        response.setIsExists(warehouseResponse.getIsExists());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("warehouseId", response.getId());
        jsonObject.addProperty("developerName", response.getDeveloperName());
        jsonObject.addProperty("countOfProducts", response.getCountOfProducts());
        jsonObject.addProperty("isExists", response.getIsExists());
        return jsonObject;

    }
}
