package cz.mfanta.tip_centrum.service.result;

import cz.mfanta.tip_centrum.entity.Competition;
import cz.mfanta.tip_centrum.entity.manager.ICompetitionManager;
import cz.mfanta.tip_centrum.entity.reader.ResultFromReader;
import cz.mfanta.tip_centrum.service.AbstractService;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Set;

@RequiredArgsConstructor
public class ResultService  extends AbstractService implements IResultService {

	private final ICompetitionManager competitionManager;

	private final IResultRetriever resultRetriever;

	private final IResultUpdater resultUpdater;
	
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
