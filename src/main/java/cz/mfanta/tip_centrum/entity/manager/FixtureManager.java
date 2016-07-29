package cz.mfanta.tip_centrum.entity.manager;

import cz.mfanta.tip_centrum.entity.*;
import cz.mfanta.tip_centrum.entity.dao.IFixtureDao;
import cz.mfanta.tip_centrum.entity.reader.IFixtureReader;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class FixtureManager implements IFixtureManager {

	private final IFixtureReader fixtureReader;
	
	private final ICompetitionManager competitionManager;
	
	private final IFixtureDao fixtureDao;

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

	public IFixtureGroup getAllFixtures() {
		IFixtureGroup upcomingFixtures = getAllUpcomingFixtures();
		IFixtureGroup storedFixtures = getStoredFixtures();
		upcomingFixtures.merge(storedFixtures);
		return upcomingFixtures;
	}

	public IFixtureGroup getStoredFixtures() {
		FixtureGroup result = new FixtureGroup();
		List<Fixture> storedFixtureList = fixtureDao.getAll();
        storedFixtureList.forEach(result::addFixture);
		return result;
	}

	public IFixtureGroup getUpcomingFixturesForCompetition(Competition competition) {
		return fixtureReader.getFixturesForCompetition(competition);
	}

	public Fixture getFixture(long fixtureId) {
		return fixtureDao.getById(fixtureId);
	}
	
	public Fixture createFixture(long fixtureId, String competitionName, Team homeTeam, Team awayTeam, Date fixtureDate, Odds odds, Prediction prediction,
		Result result) {
		Fixture fixture = new Fixture(fixtureId, competitionName, homeTeam, awayTeam, fixtureDate, odds, prediction, result);
		fixtureDao.save(fixture);
		return fixture;
	}

	public void updateFixture(Fixture fixture) {
		fixtureDao.update(fixture);
	}

	public void updateTeamInFixtures(Team oldTeam, Team newTeam) {
		String oldTeamName = oldTeam.getName();
		List<Fixture> fixturesWithCorrespondingHomeTeam = fixtureDao.findFixturesByHomeTeam(oldTeamName);
		for (Fixture fixture : fixturesWithCorrespondingHomeTeam) {
			fixture.setHomeTeam(newTeam);
			fixtureDao.update(fixture);
		}
		List<Fixture> fixturesWithCorrespondingAwayTeam = fixtureDao.findFixturesByAwayTeam(oldTeamName);
		for (Fixture fixture : fixturesWithCorrespondingAwayTeam) {
			fixture.setAwayTeam(newTeam);
			fixtureDao.update(fixture);
		}
	}

}
