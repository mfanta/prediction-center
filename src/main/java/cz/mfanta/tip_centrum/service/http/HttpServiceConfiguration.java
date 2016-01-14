package cz.mfanta.tip_centrum.service.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpServiceConfiguration {

    @Bean
    public HttpService httpService() {
        return new HttpService();
    }
}
