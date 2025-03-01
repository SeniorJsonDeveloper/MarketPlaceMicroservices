package dn.mp_warehouse.api.dto.delivery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IpGeolocationOutDto {

        private String ip;

        private Location location;

        @Data
        public static class Location {
                private double latitude;
                private double longitude;
                private String country;
                private String city;
                private String language;
        }

}
