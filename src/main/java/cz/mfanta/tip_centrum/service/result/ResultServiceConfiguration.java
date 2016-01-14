package cz.mfanta.tip_centrum.service.result;

import cz.mfanta.tip_centrum.entity.dao.DaoConfiguration;
import cz.mfanta.tip_centrum.entity.dao.IFixtureDao;
import cz.mfanta.tip_centrum.entity.manager.*;
import cz.mfanta.tip_centrum.entity.reader.IResultReader;
import cz.mfanta.tip_centrum.entity.reader.provider.scores_pro.ScoresProFormatResultReader;
import cz.mfanta.tip_centrum.entity.reader.provider.scores_pro.ScoresProResultHandler;
import cz.mfanta.tip_centrum.service.config.ConfigService;
import cz.mfanta.tip_centrum.service.config.ConfigServiceConfiguration;
import cz.mfanta.tip_centrum.service.http.HttpService;
import cz.mfanta.tip_centrum.service.http.HttpServiceConfiguration;
import cz.mfanta.tip_centrum.service.xml.XmlService;
import cz.mfanta.tip_centrum.service.xml.XmlServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        EntityManagerConfiguration.class,
        ConfigServiceConfiguration.class,
        HttpServiceConfiguration.class,
        XmlServiceConfiguration.class,
        DaoConfiguration.class
})
public class ResultServiceConfiguration {

    @Autowired
    private ConfigService configService;

    @Autowired
    private HttpService httpService;

    @Autowired
    private XmlService xmlService;

    @Autowired
    private ICompetitionManager competitionManager;

    @Autowired
    private ITeamManager teamManager;

    @Autowired
    private IFixtureManager fixtureManager;

    @Autowired
    private IResultManager resultManager;

    @Autowired
    private IFixtureDao fixtureDao;

    @Bean
    public IFixtureMatcher fixtureMatcher() {
        return new FixtureMatcher(fixtureDao);
    }

    @Bean
    public ScoresProResultHandler resultHandler() {
        return new ScoresProResultHandler(teamManager);
    }

    @Bean
    public IResultReader resultReader() {
        return new ScoresProFormatResultReader(
                xmlService,
                resultHandler()
        );
    }

    @Bean
    public IResultRetriever resultRetriever() {
        return new ResultRetriever(
                configService,
                httpService,
                resultReader()
        );
    }

    @Bean
    public IResultUpdater resultUpdater() {
        return new ResultUpdater(
                fixtureMatcher(),
                fixtureManager,
                resultManager
        );
    }

    @Bean
    public IResultService resultService() {
        return new ResultService(
                competitionManager,
                resultRetriever(),
                resultUpdater()
        );
    }
}
