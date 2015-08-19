package cz.mfanta.tip_centrum.service.parser;

import org.springframework.beans.factory.annotation.Autowired;

import cz.mfanta.tip_centrum.service.AbstractService;
import org.springframework.stereotype.Component;

@Component
public class ParserService extends AbstractService {

	@Autowired
	private DateParser dateParser;

	public DateParser getDateParser() {
		return dateParser;
	}

}
