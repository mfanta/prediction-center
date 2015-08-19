package cz.mfanta.tip_centrum.service.resource;

public class FontInfo {

	private String name;
	private int style;
	private int size;

	public FontInfo(String name, int style, int size) {
		this.name = name;
		this.style = style;
		this.size = size;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the style
	 */
	public int getStyle() {
		return style;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	// override hashCode() and equals() to be usable as key for maps

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + size;
		result = prime * result + style;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FontInfo other = (FontInfo) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (size != other.size)
			return false;
		if (style != other.style)
			return false;
		return true;
	}

}
