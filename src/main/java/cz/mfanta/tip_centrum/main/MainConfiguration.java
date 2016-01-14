package cz.mfanta.tip_centrum.main;

import cz.mfanta.tip_centrum.service.gui.GuiServiceConfiguration;
import cz.mfanta.tip_centrum.view.listeners.ListenerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        GuiServiceConfiguration.class,
        ListenerConfiguration.class
})
public class MainConfiguration {
}
