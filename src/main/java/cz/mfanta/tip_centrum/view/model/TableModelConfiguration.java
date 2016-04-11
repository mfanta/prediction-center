package cz.mfanta.tip_centrum.view.model;

import cz.mfanta.tip_centrum.entity.manager.EntityManagerConfiguration;
import cz.mfanta.tip_centrum.entity.manager.IFixtureManager;
import cz.mfanta.tip_centrum.infrastructure.ThreadPoolConfiguration;
import cz.mfanta.tip_centrum.service.format.FormatService;
import cz.mfanta.tip_centrum.service.format.FormatServiceConfiguration;
import cz.mfanta.tip_centrum.service.stats.StatsService;
import cz.mfanta.tip_centrum.service.stats.StatsServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.AsyncTaskExecutor;

@Configuration
@Import({
        FormatServiceConfiguration.class,
        EntityManagerConfiguration.class,
        StatsServiceConfiguration.class,
        ThreadPoolConfiguration.class
})
public class TableModelConfiguration {

    @Autowired
    private FormatService formatService;

    @Autowired
    private StatsService statsService;

    @Autowired
    private IFixtureManager fixtureManager;

    @Autowired
    private PredictionRenderer predictionRenderer;

    @Autowired
    private ResultRenderer resultRenderer;

    @Autowired
    private AsyncTaskExecutor taskScheduler;

    @Bean
    public PredictionRenderer predictionRenderer() {
        return new PredictionRenderer();
    }

    @Bean
    public ResultRenderer resultRenderer() {
        return new ResultRenderer();
    }

    @Bean
    public FixtureTableModel fixtureTableModel() {
        return new FixtureTableModel(
                formatService,
                fixtureManager,
                predictionRenderer,
                resultRenderer,
                taskScheduler
        );
    }

    @Bean
    public StatsTableModel statsTableModel() {
        return new StatsTableModel(statsService, fixtureManager);
    }
}
