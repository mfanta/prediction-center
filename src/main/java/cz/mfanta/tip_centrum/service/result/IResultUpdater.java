package cz.mfanta.tip_centrum.service.result;

import java.util.Collection;

import cz.mfanta.tip_centrum.entity.reader.ResultFromReader;

/**
 * An interface for a service responsible for updating existing fixtures in the database with results as they are finished.
 */
public interface IResultUpdater {

	/**
	 * Stores a single result in the storage.
	 *
	 * @param result The result to be stored.
	 */
	public void storeResult(ResultFromReader result);

	/**
	 * Stores a collection of results in the storage.
	 *
	 * @param results A collection of results to be stored.
	 */
	public void storeResults(Collection<ResultFromReader> results);

}
