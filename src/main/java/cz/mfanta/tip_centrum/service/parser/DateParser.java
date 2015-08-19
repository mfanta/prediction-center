package cz.mfanta.tip_centrum.service.parser;

import java.text.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import cz.mfanta.tip_centrum.general.GeneralConstants;
import cz.mfanta.tip_centrum.service.AbstractService;
import cz.mfanta.tip_centrum.service.log.LogService;
import cz.mfanta.tip_centrum.service.log.Severity;
import org.springframework.stereotype.Component;

@Component
public class DateParser extends AbstractService {

	// date format cache
	private Map<String, DateFormat> dateFormats;

	@Autowired
	private LogService logService;

	public DateParser() {
		dateFormats = new HashMap<String, DateFormat>();
	}

	public Date parseDate(String date, String dateFormat) {
		return parseDate(date, dateFormat, TimeZone.getDefault());
	}

	public Date parseDate(String date, String dateFormat, TimeZone timeZone) {
		DateFormat df = getDateFormat(dateFormat, timeZone);
		Date result;
		try {
			result = df.parse(date);
		} catch (ParseException pe) {
			logService.logException(pe, Severity.WRN);
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
		String result = df + GeneralConstants.HASH_DELIMITER + tz.toString();
		return result;
	}

}
