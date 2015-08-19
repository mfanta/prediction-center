package cz.mfanta.tip_centrum.entity.manager;

import java.util.Set;

import cz.mfanta.tip_centrum.entity.Competition;

public interface ICompetitionManager extends IEntityManager {

	public Set<Competition> getAllCompetitions();

}
