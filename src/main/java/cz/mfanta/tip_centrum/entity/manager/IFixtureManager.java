package cz.mfanta.tip_centrum.entity.manager;

import java.util.Date;

import cz.mfanta.tip_centrum.entity.Competition;
import cz.mfanta.tip_centrum.entity.Fixture;
import cz.mfanta.tip_centrum.entity.IFixtureGroup;
import cz.mfanta.tip_centrum.entity.Odds;
import cz.mfanta.tip_centrum.entity.Prediction;
import cz.mfanta.tip_centrum.entity.Result;
import cz.mfanta.tip_centrum.entity.Team;

public interface IFixtureManager extends IEntityManager {

	public IFixtureGroup getAllUpcomingFixtures();
	
	public IFixtureGroup getAllFixtures();
	
	public IFixtureGroup getStoredFixtures();

	public IFixtureGroup getUpcomingFixturesForCompetition(Competition competition);
	
	public Fixture getFixture(long fixtureId);
	
	public Fixture createFixture(long fixtureId, String competitionName, Team homeTeam, Team awayTeam,
        Date fixtureDate, Odds odds, Prediction prediction, Result result);
	
	public void updateFixture(Fixture fixture);

	public void updateTeamInFixtures(Team oldTeam, Team newTeam);

}
