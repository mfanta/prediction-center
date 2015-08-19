package cz.mfanta.tip_centrum.entity.reader.provider.scores_pro;

import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.google.common.collect.Lists;
import cz.mfanta.tip_centrum.entity.Team;
import cz.mfanta.tip_centrum.entity.manager.TeamManager;
import cz.mfanta.tip_centrum.entity.reader.ResultFromReader;
import cz.mfanta.tip_centrum.service.log.LogService;
import cz.mfanta.tip_centrum.service.log.Severity;

// Date definition:
// <div class="ncet">
// <li class="gradient6 ncet_round c25px">3</li>
// <li class="ncet_date gradient2 c25px">Sun 02 Sep 2012</li>
// </div>
// // <table id="blocks" class="gteam team10991-1 team11573-1" onmouseover="rowOver('10991-1','11573-1')" onmouseout="resetOver()">
// Fixture definition:
// <tbody><tr class="even">
// Time:
// <td class="kick">13:30</td>
// Finished?:
// <td class="status"><span title="Final Time">FT</span></td>
// Home Team:
// <td class="home uc "> <span class='yellowcard'></span><span class='yellowcard'></span> Liverpool FC</td>
// Score:
// <td class="score"><a title="Match Details" class="score_link" href="javascript:popup('853393-1')">0 - 2</a></td>
// Away Team:
// <td class="away uc winteam">Arsenal FC <span class='yellowcard'></span><span class='yellowcard'></span></td>

@Component
public class ScoresProResultHandler extends DefaultHandler {

