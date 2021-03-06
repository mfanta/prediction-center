package cz.mfanta.tip_centrum.entity.dao;

import cz.mfanta.tip_centrum.entity.Fixture;
import cz.mfanta.tip_centrum.entity.Team;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FixtureDao extends AbstractDao<Fixture> implements IFixtureDao {

    public FixtureDao(EntityManager entityManager) {
		super(entityManager);
        setClazz(Fixture.class);
    }

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Fixture findFixtureByTeamsAndDate(String homeTeamName, String awayTeamName, Date date) {
		// JPA criteria approach is significantly more complicated than Hibernate native one
		final EntityManager entityManager = getEntityManager();
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Fixture> query = criteriaBuilder.createQuery(Fixture.class);
		final Root<Fixture> fixtureRoot = query.from(Fixture.class);
		final Join<Fixture, Team> fixtureHomeTeamJoin = fixtureRoot.join("homeTeam");
		final Join<Fixture, Team> fixtureAwayTeamJoin = fixtureRoot.join("awayTeam");
		// day granularity comparison for dates should be enough - unlikely two teams playing
        // together twice a day
		final Calendar calendarDate = Calendar.getInstance();
		calendarDate.setTime(date);
		final Predicate yearPredicate = criteriaBuilder.equal(
                criteriaBuilder.function(
                        "year",
                        Integer.class,
                        fixtureRoot.get("date")
                ),
			    calendarDate.get(Calendar.YEAR)
        );
		final Predicate monthPredicate = criteriaBuilder.equal(
                criteriaBuilder.function(
                        "month",
                        Integer.class,
                        fixtureRoot.get("date")
                ),
			    calendarDate.get(Calendar.MONTH) + 1
        );
		final Predicate dayPredicate = criteriaBuilder.equal(
                criteriaBuilder.function(
                        "day",
                        Integer.class,
                        fixtureRoot.get("date")
                ),
			    calendarDate.get(Calendar.DAY_OF_MONTH)
        );
		final Predicate datePredicate = criteriaBuilder.and(
                yearPredicate,
                monthPredicate,
                dayPredicate
        );
		final Predicate homeTeamPredicate = criteriaBuilder.equal(
                fixtureHomeTeamJoin.get("name"),
                homeTeamName
        );
		final Predicate awayTeamPredicate = criteriaBuilder.equal(
                fixtureAwayTeamJoin.get("name"),
                awayTeamName
        );
		query.where(criteriaBuilder.and(datePredicate, homeTeamPredicate, awayTeamPredicate));
		final List<Fixture> resultList = entityManager.createQuery(query).getResultList();
		Fixture fixture = null;
		if (!resultList.isEmpty()) {
			fixture = resultList.get(0);
		}
		return fixture;
	}

	public List<Fixture> findFixturesByHomeTeam(String homeTeamName) {
		final EntityManager entityManager = getEntityManager();
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Fixture> query = criteriaBuilder.createQuery(Fixture.class);
		final Root<Fixture> fixtureRoot = query.from(Fixture.class);
		final Join<Fixture, Team> fixtureHomeTeamJoin = fixtureRoot.join("homeTeam");
		final Predicate homeTeamPredicate = criteriaBuilder.equal(
                fixtureHomeTeamJoin.get("name"),
                homeTeamName
        );
		query.where(homeTeamPredicate);
		return entityManager.createQuery(query).getResultList();
	}
    
	public List<Fixture> findFixturesByAwayTeam(String awayTeamName) {
		final EntityManager entityManager = getEntityManager();
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Fixture> query = criteriaBuilder.createQuery(Fixture.class);
		final Root<Fixture> fixtureRoot = query.from(Fixture.class);
		final Join<Fixture, Team> fixtureAwayTeamJoin = fixtureRoot.join("awayTeam");
		final Predicate awayTeamPredicate = criteriaBuilder.equal(
                fixtureAwayTeamJoin.get("name"),
                awayTeamName
        );
		query.where(awayTeamPredicate);
		return entityManager.createQuery(query).getResultList();
	}

}
