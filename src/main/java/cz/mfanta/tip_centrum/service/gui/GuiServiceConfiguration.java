package cz.mfanta.tip_centrum.service.gui;

import cz.mfanta.tip_centrum.entity.manager.EntityManagerConfiguration;
import cz.mfanta.tip_centrum.entity.manager.IPredictionManager;
import cz.mfanta.tip_centrum.entity.manager.IResultManager;
import cz.mfanta.tip_centrum.service.resource.ResourceManager;
import cz.mfanta.tip_centrum.service.resource.ResourceManagerConfiguration;
import cz.mfanta.tip_centrum.view.listeners.ListenerConfiguration;
import cz.mfanta.tip_centrum.view.model.FixtureTableModel;
import cz.mfanta.tip_centrum.view.model.StatsTableModel;
import cz.mfanta.tip_centrum.view.model.TableModelConfiguration;
import cz.mfanta.tip_centrum.view.render.CellRendererConfiguration;
import cz.mfanta.tip_centrum.view.render.ResultCellRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        ResourceManagerConfiguration.class,
        EntityManagerConfiguration.class,
        TableModelConfiguration.class,
        CellRendererConfiguration.class
})
public class GuiServiceConfiguration {

    @Autowired
    private ResourceManager resourceManager;

    @Autowired
    private IPredictionManager predictionManager;

    @Autowired
    private IResultManager resultManager;

    @Autowired
    private FixtureTableModel fixtureTableModel;

    @Autowired
    private StatsTableModel statsTableModel;

    @Autowired
    private ResultCellRenderer resultCellRenderer;

    @Bean
    public MainWindowCreator mainWindowCreator() {
        return new MainWindowCreator(
                fixtureTableModel,
                statsTableModel,
                resourceManager,
                resultCellRenderer
        );
    }

    @Bean
    public GuiService guiService() {
        return new GuiService(
                resourceManager,
                predictionManager,
                resultManager,
                fixtureTableModel,
                statsTableModel,
                mainWindowCreator()
        );
    }
}
