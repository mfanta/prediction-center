package cz.mfanta.tip_centrum.entity.reader;

import java.io.InputStream;
import java.io.Reader;
import java.util.Collection;

/**
 * The interface for a service capable of extracting structured results from raw data.
 */
public interface IResultReader {

	/**
	 * Processes the input stream containing raw result data and extracts a collection of results.
	 *
	 * @param is The Input Stream containing the raw data (XML, HTML, ...)
	 * @return The Collection of extracted results.
	 */
	public Collection<ResultFromReader> readResults(InputStream is);

}
