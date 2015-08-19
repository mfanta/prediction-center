package cz.mfanta.tip_centrum.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Prediction")
public class Prediction implements Serializable {

	@Id
	private long fixtureId;

	@Column(name = "homeTeamScore")
	private int homeGoals;
	
	@Column(name = "awayTeamScore")
	private int awayGoals;

	protected Prediction() {
	}

	public Prediction(long fixtureId, int homeGoals, int awayGoals) {
		this.fixtureId = fixtureId;
		this.homeGoals = homeGoals;
		this.awayGoals = awayGoals;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + awayGoals;
		result = prime * result + homeGoals;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prediction other = (Prediction) obj;
		if (awayGoals != other.awayGoals)
			return false;
		if (homeGoals != other.homeGoals)
			return false;
		return true;
	}

	public int getHomeGoals() {
		return homeGoals;
	}
	
	public int getAwayGoals() {
		return awayGoals;
	}

	public long getFixtureId() {
		return fixtureId;
	}

	public void setFixtureId(long fixtureId) {
		this.fixtureId = fixtureId;
	}

	public void setHomeGoals(int homeGoals) {
		this.homeGoals = homeGoals;
	}

	public void setAwayGoals(int awayGoals) {
		this.awayGoals = awayGoals;
	}

	public void setNewScore(int homeGoals, int awayGoals) {
		this.homeGoals = homeGoals;
		this.awayGoals = awayGoals;
	}

	@Transient
	public boolean isEmpty() {
		return homeGoals == -1 || awayGoals == -1;
	}
	
	@Transient
	public boolean isAccurate(Result result) {
		final int goalDiff = homeGoals - awayGoals;
		final int resultGoalDiff = result.getHomeGoals() - result.getAwayGoals();
		// could there be a better way?! ;-)
		return goalDiff == resultGoalDiff || (goalDiff < 0 && resultGoalDiff < 0) || (goalDiff > 0 && resultGoalDiff > 0);
	}
	
	@Transient
	public boolean isExact(Result result) {
		return homeGoals == result.getHomeGoals() && awayGoals == result.getAwayGoals();
	}
	
	@Transient
	public int getOutcome(Odds odds) {
		final int outcome;
		if (homeGoals > awayGoals) {
			outcome = odds.getHomeOdds();
		} else if (homeGoals < awayGoals) {
			outcome = odds.getAwayOdds();
		} else {
			outcome = odds.getDrawOdds();
		}
		return outcome;
	}

}
