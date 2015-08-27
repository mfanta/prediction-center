package cz.mfanta.tip_centrum.view.render;

import cz.mfanta.tip_centrum.service.gui.ColorPicker;
import cz.mfanta.tip_centrum.service.gui.GuiServiceConfiguration;
import cz.mfanta.tip_centrum.view.model.FixtureTableModel;
import cz.mfanta.tip_centrum.view.model.TableModelConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        TableModelConfiguration.class,
        GuiServiceConfiguration.class
})
@Configuration
public class RendererConfiguration {

    @Autowired
    private FixtureTableModel fixtureTableModel;

    @Autowired
    private ColorPicker colorPicker;

    @Bean
    public ResultCellRenderer resultCellRenderer() {
        return new ResultCellRenderer(fixtureTableModel, colorPicker);
    }
}
