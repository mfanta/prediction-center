package cz.mfanta.tip_centrum.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

import com.google.common.base.Objects;

@Entity
public class Result implements Serializable {

	@Id
	private long fixtureId;

	@Column(name = "homeTeamScore")
	private int homeGoals;

	@Column(name = "awayTeamScore")
	private int awayGoals;

	protected Result() {}

	public Result(long fixtureId, int homeGoals, int awayGoals) {
		this.fixtureId = fixtureId;
		this.homeGoals = homeGoals;
		this.awayGoals = awayGoals;
	}

	public long getFixtureId() {
		return fixtureId;
	}

	public int getHomeGoals() {
		return homeGoals;
	}

	public int getAwayGoals() {
		return awayGoals;
	}

	public void setNewScore(int homeGoals, int awayGoals) {
		this.homeGoals = homeGoals;
		this.awayGoals = awayGoals;
	}

	public boolean isHomeWin() {
        return homeGoals > awayGoals;
    }

    public boolean isDraw() {
        return homeGoals == awayGoals && homeGoals != -1;
    }

    public boolean isAwayWin() {
        return homeGoals < awayGoals;
    }

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
			.add("homeGoals", homeGoals)
			.add("awayGoals", awayGoals)
			.toString();
	}
}
