package dn.mp_warehouse.domain.service.impl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParser;
import dn.mp_warehouse.api.dto.delivery.IpGeolocationOutDto;
import dn.mp_warehouse.domain.service.GeoApifyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeoApifyServiceImpl implements GeoApifyService {

    private static final String GEOLOCATION_API_URL = "https://api.geoapify.com/v1/ipinfo?ip=%s&apiKey=%s";

    private final RestClient restClient;

    private final ObjectMapper objectMapper;

    @Value("${integration.geolocation.api_key}")
    private String apiKey;

    @Override
    @SneakyThrows
    public IpGeolocationOutDto getIpGeolocation(String ip) {
        String refactoringURL = buildApiUrl(ip);
        var apiResponse = restClient.get()
                .uri(refactoringURL)
                .retrieve()
                .body(Object.class);
        var jsonString = objectMapper.writeValueAsString(apiResponse);
        return convertFromJsonStringToPojo(jsonString);

    }

    private String buildApiUrl(String ip){
        return String.format(GEOLOCATION_API_URL, ip, apiKey);
    }

    public static IpGeolocationOutDto convertFromJsonStringToPojo(String jsonString){
         final String CITY_KEY = "city";
         final String NAME_VALUE = "name";
         final String COUNTRY_KEY = "country";
         final String LANGUAGE = "ru";
         final String LOCATION = "location";
         final String LATITUDE_VALUE = "latitude";
         final String LONGITUDE_VALUES = "longitude";

         IpGeolocationOutDto.Location ipGeolocationOutLocation = new IpGeolocationOutDto.Location();
         var city = JsonParser.parseString(jsonString)
                 .getAsJsonObject()
                 .get(CITY_KEY).getAsJsonObject()
                 .get(NAME_VALUE).getAsString();
         ipGeolocationOutLocation.setCity(city);
         var country = JsonParser.parseString(jsonString)
                 .getAsJsonObject()
                 .get(COUNTRY_KEY).getAsJsonObject()
                 .get(NAME_VALUE).getAsString();
         ipGeolocationOutLocation.setCountry(country);
         var latitude = JsonParser.parseString(jsonString)
                 .getAsJsonObject()
                 .get(LOCATION).getAsJsonObject()
                 .get(LATITUDE_VALUE).getAsDouble();
         ipGeolocationOutLocation.setLatitude(latitude);
         var longitude = JsonParser.parseString(jsonString)
                 .getAsJsonObject()
                .get(LOCATION).getAsJsonObject()
                .get(LONGITUDE_VALUES).getAsDouble();
        ipGeolocationOutLocation.setLongitude(longitude);
         var language = JsonParser.parseString(jsonString)
                 .getAsJsonObject()
                 .get(LANGUAGE)
                 .getAsString();
         ipGeolocationOutLocation.setLanguage(language);
         IpGeolocationOutDto ipGeolocationOutDto = new IpGeolocationOutDto();
         ipGeolocationOutDto.setLocation(ipGeolocationOutLocation);
         return ipGeolocationOutDto;
    }


}




