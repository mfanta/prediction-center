package cz.mfanta.tip_centrum.service.format;

import java.text.*;
import java.util.*;

import cz.mfanta.tip_centrum.service.AbstractService;
import cz.mfanta.tip_centrum.service.ServiceException;
import org.springframework.stereotype.Component;

@Component
public class FormatService extends AbstractService {

	private Map<String, DateFormat> dateFormats;
	private Map<String, NumberFormat> numberFormats;

	@Override
	public void start() throws ServiceException {
		dateFormats = new HashMap<String, DateFormat>();
		numberFormats = new HashMap<String, NumberFormat>();
	}

	public DateFormat getDateFormat(String format) {
		DateFormat result = dateFormats.get(format);
		if (result == null) {
			result = createDateFormat(format);
		}
		return result;
	}

	public String formatDate(Date date, String format) {
		DateFormat dateFormat = getDateFormat(format);
		String result = dateFormat.format(date);
		return result;
	}

	public NumberFormat getNumberFormat(String format) {
		NumberFormat result = numberFormats.get(format);
		if (result == null) {
			result = createNumberFormat(format);
		}
		return result;
	}

	public String formatLong(long number, String format) {
		NumberFormat numberFormat = getNumberFormat(format);
		String result = numberFormat.format(number);
		return result;
	}

	public String formatDouble(double number, String format) {
		NumberFormat numberFormat = getNumberFormat(format);
		String result = numberFormat.format(number);
		return result;
	}

	private DateFormat createDateFormat(String format) {
		DateFormat result = new SimpleDateFormat(format);
		dateFormats.put(format, result);
		return result;
	}

	private NumberFormat createNumberFormat(String format) {
		NumberFormat result = new DecimalFormat(format);
		numberFormats.put(format, result);
		return result;
	}

}
