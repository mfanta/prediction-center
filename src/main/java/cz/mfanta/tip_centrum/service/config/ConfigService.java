package cz.mfanta.tip_centrum.service.config;

import cz.mfanta.tip_centrum.general.GeneralConstants;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.StringTokenizer;

import static cz.mfanta.tip_centrum.service.config.ConfigNames.COMPETITIONS;
import static cz.mfanta.tip_centrum.service.config.ConfigNames.PINNACLE_FIXTURE_URL_PREFIX;
import static cz.mfanta.tip_centrum.service.config.ConfigNames.PROPERTY_FILE_PATH;
import static cz.mfanta.tip_centrum.service.config.ConfigNames.RESULT_URL_PREFIX;

@Slf4j
public class ConfigService {

	private transient Properties props;

	public ConfigService() {
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
			log.error("Exception while starting config service", ioe);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ioe2) {
					log.warn("Failed to close the " + PROPERTY_FILE_PATH + " file", ioe2);
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