	private static final String DATE_FORMAT_STRING = "EEE dd MMM yyyy z HH:mm";
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_STRING, Locale.US);

	private static final String TD_ELEMENT = "td";
	private static final String LI_ELEMENT = "li";
	private static final String CLASS_ATTRIBUTE = "class";
	private static final String CLASS_VALUE_KICK = "kick";
	private static final String CLASS_VALUE_STATUS = "status";
	private static final String CLASS_VALUE_HOME = "home";
	private static final String CLASS_VALUE_AWAY = "away";
	private static final String CLASS_VALUE_SCORE = "score";
	private static final String CLASS_VALUE_DATE = "ncet_date";
	private static final char SCORE_DELIMITER = '-';
	private static final String TIME_ZONE_SUFFIX = " CET";
	private static final String NEUTRAL_VENUE_SUFFIX = " (n)";

	@Autowired
	private LogService logService;

	@Autowired
	private TeamManager teamManager;

	private boolean readingDate;
	private boolean readingTime;
	private boolean readingStatus;
	private boolean readingHome;
	private boolean readingScore;
	private boolean readingAway;

	// concatenated date and time (e.g. Sun 02 Sep 2012 13:30)
	private String dateBuffer;
	private String timeBuffer;
	private String statusBuffer;
	private String homeBuffer;
	private String awayBuffer;
	private String scoreBuffer;

	private List<ResultFromReader> results;

	public Collection<ResultFromReader> getResults() {
		return results;
	}

	@Override
	public void startDocument() throws SAXException {
		logService.logInfo("startDocument");
		results = Lists.newArrayList();
		clearFlags();
		clearBuffers();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals(TD_ELEMENT)) {
			final String classAttr = attributes.getValue(CLASS_ATTRIBUTE);
			if (classAttr.contains(CLASS_VALUE_KICK)) {
				readingTime = true;
			} else if (classAttr.contains(CLASS_VALUE_STATUS)) {
				readingStatus = true;
			} else if (classAttr.contains(CLASS_VALUE_HOME)) {
				readingHome = true;
			} else if (classAttr.contains(CLASS_VALUE_AWAY)) {
				readingAway = true;
			} else if (classAttr.contains(CLASS_VALUE_SCORE)) {
				readingScore = true;
			}
		} else if (qName.equals(LI_ELEMENT)) {
			final String classAttr = attributes.getValue(CLASS_ATTRIBUTE);
			if (classAttr != null && classAttr.contains(CLASS_VALUE_DATE)) {
				readingDate = true;
				dateBuffer = "";
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals(TD_ELEMENT) || qName.equals(LI_ELEMENT)) {
			if (readingAway) {
				normalizeBuffers();
				storeResult();
				clearBuffers();
			}
			clearFlags();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String s = null;
		if (isReadingData()) {
			s = new String(ch, start, length);
		}
		if (readingDate) {
			dateBuffer += s;
		} else if (readingTime) {
			timeBuffer += s;
		} else if (readingStatus) {
			statusBuffer += s;
		} else if (readingScore) {
			scoreBuffer += s;
		} else if (readingHome) {
			homeBuffer += s;
		} else if (readingAway) {
			awayBuffer += s;
		}
	}

	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException {
		// do not resolve external entities
		return new InputSource(new StringReader(""));
	}

	private boolean isReadingData() {
		return readingDate || readingTime || readingStatus || readingScore || readingHome || readingAway;
	}

	private void clearFlags() {
		readingDate = readingTime = readingStatus = readingHome = readingScore = readingAway = false;
	}

	private void clearBuffers() {
		timeBuffer = statusBuffer = homeBuffer = awayBuffer = scoreBuffer = "";
	}

	private void normalizeBuffers() {
		dateBuffer = dateBuffer.trim();
		timeBuffer = timeBuffer.trim();
		statusBuffer = statusBuffer.trim();
		homeBuffer = normalizeBuffer(homeBuffer);
		awayBuffer = normalizeBuffer(awayBuffer);
		scoreBuffer = scoreBuffer.trim();
	}

	private String normalizeBuffer(String buffer) {
		String normalizedBuffer = buffer.replace("*", "").trim().replaceAll("\\s+", " ");
		if (normalizedBuffer.endsWith(NEUTRAL_VENUE_SUFFIX)) {
			normalizedBuffer = normalizedBuffer.substring(0, normalizedBuffer.length() - NEUTRAL_VENUE_SUFFIX.length());
		}
		return normalizedBuffer;
	}

	private void storeResult() {
		final MatchStatus status = getStatus();
		if (status.equals(MatchStatus.FT)) {
			final Date fixtureDate = getDate();
			final String homeTeamName = getHomeTeamName();
			if (homeTeamName == null) {
				logService.logWarning("Home team not found: '" + homeBuffer + "'");
			} else {
				final String awayTeamName = getAwayTeamName();
				if (awayTeamName == null) {
					logService.logWarning("Away team not found: '" + awayBuffer + "'");
				} else {
					final int homeGoals = getHomeGoals();
					final int awayGoals = getAwayGoals();
					final ResultFromReader result = new ResultFromReader(homeTeamName, awayTeamName, fixtureDate, homeGoals, awayGoals);
					logService.logInfo("Found result: " + result.toString());
					results.add(result);
				}
			}
		}
	}

	private String getAwayTeamName() {
		return getTeamPrimaryName(awayBuffer);
	}

	private String getHomeTeamName() {
		return getTeamPrimaryName(homeBuffer);
	}

	private String getTeamPrimaryName(String rawTeamName) {
		String teamName = null;
		String normalizedName = normalizeCharacters(rawTeamName);
		final Team team = teamManager.getTeamByNameOrAlias(normalizedName);
		if (team != null) {
			teamName = team.getName();
		}
		return teamName;
	}

	private MatchStatus getStatus() {
		statusBuffer = normalizeCharacters(statusBuffer);
		return MatchStatus.valueOf(statusBuffer);
	}

	private int getHomeGoals() {
		int homeGoals = -1;
		final int delimiterIndex = scoreBuffer.indexOf(SCORE_DELIMITER);
		if (delimiterIndex != -1) {
			final String homeGoalsString = scoreBuffer.substring(0, delimiterIndex).trim();
			homeGoals = Integer.parseInt(homeGoalsString);
		}
		return homeGoals;
	}

	private int getAwayGoals() {
		int awayGoals = -1;
		final int delimiterIndex = scoreBuffer.indexOf(SCORE_DELIMITER);
		if (delimiterIndex != -1) {
			final String awayGoalsString = scoreBuffer.substring(delimiterIndex + 1).trim();
			awayGoals = Integer.parseInt(awayGoalsString);
		}
		return awayGoals;
	}

	private Date getDate() {
		try {
			dateBuffer = normalizeCharacters(dateBuffer);
			if (!dateBuffer.endsWith(TIME_ZONE_SUFFIX)) {
				dateBuffer += TIME_ZONE_SUFFIX;
			}
			return DATE_FORMAT.parse(dateBuffer + " " + timeBuffer);
		} catch (ParseException pe) {
			logService.logException(pe, Severity.ERR, "Unable to parse result fixture date - the format must have changed!");
			return new Date();
		}
	}

	private String normalizeCharacters(String characters) {
		String newCharacters = characters.replace("\r", "");
		newCharacters = newCharacters.replace("\n", "");
		newCharacters = newCharacters.replace("\t", "");
		newCharacters = newCharacters.replaceAll(" +", " ");
		return newCharacters;
	}
}
