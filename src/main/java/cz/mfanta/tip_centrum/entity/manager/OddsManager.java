package cz.mfanta.tip_centrum.entity.manager;

import cz.mfanta.tip_centrum.entity.Odds;
import cz.mfanta.tip_centrum.entity.dao.IOddsDao;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OddsManager implements IOddsManager {
	
	private final IOddsDao oddsDao;
	
	@Override
	public Odds loadOdds(long fixtureId) {
		return oddsDao.getById(fixtureId);
	}

	@Override
	public void storeOdds(Odds odds) {
		Odds existingOdds = loadOdds(odds.getFixtureId());
		if (existingOdds != null) {
			oddsDao.update(odds);
		} else {
			oddsDao.save(odds);
		}
	}
	
	@Override
	public boolean oddsChanged(Odds oldOdds, Odds newOdds) {
		boolean result = true;
		if (oldOdds != null) {
			result = !oldOdds.equals(newOdds);
		}
		return result;
	}

}
