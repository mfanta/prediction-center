package cz.mfanta.tip_centrum.service.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResourceManagerConfiguration {

    @Bean
    public ResourceManager resourceManager() {
        return new ResourceManager();
    }
}
