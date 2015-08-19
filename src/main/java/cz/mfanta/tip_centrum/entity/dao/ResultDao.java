package cz.mfanta.tip_centrum.entity.dao;

import cz.mfanta.tip_centrum.entity.Result;
import org.springframework.stereotype.Repository;

@Repository
public class ResultDao extends AbstractDao<Result> {

    public ResultDao() {
        setClazz(Result.class);
    }
}
