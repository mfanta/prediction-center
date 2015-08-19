package cz.mfanta.tip_centrum.service.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;

import cz.mfanta.tip_centrum.general.GeneralConstants;
import cz.mfanta.tip_centrum.service.AbstractService;
import cz.mfanta.tip_centrum.service.ServiceException;
import cz.mfanta.tip_centrum.service.log.LogService;
import cz.mfanta.tip_centrum.service.log.Severity;
import org.springframework.stereotype.Component;

import static cz.mfanta.tip_centrum.service.config.ConfigNames.*;

@Component
public class ConfigService extends AbstractService {

	private transient Properties props;

	@Autowired
	private transient LogService logService;

	@Override
	public void start() throws ServiceException {
		// create empty properties
		props = new Properties();
		// add all the system properties
		props.putAll(System.getProperties());
		// load properties from a file
		final Properties fileProps = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = getClass().getResourceAsStream(PROPERTY_FILE_PATH);
			if (inputStream != null) {
				fileProps.load(inputStream);
			}
		} catch (IOException ioe) {
			logService.logException(ioe, Severity.WRN);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ioe2) {
					logService.logException(ioe2, Severity.INF, "Failed to close the " + PROPERTY_FILE_PATH + " file");
				}
			}
		}
		props.putAll(fileProps);
	}

	public String[] getCompetitionNames() {
		String[] result = null;
		final String delimitedComps = props.getProperty(COMPETITIONS);
		if (delimitedComps != null) {
			final StringTokenizer tokenizer = new StringTokenizer(delimitedComps, GeneralConstants.CONFIG_DELIMITER);
			final int tokenCount = tokenizer.countTokens();
			result = new String[tokenCount];
			int index = 0;
			while (tokenizer.hasMoreTokens()) {
				final String token = tokenizer.nextToken();
				result[index] = token;
				index++;
			}
		}
		return result;
	}

	public String getPinnacleFixtureReaderUrl(final String compName) {
		final String propName = PINNACLE_FIXTURE_URL_PREFIX + compName;
		return props.getProperty(propName);
	}

	public String getResultUrl(String compName) {
		final String propName = RESULT_URL_PREFIX + compName;
		return props.getProperty(propName);
	}

}
