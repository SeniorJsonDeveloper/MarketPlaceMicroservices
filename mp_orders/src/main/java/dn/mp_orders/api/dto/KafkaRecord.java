package dn.mp_orders.api.dto;

import lombok.Data;

@Data
public class KafkaRecord {

    private String id;

    private String message;

    private String status;

    private String name;

    @Override
    public String toString() {
        return "KafkaDto{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", name='" + name + '\'' +
                '}';
    }
}
