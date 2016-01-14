package cz.mfanta.tip_centrum.service.parser;

import cz.mfanta.tip_centrum.general.GeneralConstants;
import cz.mfanta.tip_centrum.service.AbstractService;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Slf4j
public class DateParser extends AbstractService {

	// date format cache
	private Map<String, DateFormat> dateFormats = new HashMap<>();

	public Date parseDate(String date, String dateFormat) {
		return parseDate(date, dateFormat, TimeZone.getDefault());
	}

	public Date parseDate(String date, String dateFormat, TimeZone timeZone) {
		DateFormat df = getDateFormat(dateFormat, timeZone);
		Date result;
		try {
			result = df.parse(date);
		} catch (ParseException pe) {
			log.warn("Error while parsing date", pe);
			result = null;
		}
		return result;
	}

	private DateFormat getDateFormat(String df, TimeZone tz) {
		String hashKey = getHashKey(df, tz);
		DateFormat result = dateFormats.get(hashKey);
		if (result == null) {
			result = createDateFormat(df, tz);
			dateFormats.put(hashKey, result);
		}
		return result;
	}

	private static DateFormat createDateFormat(String df, TimeZone tz) {
		DateFormat result = new SimpleDateFormat(df);
		result.setTimeZone(tz);
		return result;
	}

	private static String getHashKey(String df, TimeZone tz) {
		return df + GeneralConstants.HASH_DELIMITER + tz.toString();
	}

}
