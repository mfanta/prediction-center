package cz.mfanta.tip_centrum.entity.dao;

import cz.mfanta.tip_centrum.entity.Fixture;

import java.util.Date;
import java.util.List;

public interface IFixtureDao extends IDao<Fixture> {

    Fixture findFixtureByTeamsAndDate(String homeTeamName, String awayTeamName, Date date);

    List<Fixture> findFixturesByHomeTeam(String homeTeamName);

    List<Fixture> findFixturesByAwayTeam(String awayTeamName);
}
