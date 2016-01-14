package cz.mfanta.tip_centrum.service.stats;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatsServiceConfiguration {

    @Bean
    public StatsService statsService() {
        return new StatsService();
    }
}
