package br.com.fco_romario.config;

import com.fasterxml.jackson.databind.ObjectMapper;//Faz serialização em Json
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        SimpleFilterProvider filters = new SimpleFilterProvider()
                .addFilter("PersonFilter", SimpleBeanPropertyFilter
                        .serializeAllExcept("exemploCampoNaoPodeSerializar")
                );

        mapper.setFilterProvider(filters);
        return mapper;
    }
}
