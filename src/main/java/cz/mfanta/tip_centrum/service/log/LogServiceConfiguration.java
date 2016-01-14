package cz.mfanta.tip_centrum.service.log;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogServiceConfiguration {

    @Bean
    public LogMessageBuilder logMessageBuilder() {
        return new LogMessageBuilder();
    }
}
