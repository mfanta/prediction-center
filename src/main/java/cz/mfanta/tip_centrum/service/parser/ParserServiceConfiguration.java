package cz.mfanta.tip_centrum.service.parser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParserServiceConfiguration {

    @Bean
    public DateParser dateParser() {
        return new DateParser();
    }
}
