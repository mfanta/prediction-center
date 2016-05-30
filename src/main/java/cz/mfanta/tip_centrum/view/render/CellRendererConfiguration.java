package cz.mfanta.tip_centrum.view.render;

import cz.mfanta.tip_centrum.view.model.FixtureTableModel;
import cz.mfanta.tip_centrum.view.model.TableModelConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        TableModelConfiguration.class
})
public class CellRendererConfiguration {

    @Autowired
    private FixtureTableModel fixtureTableModel;

    @Bean
    public ColorPicker colorPicker() {
        return new ColorPicker();
    }

    @Bean
    public ResultCellRenderer resultCellRenderer() {
        return new ResultCellRenderer(
                fixtureTableModel,
                colorPicker()
        );
    }

    @Bean
    public TeamCellRenderer teamCellRenderer() {
        return new TeamCellRenderer();
    }
}
