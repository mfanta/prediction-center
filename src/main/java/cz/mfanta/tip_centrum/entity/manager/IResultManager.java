package cz.mfanta.tip_centrum.entity.manager;

import cz.mfanta.tip_centrum.entity.Result;

public interface IResultManager {

	Result loadResult(long fixtureId);
	
	void storeResult(Result result);

}
