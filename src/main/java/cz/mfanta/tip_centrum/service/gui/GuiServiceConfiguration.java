package cz.mfanta.tip_centrum.service.gui;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GuiServiceConfiguration {

    @Bean
    public ColorPicker colorPicker() {
        return new ColorPicker();
    }
}
