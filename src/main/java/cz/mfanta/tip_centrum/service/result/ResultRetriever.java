package cz.mfanta.tip_centrum.service.result;

import java.io.InputStream;
import java.util.Collection;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cz.mfanta.tip_centrum.entity.reader.IResultReader;
import cz.mfanta.tip_centrum.entity.reader.ResultFromReader;
import cz.mfanta.tip_centrum.service.config.ConfigService;
import cz.mfanta.tip_centrum.service.http.HttpService;

@RequiredArgsConstructor
public class ResultRetriever implements IResultRetriever {

	private final ConfigService configService;

	private final HttpService httpService;

	private final IResultReader resultReader;

	@Override
	public Collection<ResultFromReader> getResultsForCompetition(String competitionName) {
		final String resultUrl = configService.getResultUrl(competitionName);
		final InputStream inputStream = httpService.getContentAsStream(resultUrl);
		return resultReader.readResults(inputStream);
	}
}
