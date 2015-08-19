package cz.mfanta.tip_centrum.entity.reader.provider.pinnacle;

import java.util.TimeZone;

public interface PinnacleConstants {

	// example: 2010-08-14 11:45
	public static final String FIXTURE_DATE_FORMAT = "yyyy-MM-dd HH:mm";
	// the GMT+0 timezone used in pinnacle dates
	public static final TimeZone GMT_TIME_ZONE = TimeZone.getTimeZone("GMT+0");

}
