package cz.mfanta.tip_centrum.entity.dao;

import cz.mfanta.tip_centrum.entity.Odds;
import org.springframework.stereotype.Repository;

@Repository
public class OddsDao extends AbstractDao<Odds> {

    public OddsDao() {
        setClazz(Odds.class);
    }
}
