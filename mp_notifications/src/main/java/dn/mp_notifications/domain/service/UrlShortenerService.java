package dn.mp_notifications.domain.service;

import java.util.Optional;

public interface UrlShortenerService {

    String shortenUrl(String url);

    Optional<String> getOriginalUrl(String shortKey);

    String generateShortKey(String url);


}
