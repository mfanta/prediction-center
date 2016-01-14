package cz.mfanta.tip_centrum.service.stats;

import cz.mfanta.tip_centrum.entity.IFixtureGroup;
import cz.mfanta.tip_centrum.entity.stats.CompletePredictionStats;

public class StatsService {
	
	public CompletePredictionStats getStats(IFixtureGroup fixtures) {
		return new CompletePredictionStats(fixtures);
	}

}
