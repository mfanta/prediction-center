package cz.mfanta.tip_centrum.view.model;

import cz.mfanta.tip_centrum.entity.Result;

public class ResultRenderer {
	
	public String renderResult(Result matchResult) {
		String result = "";
		if (matchResult != null) {
			result = Integer.toString(matchResult.getHomeGoals()) + ":" + Integer.toString(matchResult.getAwayGoals());
		}
		return result;
	}

}
