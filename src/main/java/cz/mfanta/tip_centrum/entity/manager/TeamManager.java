package cz.mfanta.tip_centrum.entity.manager;

import cz.mfanta.tip_centrum.entity.TeamAlias;
import cz.mfanta.tip_centrum.entity.dao.TeamAliasDao;
import cz.mfanta.tip_centrum.entity.dao.TeamDao;
import org.springframework.beans.factory.annotation.Autowired;

import cz.mfanta.tip_centrum.entity.Team;
import cz.mfanta.tip_centrum.service.AbstractService;
import cz.mfanta.tip_centrum.service.log.LogService;
import org.springframework.stereotype.Component;

@Component
public class TeamManager extends AbstractService implements ITeamManager {

	@Autowired
	private TeamDao teamDao;

	@Autowired
	private TeamAliasDao teamAliasDao;

	@Autowired
	private FixtureManager fixtureManager;

	@Autowired
	private LogService logService;

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
				logService.logInfo("Alias '" + alias + "' for team '" + primaryTeamName + "' successfully created");
				final Team aliasTeam = getTeamByName(alias);
				if (aliasTeam != null) {
					logService.logInfo("Team with name '" + alias + "' exists. Replacing it with the new primary team '" + primaryTeamName + "' in all " +
						"fixtures and deleting it");
					fixtureManager.updateTeamInFixtures(aliasTeam, team);
					deleteTeam(aliasTeam);
				}
			} else {
				logService.logWarning("Unable to create alias '" + alias + "' for team '" + primaryTeamName + "' as it does not exist");
			}
		} else {
			logService.logInfo("Alias '" + alias + "' not created as it is the same as the primary team name");
		}
	}
}
