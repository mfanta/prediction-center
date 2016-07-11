package cz.mfanta.tip_centrum.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Fixture implements Serializable {
	
	@Id
	private long fixtureId;

	@Column(name = "fixtureDate")
	private Date date;

    private String competitionName;

	@ManyToOne(optional = false, cascade = {CascadeType.MERGE})
	@JoinColumn(name = "homeTeamName")
	private Team homeTeam;
	
	@ManyToOne(optional = false, cascade = {CascadeType.MERGE})
	@JoinColumn(name = "awayTeamName")
	private Team awayTeam;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fixtureId")
	private Odds odds;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fixtureId")
	private Prediction prediction;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fixtureId")
	private Result result;
	
	protected Fixture() {}

	public Fixture(long fixtureId, String competitionName, Team homeTeam, Team awayTeam, Date date, Odds odds, Prediction prediction, Result result) {
		this.fixtureId = fixtureId;
        this.competitionName = competitionName;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.date = (Date) date.clone();
		this.odds = odds;
		this.prediction = prediction;
		this.result = result;
	}

	public long getFixtureId() {
		return fixtureId;
	}

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public Team getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}

	public Team getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
	}

	public Date getDate() {
		return (Date) date.clone();
	}

	public void setDate(Date date) {
		this.date = (Date) date.clone();
	}

	public Odds getOdds() {
		return odds;
	}
	
	public void setOdds(Odds odds) {
		this.odds = odds;
	}

	public Prediction getPrediction() {
		return prediction;
	}

	public void setPrediction(Prediction prediction) {
		this.prediction = prediction;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
	
	@Transient
	public boolean isDecided() {
		return prediction != null && !prediction.isEmpty() && result != null;
	}
	
	@Override
	public String toString() {
		return homeTeam.getName() + " - " + awayTeam.getName();
	}

}
