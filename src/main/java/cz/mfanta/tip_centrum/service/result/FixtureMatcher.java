package cz.mfanta.tip_centrum.service.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cz.mfanta.tip_centrum.entity.Fixture;
import cz.mfanta.tip_centrum.entity.dao.FixtureDao;
import cz.mfanta.tip_centrum.entity.reader.ResultFromReader;

@Component
public class FixtureMatcher implements IFixtureMatcher {

	@Autowired
	private FixtureDao fixtureDao;
	
	@Override
	public Fixture getFixtureForResult(ResultFromReader result) {
		return fixtureDao.findFixtureByTeamsAndDate(result.getHomeTeamName(), result.getAwayTeamName(), result.getFixtureDate());
	}
}
