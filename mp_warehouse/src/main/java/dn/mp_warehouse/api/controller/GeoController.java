package dn.mp_warehouse.api.controller;

import dn.mp_warehouse.api.dto.delivery.IpGeolocationOutDto;
import dn.mp_warehouse.domain.service.GeoApifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GeoController {

    private final GeoApifyService geoApifyService;

    @GetMapping("/geo")
    public IpGeolocationOutDto getGeo(@RequestParam(required = false) String ip){
        return geoApifyService.getIpGeolocation(ip);
    }
}
