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

	IFixtureGroup getAllFixtures();

	Fixture getFixture(long fixtureId);
	
	Fixture createFixture(long fixtureId, String competitionName, Team homeTeam, Team awayTeam,
						  Date fixtureDate, Odds odds, Prediction prediction, Result result);
	
	void updateFixture(Fixture fixture);

	void updateTeamInFixtures(Team oldTeam, Team newTeam);

}
