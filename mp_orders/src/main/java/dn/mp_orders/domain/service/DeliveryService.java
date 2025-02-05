package dn.mp_orders.domain.service;

import dn.mp_orders.api.dto.DeliveryDto;

import java.util.Set;

public interface DeliveryService {

    Set<DeliveryDto> getDeliveryByOrderId(String orderId);

    Set<DeliveryDto> getAllDeliveries();

    DeliveryDto createDelivery(String orderId);

    DeliveryDto getDeliveryById(String id);

    void updateDeliveryStatus();

    void deleteDelivery();



}
