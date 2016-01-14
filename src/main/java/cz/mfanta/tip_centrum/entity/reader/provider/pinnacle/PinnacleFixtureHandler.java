package cz.mfanta.tip_centrum.entity.reader.provider.pinnacle;

import cz.mfanta.tip_centrum.entity.*;
import cz.mfanta.tip_centrum.entity.manager.IFixtureManager;
import cz.mfanta.tip_centrum.entity.manager.IOddsManager;
import cz.mfanta.tip_centrum.entity.manager.ITeamManager;
import cz.mfanta.tip_centrum.exception.ConversionException;
import cz.mfanta.tip_centrum.general.GeneralConstants;
import cz.mfanta.tip_centrum.service.fixture.FixtureService;
import cz.mfanta.tip_centrum.service.log.LogMessageBuilder;
import cz.mfanta.tip_centrum.service.parser.DateParser;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Date;

import static cz.mfanta.tip_centrum.entity.reader.provider.pinnacle.PinnacleConstants.FIXTURE_DATE_FORMAT;
import static cz.mfanta.tip_centrum.entity.reader.provider.pinnacle.PinnacleConstants.GMT_TIME_ZONE;

/**
 * This class is definitely not thread-safe!
 */

@Slf4j
@RequiredArgsConstructor
public class PinnacleFixtureHandler extends DefaultHandler {

	/**
	 * Resulting fixture group.
	 */
	private FixtureGroup fixtureGroup;

	private String dateString;

	private String homeTeamName, awayTeamName, lastTeamName;

	private String homeMoneyline, drawMoneyline, awayMoneyline;

	private String fixtureIdString;

	private int fixtureId;

	private String chars;

    private String competitionName;

	private final LogMessageBuilder logMessageBuilder;

	private final DateParser dateParser;

	private final FixtureService fixtureService;

    @Setter
    private ITeamManager teamManager;

    @Setter
    private IOddsManager oddsManager;

	@Setter
	private IFixtureManager fixtureManager;

	private Date fixtureDate;

	/**
	 * Return the fixture group created by parsing the fixture web page
	 * contents.
	 * 
	 * @return The group of fixtures defined in the markup returned from the fixture provider.
	 */
	public IFixtureGroup getFixtureGroup() {
		return fixtureGroup;
	}

	@Override
	public void startDocument() throws SAXException {
		fixtureGroup = new FixtureGroup();
		resetChars();
	}

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    private void resetChars() {
		chars = "";
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		resetChars();
		// visitor-like pattern
		PinnacleElement element = getElementEnum(qName);
		if (element != null) {
			element.startElement(this);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		chars += new String(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		PinnacleElement element = getElementEnum(qName);
		if (element != null) {
			element.endElement(this);
		}
	}

	private PinnacleElement getElementEnum(String elementName) {
		PinnacleElement result = null;
		try {
			result = PinnacleElement.valueOf(PinnacleElement.class, elementName);
		} catch (IllegalArgumentException iae) {
			// ignore the element we're not interested in
			log.debug(iae.getMessage());
		}
		return result;
	}

	public void startEventElement() {
		// reset all values from previous event
		dateString = homeTeamName = awayTeamName = lastTeamName = null;
		homeMoneyline = drawMoneyline = awayMoneyline = null;
	}

	public void endEventElement() {
		createAndAddFixture();
	}

	private void createAndAddFixture() {
		// the moneylines might be missing at this point
		// it most likely means the odds have not been established yet
		if (allMoneylinesDefined()) {
			fixtureId = parseFixtureId();
			fixtureDate = parseFixtureDate();
			if (fixtureDate != null) {
                final Odds newOdds = buildNewOdds();
                Fixture fixture = fixtureManager.getFixture(fixtureId);
                if (fixture == null) {
                    fixture = createNewFixture(newOdds);
                } else {
                    updateFixture(fixture, newOdds);
                }
                fixtureGroup.addFixture(fixture);
            } else {
                log.warn("Skipping fixture with invalid date {}", dateString);
            }
		} else {
			log.info("Not all moneylines defined for the match between \'" + homeTeamName + "\' and \'" + awayTeamName + "\'");
		}
	}

	private void updateFixture(Fixture fixture, Odds odds) {
		final Odds storedOdds = oddsManager.loadOdds(fixtureId);
		fixture.setCompetitionName(competitionName);
		fixture.setDate(fixtureDate);
		updateOddsForFixture(fixture, odds, storedOdds);
		fixtureManager.updateFixture(fixture);
	}

	private void updateOddsForFixture(Fixture fixture, Odds odds, Odds storedOdds) {
		if (oddsManager.oddsChanged(storedOdds, odds)) {
			oddsManager.storeOdds(odds);
			fixture.setOdds(odds);
			log.info(logMessageBuilder.buildChangedOddsMessage(fixture, storedOdds, odds));
		}
	}

	private Date parseFixtureDate() {
		return dateParser.parseDate(dateString, FIXTURE_DATE_FORMAT, GMT_TIME_ZONE);
	}

	private Fixture createNewFixture(Odds newOdds) {
		Fixture fixture;Team homeTeam = teamManager.getOrCreateTeam(homeTeamName);
		Team awayTeam = teamManager.getOrCreateTeam(awayTeamName);
		Prediction prediction = new Prediction(fixtureId, -1, -1);
		fixture = fixtureManager.createFixture(fixtureId, competitionName, homeTeam, awayTeam, fixtureDate, newOdds, prediction, null);
		return fixture;
	}

	private Odds buildNewOdds() {
		int homeOdds = moneylineToOdds(homeMoneyline);
		int drawOdds = moneylineToOdds(drawMoneyline);
		int awayOdds = moneylineToOdds(awayMoneyline);
		return new Odds(fixtureId, homeOdds, drawOdds, awayOdds);
	}

	private int parseFixtureId() {
		return Integer.parseInt(fixtureIdString);
	}

	private boolean allMoneylinesDefined() {
		return homeMoneyline != null && drawMoneyline != null && awayMoneyline != null;
	}

	private int moneylineToOdds(String moneyline) {
		int result;
		try {
			result = fixtureService.moneylineToOdds(moneyline);
		} catch (ConversionException e) {
			log.warn("Exception while converting moneyline to odds", e);
			result = GeneralConstants.INVALID_ODDS;
		}
		return result;
	}

	public void startEventDateTimeElement() {
	}

	public void endEventDateTimeElement() {
		dateString = chars;
	}

	public void startParticipantElement() {
	}

	public void endParticipantElement() {
	}

	public void startParticipantNameElement() {
	}

	public void endParticipantNameElement() {
		lastTeamName = chars;
	}

	public void startVisitingHomeDrawElement() {
	}

	public void endVisitingHomeDrawElement() {
		PinnacleVenue.valueOf(PinnacleVenue.class, chars).defineVenue(this);
	}

	public void startMoneylineElement() {
	}

	public void endMoneylineElement() {
	}

	public void startMoneylineVisitingElement() {
	}

	public void endMoneylineVisitingElement() {
		awayMoneyline = chars;
	}

	public void startMoneylineDrawElement() {
	}

	public void endMoneylineDrawElement() {
		drawMoneyline = chars;
	}

	public void startMoneylineHomeElement() {
	}

	public void endMoneylineHomeElement() {
		homeMoneyline = chars;
	}

	public void startGameNumberElement() {
	}

	public void endGameNumberElement() {
		fixtureIdString = chars;
	}

	public void setHomeVenue() {
		homeTeamName = lastTeamName;
	}

	public void setDrawVenue() {
	}

	public void setAwayVenue() {
		awayTeamName = lastTeamName;
	}

}
