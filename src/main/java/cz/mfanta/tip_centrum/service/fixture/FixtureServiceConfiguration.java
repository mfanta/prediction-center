package cz.mfanta.tip_centrum.service.fixture;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FixtureServiceConfiguration {

    @Bean
    public FixtureService fixtureService() {
        return new FixtureService();
    }
}
