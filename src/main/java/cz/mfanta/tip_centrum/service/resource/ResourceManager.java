package cz.mfanta.tip_centrum.service.resource;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import cz.mfanta.tip_centrum.service.AbstractService;
import cz.mfanta.tip_centrum.service.ServiceException;
import org.springframework.stereotype.Component;

@Component
public class ResourceManager extends AbstractService {

	private Map<FontInfo, Font> fontMap;

	@Override
	public void start() throws ServiceException {
		fontMap = new HashMap<FontInfo, Font>();
	}

	@Override
	public void stop() throws ServiceException {
		// it seems the fonts do not need to be explicitly disposed
	}

	public Font getFont(FontInfo fontInfo) {
		Font result = fontMap.get(fontInfo);
		if (result == null) {
			result = createFont(fontInfo);
		}
		return result;
	}

	public Font createFont(FontInfo fontInfo) {
		Font result = new Font(fontInfo.getName(), fontInfo.getStyle(), fontInfo.getSize());
		return result;
	}

}
