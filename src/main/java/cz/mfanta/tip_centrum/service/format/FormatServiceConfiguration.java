package cz.mfanta.tip_centrum.service.format;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FormatServiceConfiguration {

    @Bean
    public FormatService formatService() {
        return new FormatService();
    }
}
