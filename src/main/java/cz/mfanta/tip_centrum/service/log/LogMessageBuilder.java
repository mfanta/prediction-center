package cz.mfanta.tip_centrum.service.log;

import cz.mfanta.tip_centrum.entity.Fixture;
import cz.mfanta.tip_centrum.entity.Odds;

public class LogMessageBuilder {
	
	public String buildChangedOddsMessage(Fixture fixture, Odds oldOdds, Odds newOdds) {
		final String result;
		if (oldOdds == null) {
			result = "New odds for fixture " + fixture.toString() + ": " + newOdds.toString();
		} else {
			result = "Changed odds for fixture " + fixture.toString() + "; old odds: " + oldOdds.toString() + 
					", new odds: " + newOdds.toString();
		}
		return result;
	}

}
