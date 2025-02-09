package dn.mp_orders.domain.configuration;

import com.fasterxml.jackson.core.StreamWriteConstraints;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper =  new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);
        objectMapper.getFactory()
                .setStreamWriteConstraints(
                        StreamWriteConstraints.builder()
                                .maxNestingDepth(2000).build()
                );
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
