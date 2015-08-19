package cz.mfanta.tip_centrum.entity.manager;

import cz.mfanta.tip_centrum.entity.dao.ResultDao;
import org.springframework.beans.factory.annotation.Autowired;

import cz.mfanta.tip_centrum.entity.Result;
import org.springframework.stereotype.Component;

@Component
public class ResultManager implements IResultManager {
	
	@Autowired
	private ResultDao resultDao;

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
