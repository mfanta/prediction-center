package cz.mfanta.tip_centrum.entity.dao;

import cz.mfanta.tip_centrum.entity.Result;

import javax.persistence.EntityManager;

public class ResultDao extends AbstractDao<Result> implements IResultDao {

    public ResultDao(EntityManager entityManager) {
        super(entityManager);
        setClazz(Result.class);
    }
}
