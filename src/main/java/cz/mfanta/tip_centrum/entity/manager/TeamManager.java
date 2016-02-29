package cz.mfanta.tip_centrum.entity.manager;

import cz.mfanta.tip_centrum.entity.Team;
import cz.mfanta.tip_centrum.entity.TeamAlias;
import cz.mfanta.tip_centrum.entity.dao.ITeamAliasDao;
import cz.mfanta.tip_centrum.entity.dao.ITeamDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class TeamManager implements ITeamManager {

	private final ITeamDao teamDao;

	private final ITeamAliasDao teamAliasDao;

	private final IFixtureManager fixtureManager;

	@Override
	public Team getTeamByName(String teamName) {
		return teamDao.getById(teamName);
	}

	@Override
	public Team createTeam(String teamName) {
		final Team team = new Team(teamName);
		teamDao.save(team);
		return team;
	}

	@Override
	public void deleteTeam(Team team) {
		teamDao.delete(team.getName());
	}

	@Override
	public Team getOrCreateTeam(String teamName) {
		Team team = getTeamByNameOrAlias(teamName);
		if (team == null) {
			team = createTeam(teamName);
		}
		return team;
	}

	@Override
	public Team getTeamByNameOrAlias(String teamNameOrAlias) {
		Team result = getTeamByName(teamNameOrAlias);
		if (result == null) {
			final TeamAlias alias = teamAliasDao.getById(teamNameOrAlias);
			if (alias != null) {
				result = alias.getTeam();
			}
		}
		return result;
	}

	@Override
	public void createTeamAlias(String primaryTeamName, String alias) {
		if (!primaryTeamName.equals(alias)) {
			final Team team = getTeamByName(primaryTeamName);
			if (team != null) {
				final TeamAlias teamAlias = new TeamAlias(team, alias);
				teamAliasDao.save(teamAlias);
				log.info("Alias '{}' for team '{}' successfully created", alias, primaryTeamName);
				final Team aliasTeam = getTeamByName(alias);
				if (aliasTeam != null) {
					log.info("Team with name '{}' exists. Replacing it with the new primary team " +
                            "'{}' in all fixtures and deleting it", alias, primaryTeamName);
					fixtureManager.updateTeamInFixtures(aliasTeam, team);
					deleteTeam(aliasTeam);
				}
			} else {
				log.warn("Unable to create alias '{}' for team '{}' as it does not exist", alias,
                        primaryTeamName);
			}
		} else {
			log.info("Alias '{}' not created as it is the same as the primary team name", alias);
		}
	}
}
