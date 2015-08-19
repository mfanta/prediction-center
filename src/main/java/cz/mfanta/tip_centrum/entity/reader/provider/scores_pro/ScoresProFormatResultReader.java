package cz.mfanta.tip_centrum.entity.reader.provider.scores_pro;

import javax.xml.parsers.SAXParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.tidy.Tidy;
import org.xml.sax.InputSource;
import com.google.common.collect.Lists;
import cz.mfanta.tip_centrum.entity.reader.IResultReader;
import cz.mfanta.tip_centrum.entity.reader.ResultFromReader;
import cz.mfanta.tip_centrum.service.log.LogService;
import cz.mfanta.tip_centrum.service.xml.XmlService;

@Component
public class ScoresProFormatResultReader implements IResultReader {

	@Autowired
	private XmlService xmlService;

	@Autowired
	private LogService logService;

	@Autowired
	private ScoresProResultHandler resultHandler;

	@Override
	public Collection<ResultFromReader> readResults(InputStream is) {
		final Tidy tidyParser = xmlService.getTidyParser();
		final StringWriter stringWriter = new StringWriter();
		logService.logInfo("Starting parsing via Tidy parser");
		tidyParser.parse(is, stringWriter);
		logService.logInfo("Finished parsing via Tidy parser");
		logService.logInfo("Converting to String");
		final String cleanXhtml = stringWriter.getBuffer().toString();
		logService.logInfo("Done converting to String");
		Collection<ResultFromReader> results;
		logService.logInfo("Preparing SAX Parser");
		final SAXParser parser = xmlService.getSaxParser();
		try {
			final StringReader stringReader = new StringReader(cleanXhtml);
			final InputSource inputSource = new InputSource(new BufferedReader(stringReader));
			logService.logInfo("Starting to parse");
			parser.parse(inputSource, resultHandler);
			logService.logInfo("Done parsing");
			results = resultHandler.getResults();
		} catch (Exception e) {
			logService.logException(e);
			results = Lists.newArrayList();
		}
		return results;
	}
}
