package cz.mfanta.tip_centrum.exception;

public class ConversionException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConversionException() {
		super();
	}

	public ConversionException(String message) {
		super(message);
	}

	public ConversionException(Throwable cause) {
		super(cause);
	}

	public ConversionException(String message, Throwable cause) {
		super(message, cause);
	}

}
