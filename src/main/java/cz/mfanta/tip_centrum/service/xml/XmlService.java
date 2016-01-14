package cz.mfanta.tip_centrum.service.xml;

import cz.mfanta.tip_centrum.service.AbstractService;
import cz.mfanta.tip_centrum.service.ServiceException;
import org.w3c.tidy.Tidy;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XmlService extends AbstractService {

	public SAXParser getSaxParser() throws ServiceException {
		try {
			return SAXParserFactory.newInstance().newSAXParser();
		} catch (SAXException | ParserConfigurationException e) {
			throw new ServiceException("Failed to create a SAX Parser", e);
		}
	}

	public Tidy getTidyParser() {
		final Tidy tidy = new Tidy();
		tidy.setXHTML(true);
		tidy.setXmlTags(true);
		tidy.setForceOutput(true);
		return tidy;
	}

}
