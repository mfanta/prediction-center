package cz.mfanta.tip_centrum.service.resource;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {

	private Map<FontInfo, Font> fontMap = new HashMap<>();

	public Font getFont(FontInfo fontInfo) {
		Font result = fontMap.get(fontInfo);
		if (result == null) {
			result = createFont(fontInfo);
			fontMap.put(fontInfo, result);
		}
		return result;
	}

	public Font createFont(FontInfo fontInfo) {
		//noinspection MagicConstant
		return new Font(fontInfo.getName(), fontInfo.getStyle(), fontInfo.getSize());
	}

}
