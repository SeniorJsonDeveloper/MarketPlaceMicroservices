package dn.mp_notifications.api.client;

import dn.mp_notifications.api.dto.MessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "orderFeignClient",url = "${integration.url.orderUrl}")
public interface OpenFeignClientService {

    @GetMapping("/api/v1/order/{id}")
    MessageDto getOrderById(@PathVariable String id);

    @GetMapping("api/v1/order/list")
    List<MessageDto> getAllOrders();


}
