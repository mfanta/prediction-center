package cz.mfanta.tip_centrum.service.log;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import cz.mfanta.tip_centrum.service.AbstractService;
import cz.mfanta.tip_centrum.service.format.FormatService;
import org.springframework.stereotype.Component;

import static cz.mfanta.tip_centrum.service.log.Severity.*;
import static cz.mfanta.tip_centrum.service.log.FormatConstants.*;

@Component
public class LogService extends AbstractService {

	@Autowired
	private FormatService formatService;

	/**
	 * Logs an exception. The log level defaults to ERR.
	 * 
	 * @param e
	 *            The exception to log.
	 */
	public void logException(Exception e) {
		logException(e, ERR);
	}

	public void logException(Exception e, Severity severity) {
		logMessage(severity, e.getMessage());
		e.printStackTrace();
		// add new line after the exception trace
		System.out.println();
	}

	public void logException(Exception e, Severity severity, String message) {
		logMessage(severity, message);
		e.printStackTrace();
		// add new line after the exception trace
		System.out.println();
	}

	public void logWarning(String message) {
		logMessage(WRN, message);
	}

	public void logMessage(Severity severity, String message) {
		System.out.println(buildMessage(severity, message));
	}

	public void logInfo(String message) {
		logMessage(INF, message);
	}

	private String buildMessage(Severity severity, String message) {
		StringBuilder builder = new StringBuilder();
		builder.append(getDate());
		builder.append(DELIMITER);
		builder.append(severity);
		builder.append(DELIMITER);
		builder.append(message);
		return builder.toString();
	}

	private String getDate() {
		return formatService.formatDate(new Date(), DATE_FORMAT);
	}

}
