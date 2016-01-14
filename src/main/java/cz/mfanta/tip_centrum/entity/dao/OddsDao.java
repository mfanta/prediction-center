package cz.mfanta.tip_centrum.entity.dao;

import cz.mfanta.tip_centrum.entity.Odds;

import javax.persistence.EntityManager;

public class OddsDao extends AbstractDao<Odds> implements IOddsDao {

    public OddsDao(EntityManager entityManager) {
        super(entityManager);
        setClazz(Odds.class);
    }
}
