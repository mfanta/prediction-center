package cz.mfanta.tip_centrum.entity.manager;

import cz.mfanta.tip_centrum.entity.Odds;

public interface IOddsManager extends IEntityManager {
	
	public Odds loadOdds(long fixtureId);
	
	public void storeOdds(Odds odds);
	
	public boolean oddsChanged(Odds oldOdds, Odds newOdds);

}
