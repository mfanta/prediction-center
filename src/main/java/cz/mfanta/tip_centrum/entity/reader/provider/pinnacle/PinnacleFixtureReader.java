package cz.mfanta.tip_centrum.entity.reader.provider.pinnacle;

import cz.mfanta.tip_centrum.entity.Competition;
import cz.mfanta.tip_centrum.entity.IFixtureGroup;
import cz.mfanta.tip_centrum.entity.PredefinedEntities;
import cz.mfanta.tip_centrum.entity.reader.IFixtureReader;
import cz.mfanta.tip_centrum.service.config.ConfigService;
import cz.mfanta.tip_centrum.service.http.HttpService;
import cz.mfanta.tip_centrum.service.xml.XmlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.xml.parsers.SAXParser;
import java.io.InputStream;

@RequiredArgsConstructor
@Slf4j
public class PinnacleFixtureReader implements IFixtureReader {

	private final ConfigService configService;

	private final HttpService httpService;
	
	private final XmlService xmlService;

	private final PinnacleFixtureHandler fixtureHandler;

	public IFixtureGroup getFixturesForCompetition(Competition competition) {
		IFixtureGroup resultFixtureGroup;
        final String competitionName = competition.getName();
        InputStream is = getInputStream(competitionName);
		// we'll use a SAX parser to parse the HTML hoping the HTML is a
		// well-formatted XHTML
		try {
			final SAXParser parser = xmlService.getSaxParser();
            fixtureHandler.setCompetitionName(competitionName);
			parser.parse(is, fixtureHandler);
			resultFixtureGroup = fixtureHandler.getFixtureGroup();
		} catch (Exception e) {
			log.error("Exception while parsing fixtures", e);
			// return an empty fixture group
			resultFixtureGroup = PredefinedEntities.EMPTY_FIXTURE_GROUP;
		}
		return resultFixtureGroup;
	}

	/**
	 * Returns an input stream representing a web page providing fixtures and
	 * odds for the competition identified by the competitionName.
	 * 
	 * @param competitionName
	 *            The name of the competition.
	 * @return The input stream providing the web page content.
	 */
	private InputStream getInputStream(String competitionName) {
		String sourceUrl = configService.getPinnacleFixtureReaderUrl(competitionName);
		return httpService.getContentAsStream(sourceUrl);
	}

}
