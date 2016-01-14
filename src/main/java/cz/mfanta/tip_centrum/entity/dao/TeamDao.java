package cz.mfanta.tip_centrum.entity.dao;

import cz.mfanta.tip_centrum.entity.Team;

import javax.persistence.EntityManager;

public class TeamDao extends AbstractDao<Team> implements ITeamDao {

    public TeamDao(EntityManager entityManager) {
        super(entityManager);
        setClazz(Team.class);
    }
}
