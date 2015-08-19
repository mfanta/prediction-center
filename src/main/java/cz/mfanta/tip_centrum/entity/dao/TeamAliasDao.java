package cz.mfanta.tip_centrum.entity.dao;

import org.springframework.stereotype.Repository;
import cz.mfanta.tip_centrum.entity.TeamAlias;

@Repository
public class TeamAliasDao extends AbstractDao<TeamAlias> {

	public TeamAliasDao() {
		setClazz(TeamAlias.class);
	}
}
