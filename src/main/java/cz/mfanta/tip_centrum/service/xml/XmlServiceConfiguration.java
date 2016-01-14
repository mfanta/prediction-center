package cz.mfanta.tip_centrum.service.xml;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XmlServiceConfiguration {

    @Bean
    public XmlService xmlService() {
        return new XmlService();
    }
}
