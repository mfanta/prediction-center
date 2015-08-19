package cz.mfanta.tip_centrum.entity.dao;

import cz.mfanta.tip_centrum.entity.Team;
import org.springframework.stereotype.Repository;

@Repository
public class TeamDao extends AbstractDao<Team> {

    public TeamDao() {
        setClazz(Team.class);
    }
}
