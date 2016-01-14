package cz.mfanta.tip_centrum.service.format;

import cz.mfanta.tip_centrum.service.AbstractService;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FormatService extends AbstractService {

	private Map<String, DateFormat> dateFormats = new HashMap<>();
	private Map<String, NumberFormat> numberFormats = new HashMap<>();

	public DateFormat getDateFormat(String format) {
		DateFormat result = dateFormats.get(format);
		if (result == null) {
			result = createDateFormat(format);
		}
		return result;
	}

	public String formatDate(Date date, String format) {
		DateFormat dateFormat = getDateFormat(format);
		return dateFormat.format(date);
	}

	public NumberFormat getNumberFormat(String format) {
		NumberFormat result = numberFormats.get(format);
		if (result == null) {
			result = createNumberFormat(format);
		}
		return result;
	}

	public String formatDouble(double number, String format) {
		NumberFormat numberFormat = getNumberFormat(format);
		return numberFormat.format(number);
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
