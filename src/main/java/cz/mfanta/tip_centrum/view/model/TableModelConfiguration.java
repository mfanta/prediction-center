package cz.mfanta.tip_centrum.view.model;

import com.google.common.eventbus.EventBus;
import cz.mfanta.tip_centrum.entity.manager.EntityManagerConfiguration;
import cz.mfanta.tip_centrum.entity.manager.IFixtureManager;
import cz.mfanta.tip_centrum.infrastructure.ThreadPoolConfiguration;
import cz.mfanta.tip_centrum.service.event.EventBusConfiguration;
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
        ThreadPoolConfiguration.class,
        EventBusConfiguration.class
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

    @Autowired
    private EventBus eventBus;

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
        return FixtureTableModel.builder()
                .formatService(formatService)
                .fixtureManager(fixtureManager)
                .predictionRenderer(predictionRenderer)
                .resultRenderer(resultRenderer)
                .taskScheduler(taskScheduler)
                .eventBus(eventBus)
                .build();
    }

    @Bean
    public StatsTableModel statsTableModel() {
        return StatsTableModel.builder()
                .statsService(statsService)
                .fixtureManager(fixtureManager)
                .eventBus(eventBus)
                .build();
    }
}
