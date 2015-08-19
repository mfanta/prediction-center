package cz.mfanta.tip_centrum.entity.dao;

import cz.mfanta.tip_centrum.entity.Prediction;
import org.springframework.stereotype.Repository;

@Repository
public class PredictionDao extends AbstractDao<Prediction> {

    public PredictionDao() {
        setClazz(Prediction.class);
    }
}
