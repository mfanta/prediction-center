package cz.mfanta.tip_centrum.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigServiceConfiguration {

    @Bean
    public ConfigService configService() {
        return new ConfigService();
    }
}
