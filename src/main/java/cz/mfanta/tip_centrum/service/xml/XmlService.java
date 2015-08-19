package cz.mfanta.tip_centrum.service.xml;

import javax.xml.parsers.*;

import org.springframework.stereotype.Component;
import org.w3c.tidy.Tidy;
import org.xml.sax.SAXException;

import cz.mfanta.tip_centrum.service.*;

@Component
public class XmlService extends AbstractService {

	private SAXParser saxParser;

	@Override
	public void start() throws ServiceException {
		try {
			saxParser = SAXParserFactory.newInstance().newSAXParser();
		} catch (SAXException se) {
			throw new ServiceException("Failed to create a SAX Parser", se);
		} catch (ParserConfigurationException pce) {
			throw new ServiceException("Failed to create a SAX Parser", pce);
		}
	}

	public SAXParser getSaxParser() {
		return saxParser;
	}

	public Tidy getTidyParser() {
		final Tidy tidy = new Tidy();
		tidy.setXHTML(true);
		tidy.setXmlTags(true);
		tidy.setForceOutput(true);
		return tidy;
	}

}
