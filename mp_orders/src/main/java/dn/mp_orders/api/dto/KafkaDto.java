package dn.mp_orders.api.dto;

import lombok.Data;

@Data
public class KafkaDto {

    private String message;

    private Boolean createdAt;

    private String status;
}
