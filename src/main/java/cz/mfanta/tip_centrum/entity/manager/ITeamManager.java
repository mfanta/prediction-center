package cz.mfanta.tip_centrum.entity.manager;

import cz.mfanta.tip_centrum.entity.Team;

public interface ITeamManager extends IEntityManager {

	public Team getTeamByName(String teamName);

	public Team createTeam(String teamName);

	public void deleteTeam(Team team);

	public Team getOrCreateTeam(String teamName);

	public Team getTeamByNameOrAlias(String teamNameOrAlias);

	public void createTeamAlias(String primaryTeamName, String alias);

}
