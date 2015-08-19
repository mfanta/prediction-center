package cz.mfanta.tip_centrum.service.stats;

import org.springframework.stereotype.Component;

import cz.mfanta.tip_centrum.entity.IFixtureGroup;
import cz.mfanta.tip_centrum.entity.stats.CompletePredictionStats;
import cz.mfanta.tip_centrum.service.AbstractService;

@Component
public class StatsService extends AbstractService {
	
	public CompletePredictionStats getStats(IFixtureGroup fixtures) {
		final CompletePredictionStats stats = new CompletePredictionStats(fixtures);
		return stats;
	}

}
