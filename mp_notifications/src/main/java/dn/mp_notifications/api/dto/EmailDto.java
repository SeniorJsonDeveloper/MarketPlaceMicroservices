package dn.mp_notifications.api.dto;


import lombok.Data;

@Data
public class EmailDto {

    private String phoneNumber;

    private String senderId;

    private Object message;
}
