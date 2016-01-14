package cz.mfanta.tip_centrum.entity.manager;

import java.util.HashSet;
import java.util.Set;

import lombok.RequiredArgsConstructor;

import cz.mfanta.tip_centrum.entity.Competition;
import cz.mfanta.tip_centrum.service.AbstractService;
import cz.mfanta.tip_centrum.service.config.ConfigService;

@RequiredArgsConstructor
public class CompetitionManager extends AbstractService implements ICompetitionManager {

	private final ConfigService configService;

	private Set<Competition> allCompetitions;

	@Override
	public Set<Competition> getAllCompetitions() {
		if (allCompetitions == null) {
			allCompetitions = new HashSet<>();
			final String[] compNames = configService.getCompetitionNames();
			for (String compName : compNames) {
				final Competition comp = createCompetition(compName);
				allCompetitions.add(comp);
			}
		}
		return allCompetitions;
	}

	private static Competition createCompetition(String compName) {
		return new Competition(compName);
	}

}
