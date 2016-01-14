package cz.mfanta.tip_centrum.entity.manager;

import cz.mfanta.tip_centrum.entity.Result;
import cz.mfanta.tip_centrum.entity.dao.IResultDao;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResultManager implements IResultManager {
	
	private final IResultDao resultDao;

	@Override
	public Result loadResult(long fixtureId) {
		return resultDao.getById(fixtureId);
	}

	@Override
	public void storeResult(Result result) {
		final Result existingResult = loadResult(result.getFixtureId());
		if (existingResult != null) {
			resultDao.update(result);
		} else {
			resultDao.save(result);
		}
		
	}

}
