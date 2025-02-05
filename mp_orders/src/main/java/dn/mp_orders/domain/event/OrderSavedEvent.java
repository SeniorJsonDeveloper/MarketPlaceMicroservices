package dn.mp_orders.domain.event;

public record OrderSavedEvent(String orderId, String status, Boolean isExist) {

}
