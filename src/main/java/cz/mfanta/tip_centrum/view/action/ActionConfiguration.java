package cz.mfanta.tip_centrum.view.action;

import cz.mfanta.tip_centrum.entity.manager.*;
import cz.mfanta.tip_centrum.service.gui.GuiService;
import cz.mfanta.tip_centrum.service.gui.GuiServiceConfiguration;
import cz.mfanta.tip_centrum.service.gui.MainWindowCreator;
import cz.mfanta.tip_centrum.view.model.FixtureTableModel;
import cz.mfanta.tip_centrum.view.model.TableModelConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        GuiServiceConfiguration.class,
        TableModelConfiguration.class,
        EntityManagerConfiguration.class
})
public class ActionConfiguration {

    @Autowired
    private GuiService guiService;

    @Autowired
    private MainWindowCreator mainWindowCreator;

    @Autowired
    private FixtureTableModel fixtureTableModel;

    @Autowired
    private IResultManager resultManager;

    @Autowired
    private IPredictionManager predictionManager;

    @Autowired
    private ITeamManager teamManager;

    @Autowired
    private IFixtureManager fixtureManager;

    @Bean
    public ActionPerformer actionPerformer() {
        return new ActionPerformer(
                mainWindowCreator,
                guiService,
                fixtureTableModel,
                resultManager,
                predictionManager,
                teamManager,
                fixtureManager
        );
    }
}
