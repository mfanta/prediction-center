package cz.mfanta.tip_centrum.entity.manager;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import cz.mfanta.tip_centrum.entity.Competition;
import cz.mfanta.tip_centrum.service.AbstractService;
import cz.mfanta.tip_centrum.service.config.ConfigService;
import org.springframework.stereotype.Component;

@Component
public class CompetitionManager extends AbstractService implements ICompetitionManager {

	@Autowired
	private ConfigService configService;

	private Set<Competition> allCompetitions;

	public CompetitionManager() {
		allCompetitions = null;
	}

	@Override
	public Set<Competition> getAllCompetitions() {
		if (allCompetitions == null) {
			allCompetitions = new HashSet<Competition>();
			final String[] compNames = configService.getCompetitionNames();
			for (String compName : compNames) {
				final Competition comp = createCompetition(compName);
				allCompetitions.add(comp);
			}
		}
		return allCompetitions;
	}

	private static Competition createCompetition(String compName) {
		final Competition result = new Competition(compName);
		return result;
	}

}
