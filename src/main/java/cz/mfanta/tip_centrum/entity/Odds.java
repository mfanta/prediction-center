package cz.mfanta.tip_centrum.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Odds implements Serializable {
	
	@Id
	private long fixtureId;

	// the odds are held as 1000x of the actual odds, e.g. 1.6 becomes 1600
	private int homeOdds;
	private int drawOdds;
	private int awayOdds;
	
	protected Odds() {}

	public Odds(long fixtureId, int homeOdds, int drawOdds, int awayOdds) {
		this.fixtureId = fixtureId;
		this.homeOdds = homeOdds;
		this.drawOdds = drawOdds;
		this.awayOdds = awayOdds;
	}

	public long getFixtureId() {
		return fixtureId;
	}

	public int getHomeOdds() {
		return homeOdds;
	}

	public int getDrawOdds() {
		return drawOdds;
	}

	public int getAwayOdds() {
		return awayOdds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + awayOdds;
		result = prime * result + drawOdds;
		result = prime * result + (int) (fixtureId ^ (fixtureId >>> 32));
		result = prime * result + homeOdds;
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
		Odds other = (Odds) obj;
		if (awayOdds != other.awayOdds)
			return false;
		if (drawOdds != other.drawOdds)
			return false;
		if (fixtureId != other.fixtureId)
			return false;
		if (homeOdds != other.homeOdds)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(formatOdds(homeOdds));
		builder.append(' ');
		builder.append(formatOdds(drawOdds));
		builder.append(' ');
		builder.append(formatOdds(awayOdds));
		return builder.toString();
	}
	
	private static String formatOdds(int odds) {
		return Integer.toString(odds / 1000) + "." + Integer.toString(odds % 1000);
	}
	
	

}
