package cz.mfanta.tip_centrum.entity.reader.provider.scores_pro;

import com.google.common.collect.Lists;
import cz.mfanta.tip_centrum.entity.reader.IResultReader;
import cz.mfanta.tip_centrum.entity.reader.ResultFromReader;
import cz.mfanta.tip_centrum.service.xml.XmlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.w3c.tidy.Tidy;
import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;

@RequiredArgsConstructor
@Slf4j
public class ScoresProFormatResultReader implements IResultReader {

	private final XmlService xmlService;

	private final ScoresProResultHandler resultHandler;

	@Override
	public Collection<ResultFromReader> readResults(InputStream is) {
		final Tidy tidyParser = xmlService.getTidyParser();
		final StringWriter stringWriter = new StringWriter();
		log.info("Starting parsing via Tidy parser");
		tidyParser.parse(is, stringWriter);
        log.info("Finished parsing via Tidy parser");
        log.info("Converting to String");
		final String cleanXhtml = stringWriter.getBuffer().toString();
        log.info("Done converting to String");
		Collection<ResultFromReader> results;
        log.info("Preparing SAX Parser");
		try {
			final SAXParser parser = xmlService.getSaxParser();
			final StringReader stringReader = new StringReader(cleanXhtml);
			final InputSource inputSource = new InputSource(new BufferedReader(stringReader));
            log.info("Starting to parse");
			parser.parse(inputSource, resultHandler);
            log.info("Done parsing");
			results = resultHandler.getResults();
		} catch (Exception e) {
			log.warn("Error while trying to read results from ScoresPro", e);
			results = Lists.newArrayList();
		}
		return results;
	}
}
