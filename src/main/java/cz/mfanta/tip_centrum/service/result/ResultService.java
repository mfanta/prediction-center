package cz.mfanta.tip_centrum.service.result;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cz.mfanta.tip_centrum.entity.Competition;
import cz.mfanta.tip_centrum.entity.manager.ICompetitionManager;
import cz.mfanta.tip_centrum.entity.reader.ResultFromReader;
import cz.mfanta.tip_centrum.service.AbstractService;

@Component
public class ResultService  extends AbstractService implements IResultService {

	@Autowired
	private ICompetitionManager competitionManager;

	@Autowired
	private IResultRetriever resultRetriever;

	@Autowired
	private IResultUpdater resultUpdater;
	
	@Override
	public void updateAllResults() {
		final Set<Competition> competitions = competitionManager.getAllCompetitions();
		for (Competition competition : competitions) {
			updateResultsForCompetition(competition.getName());
		}
	}

	@Override
	public void updateResultsForCompetition(String competitionName) {
		final Collection<ResultFromReader> readerResults = resultRetriever.getResultsForCompetition(competitionName);
		resultUpdater.storeResults(readerResults);
	}
	
}
