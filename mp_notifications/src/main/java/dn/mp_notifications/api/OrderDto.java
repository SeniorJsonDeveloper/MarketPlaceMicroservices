package dn.mp_notifications.api;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private String message;

    private String name;

    private String status;


}
