package cz.mfanta.tip_centrum.service.parser;

import cz.mfanta.tip_centrum.service.AbstractService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ParserService extends AbstractService {

	private final DateParser dateParser;

	public DateParser getDateParser() {
		return dateParser;
	}

}
