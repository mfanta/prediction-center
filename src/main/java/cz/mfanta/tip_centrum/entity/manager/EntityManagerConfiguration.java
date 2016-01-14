package cz.mfanta.tip_centrum.entity.manager;

import cz.mfanta.tip_centrum.entity.dao.*;
import cz.mfanta.tip_centrum.entity.reader.IFixtureReader;
import cz.mfanta.tip_centrum.entity.reader.provider.pinnacle.PinnacleFixtureHandler;
import cz.mfanta.tip_centrum.entity.reader.provider.pinnacle.PinnacleFixtureReader;
import cz.mfanta.tip_centrum.service.config.ConfigService;
import cz.mfanta.tip_centrum.service.config.ConfigServiceConfiguration;
import cz.mfanta.tip_centrum.service.fixture.FixtureService;
import cz.mfanta.tip_centrum.service.fixture.FixtureServiceConfiguration;
import cz.mfanta.tip_centrum.service.http.HttpService;
import cz.mfanta.tip_centrum.service.http.HttpServiceConfiguration;
import cz.mfanta.tip_centrum.service.log.LogMessageBuilder;
import cz.mfanta.tip_centrum.service.log.LogServiceConfiguration;
import cz.mfanta.tip_centrum.service.parser.DateParser;
import cz.mfanta.tip_centrum.service.parser.ParserServiceConfiguration;
import cz.mfanta.tip_centrum.service.xml.XmlService;
import cz.mfanta.tip_centrum.service.xml.XmlServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        DaoConfiguration.class,
        ConfigServiceConfiguration.class,
        XmlServiceConfiguration.class,
        LogServiceConfiguration.class,
        ParserServiceConfiguration.class,
        FixtureServiceConfiguration.class,
        HttpServiceConfiguration.class
})
public class EntityManagerConfiguration {

    @Autowired
    private LogMessageBuilder logMessageBuilder;

    @Autowired
    private DateParser dateParser;

    @Autowired
    private ConfigService configService;

    @Autowired
    private HttpService httpService;

    @Autowired
    private XmlService xmlService;

    @Autowired
    private FixtureService fixtureService;

    @Autowired
    private IPredictionDao predictionDao;

    @Autowired
    private IResultDao resultDao;

    @Autowired
    private ITeamDao teamDao;

    @Autowired
    private ITeamAliasDao teamAliasDao;

    @Autowired
    private IFixtureDao fixtureDao;

    @Autowired
    private IOddsDao oddsDao;

    @Bean
    public PinnacleFixtureHandler pinnacleFixtureHandler() {
        return new PinnacleFixtureHandler(
                logMessageBuilder,
                dateParser,
                fixtureService
        );
    }

    @Bean
    public IFixtureReader fixtureReader() {
        return new PinnacleFixtureReader(
                configService,
                httpService,
                xmlService,
                pinnacleFixtureHandler()
        );
    }

    @Bean
    public ICompetitionManager competitionManager() {
        return new CompetitionManager(configService);
    }

    @Bean
    public IPredictionManager predictionManager() {
        return new PredictionManager(predictionDao);
    }

    @Bean
    public IResultManager resultManager() {
        return new ResultManager(resultDao);
    }

    @Bean
    public IFixtureManager fixtureManager() {
        FixtureManager fixtureManager = new FixtureManager(
                fixtureReader(),
                competitionManager(),
                fixtureDao
        );
        pinnacleFixtureHandler().setFixtureManager(fixtureManager);
        return fixtureManager;
    }

    @Bean
    public ITeamManager teamManager() {
        TeamManager teamManager = new TeamManager(
                teamDao,
                teamAliasDao,
                fixtureManager()
        );
        pinnacleFixtureHandler().setTeamManager(teamManager);
        return teamManager;
    }

    @Bean
    public IOddsManager oddsManager() {
        OddsManager oddsManager = new OddsManager(oddsDao);
        pinnacleFixtureHandler().setOddsManager(oddsManager);
        return oddsManager;
    }
}
