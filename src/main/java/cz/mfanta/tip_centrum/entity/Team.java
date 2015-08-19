package cz.mfanta.tip_centrum.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Team implements Serializable {
	
	@Id
	private String name;
	
	protected Team() {}

	public Team(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
