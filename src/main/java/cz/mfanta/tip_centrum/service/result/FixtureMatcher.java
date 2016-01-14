package cz.mfanta.tip_centrum.service.result;

import cz.mfanta.tip_centrum.entity.Fixture;
import cz.mfanta.tip_centrum.entity.dao.IFixtureDao;
import cz.mfanta.tip_centrum.entity.reader.ResultFromReader;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FixtureMatcher implements IFixtureMatcher {

	private final IFixtureDao fixtureDao;
	
	@Override
	public Fixture getFixtureForResult(ResultFromReader result) {
		return fixtureDao.findFixtureByTeamsAndDate(result.getHomeTeamName(), result.getAwayTeamName(), result.getFixtureDate());
	}
}
