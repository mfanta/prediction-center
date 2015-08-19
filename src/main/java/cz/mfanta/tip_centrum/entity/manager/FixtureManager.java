package cz.mfanta.tip_centrum.entity.manager;

import java.util.*;

import cz.mfanta.tip_centrum.entity.dao.FixtureDao;
import org.springframework.beans.factory.annotation.Autowired;

import cz.mfanta.tip_centrum.entity.Competition;
import cz.mfanta.tip_centrum.entity.Fixture;
import cz.mfanta.tip_centrum.entity.FixtureGroup;
import cz.mfanta.tip_centrum.entity.IFixtureGroup;
import cz.mfanta.tip_centrum.entity.Odds;
import cz.mfanta.tip_centrum.entity.Prediction;
import cz.mfanta.tip_centrum.entity.Result;
import cz.mfanta.tip_centrum.entity.Team;
import cz.mfanta.tip_centrum.entity.reader.IFixtureReader;
import cz.mfanta.tip_centrum.service.AbstractService;
import org.springframework.stereotype.Component;

@Component
public class FixtureManager extends AbstractService implements IFixtureManager {

	@Autowired
	private IFixtureReader fixtureReader;
	
	@Autowired
	private ICompetitionManager competitionManager;
	
	@Autowired
	private FixtureDao fixtureDao;

	@Override
	public IFixtureGroup getAllUpcomingFixtures() {
		IFixtureGroup result = null;
		// merge the fixtures from all the available competitions
		Set<Competition> allComps = competitionManager.getAllCompetitions();
		Iterator<Competition> compIt = allComps.iterator();
		if (compIt.hasNext()) {
			Competition comp = compIt.next();
			result = getUpcomingFixturesForCompetition(comp);
			while (compIt.hasNext()) {
				comp = compIt.next();
				IFixtureGroup group = getUpcomingFixturesForCompetition(comp);
				result.merge(group);
			}
		}
		return result;
	}

	@Override
	public IFixtureGroup getAllFixtures() {
		final IFixtureGroup upcomingFixtures = getAllUpcomingFixtures();
		final IFixtureGroup storedFixtures = getStoredFixtures();
		upcomingFixtures.merge(storedFixtures);
		return upcomingFixtures;
	}

	@Override
	public IFixtureGroup getStoredFixtures() {
		final FixtureGroup result = new FixtureGroup();
		final List<Fixture> storedFixtureList = fixtureDao.getAll();
		for (Fixture fixture : storedFixtureList) {
			result.addFixture(fixture);
		}
		return result;
	}

	@Override
	public IFixtureGroup getUpcomingFixturesForCompetition(Competition competition) {
		return fixtureReader.getFixturesForCompetition(competition);
	}

	@Override
	public Fixture getFixture(long fixtureId) {
		return fixtureDao.getById(fixtureId);
	}
	
	@Override
	public Fixture createFixture(long fixtureId, String competitionName, Team homeTeam, Team awayTeam, Date fixtureDate, Odds odds, Prediction prediction,
		Result result) {
		final Fixture fixture = new Fixture(fixtureId, competitionName, homeTeam, awayTeam, fixtureDate, odds, prediction, result);
		fixtureDao.save(fixture);
		return fixture;
	}

	@Override
	public void updateFixture(Fixture fixture) {
		fixtureDao.update(fixture);
	}

	@Override
	public void updateTeamInFixtures(Team oldTeam, Team newTeam) {
		final String oldTeamName = oldTeam.getName();
		final List<Fixture> fixturesWithCorrespondingHomeTeam = fixtureDao.findFixturesByHomeTeam(oldTeamName);
		for (Fixture fixture : fixturesWithCorrespondingHomeTeam) {
			fixture.setHomeTeam(newTeam);
			fixtureDao.update(fixture);
		}
		final List<Fixture> fixturesWithCorrespondingAwayTeam = fixtureDao.findFixturesByAwayTeam(oldTeamName);
		for (Fixture fixture : fixturesWithCorrespondingAwayTeam) {
			fixture.setAwayTeam(newTeam);
			fixtureDao.update(fixture);
		}
	}

}
