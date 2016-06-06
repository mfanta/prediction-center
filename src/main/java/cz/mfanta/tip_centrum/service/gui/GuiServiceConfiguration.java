package cz.mfanta.tip_centrum.service.gui;

import com.google.common.eventbus.EventBus;
import cz.mfanta.tip_centrum.service.event.EventBusConfiguration;
import cz.mfanta.tip_centrum.service.resource.ResourceManager;
import cz.mfanta.tip_centrum.service.resource.ResourceManagerConfiguration;
import cz.mfanta.tip_centrum.view.model.FixtureTableModel;
import cz.mfanta.tip_centrum.view.model.StatsTableModel;
import cz.mfanta.tip_centrum.view.model.TableModelConfiguration;
import cz.mfanta.tip_centrum.view.render.CellRendererConfiguration;
import cz.mfanta.tip_centrum.view.render.ResultCellRenderer;
import cz.mfanta.tip_centrum.view.render.TeamCellRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        ResourceManagerConfiguration.class,
        TableModelConfiguration.class,
        CellRendererConfiguration.class,
        EventBusConfiguration.class
})
public class GuiServiceConfiguration {

    @Autowired
    private ResourceManager resourceManager;

    @Autowired
    private FixtureTableModel fixtureTableModel;

    @Autowired
    private StatsTableModel statsTableModel;

    @Autowired
    private ResultCellRenderer resultCellRenderer;

    @Autowired
    private TeamCellRenderer teamCellRenderer;

    @Autowired
    private EventBus eventBus;

    @Bean
    public FixtureTableWrapper fixtureTableWrapper() {
        return FixtureTableWrapper.builder()
                .eventBus(eventBus)
                .fixtureTableModel(fixtureTableModel)
                .resourceManager(resourceManager)
                .resultCellRenderer(resultCellRenderer)
                .teamCellRenderer(teamCellRenderer)
                .build();
    }

    @Bean
    public StatsTableWrapper statsTableWrapper() {
        return StatsTableWrapper.builder()
                .statsTableModel(statsTableModel)
                .eventBus(eventBus)
                .build();
    }

    @Bean
    public MainWindowCreator mainWindowCreator() {
        return MainWindowCreator.builder()
                .statsTableWrapper(statsTableWrapper())
                .fixtureTableWrapper(fixtureTableWrapper())
                .build();
    }

    @Bean
    public GuiService guiService() {
        return new GuiService(
                fixtureTableModel,
                mainWindowCreator()
        );
    }
}
