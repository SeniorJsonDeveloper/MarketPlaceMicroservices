package dn.mp_warehouse.domain.service;
import dn.mp_warehouse.api.dto.delivery.IpGeolocationOutDto;;


@FunctionalInterface
public interface GeoApifyService {

    IpGeolocationOutDto getIpGeolocation(String ip);
}
