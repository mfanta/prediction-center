package cz.mfanta.tip_centrum.entity.reader;

import java.util.Date;

import com.google.common.base.Objects;
import cz.mfanta.tip_centrum.entity.Result;

/**
 * Represents a fixture result retrieved from external data source.
 */
public class ResultFromReader {

	private String homeTeamName;

	private String awayTeamName;

	private Date fixtureDate;

	private int homeGoals;

	private int awayGoals;

	public ResultFromReader(String homeTeamName, String awayTeamName, Date fixtureDate, int homeGoals, int awayGoals) {
		this.homeTeamName = homeTeamName;
		this.awayTeamName = awayTeamName;
		this.fixtureDate = (Date) fixtureDate.clone();
		this.homeGoals = homeGoals;
		this.awayGoals = awayGoals;
	}

	public String getHomeTeamName() {
		return homeTeamName;
	}

	public String getAwayTeamName() {
		return awayTeamName;
	}

	public Date getFixtureDate() {
		return (Date) fixtureDate.clone();
	}

	public int getHomeGoals() {
		return homeGoals;
	}

	public int getAwayGoals() {
		return awayGoals;
	}

	public boolean sameResult(Result result) {
		return homeGoals == result.getHomeGoals() && awayGoals == result.getAwayGoals();
	}

	@Override
	public String toString() {
		return Objects
			.toStringHelper(this)
			.add("date", fixtureDate)
			.add("homeTeamName", homeTeamName)
			.add("awayTeamName", awayTeamName)
			.add("result", "" + homeGoals + ":" + awayGoals)
			.toString();
	}
}
