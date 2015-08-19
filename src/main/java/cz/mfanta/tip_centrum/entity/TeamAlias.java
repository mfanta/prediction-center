package cz.mfanta.tip_centrum.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class TeamAlias implements Serializable {

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "name")
	private Team team;

	@Id
	private String alias;

	protected TeamAlias() {}

	public TeamAlias(Team team, String alias) {
		this.team = team;
		this.alias = alias;
	}

	public Team getTeam() {
		return team;
	}

}
