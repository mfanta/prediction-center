package cz.mfanta.tip_centrum.view.listeners;

import cz.mfanta.tip_centrum.service.gui.GuiService;
import cz.mfanta.tip_centrum.service.gui.GuiServiceConfiguration;
import cz.mfanta.tip_centrum.service.gui.MainWindowCreator;
import cz.mfanta.tip_centrum.service.result.IResultService;
import cz.mfanta.tip_centrum.service.result.ResultService;
import cz.mfanta.tip_centrum.service.result.ResultServiceConfiguration;
import cz.mfanta.tip_centrum.view.action.ActionConfiguration;
import cz.mfanta.tip_centrum.view.action.ActionPerformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        ActionConfiguration.class,
        ResultServiceConfiguration.class,
        GuiServiceConfiguration.class
})
public class ListenerConfiguration {

    @Autowired
    private ActionPerformer actionPerformer;

    @Autowired
    private IResultService resultService;

    @Autowired
    private GuiService guiService;

    @Autowired
    private MainWindowCreator mainWindowCreator;

    @Bean
    public FixtureTableResultEditor fixtureTableResultEditor() {
        return new FixtureTableResultEditor(actionPerformer, mainWindowCreator);
    }

    @Bean
    public FixtureTablePredictionUpdater fixtureTablePredictionUpdater() {
        return new FixtureTablePredictionUpdater(actionPerformer, mainWindowCreator);
    }

    @Bean
    public EnterPredictionOrResultKeyListener enterPredictionOrResultKeyListener() {
        return new EnterPredictionOrResultKeyListener(actionPerformer, mainWindowCreator);
    }

    @Bean
    public GetResultsActionListener getResultsActionListener() {
        return new GetResultsActionListener(
                resultService,
                guiService,
                mainWindowCreator);
    }

    @Bean
    public AddAliasActionListener addAliasActionListener() {
        return new AddAliasActionListener(actionPerformer, mainWindowCreator);
    }
}
