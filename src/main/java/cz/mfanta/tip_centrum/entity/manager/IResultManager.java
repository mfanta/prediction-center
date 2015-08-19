package cz.mfanta.tip_centrum.entity.manager;

import cz.mfanta.tip_centrum.entity.Result;

public interface IResultManager {

	public Result loadResult(long fixtureId);
	
	public void storeResult(Result result);

}
