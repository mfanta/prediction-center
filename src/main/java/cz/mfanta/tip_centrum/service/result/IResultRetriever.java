package cz.mfanta.tip_centrum.service.result;

import java.util.Collection;

import cz.mfanta.tip_centrum.entity.reader.ResultFromReader;

/**
 * The interface for a aggregated service used for results retrieval.
 */
public interface IResultRetriever {

	/**
	 * Return all the fixture results for a particular competition from an external source known at the moment of the API call.
	 *
	 * @param competitionName The competition name.
	 * @return Collection of known results.
	 */
	public Collection<ResultFromReader> getResultsForCompetition(String competitionName);

}
