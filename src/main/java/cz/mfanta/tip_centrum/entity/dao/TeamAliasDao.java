package cz.mfanta.tip_centrum.entity.dao;

import org.springframework.stereotype.Repository;
import cz.mfanta.tip_centrum.entity.TeamAlias;

import javax.persistence.EntityManager;

@Repository
public class TeamAliasDao extends AbstractDao<TeamAlias> implements ITeamAliasDao {

	public TeamAliasDao(EntityManager entityManager) {
		super(entityManager);
		setClazz(TeamAlias.class);
	}
}
