package cz.mfanta.tip_centrum.entity.dao;

import cz.mfanta.tip_centrum.entity.Prediction;

import javax.persistence.EntityManager;

public class PredictionDao extends AbstractDao<Prediction> implements IPredictionDao {

    public PredictionDao(EntityManager entityManager) {
        super(entityManager);
        setClazz(Prediction.class);
    }
}
