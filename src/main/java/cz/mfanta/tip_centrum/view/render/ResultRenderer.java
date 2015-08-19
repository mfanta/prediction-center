package cz.mfanta.tip_centrum.view.render;

import cz.mfanta.tip_centrum.entity.Result;
import org.springframework.stereotype.Component;

@Component
public class ResultRenderer {
	
	public String renderResult(Result matchResult) {
		String result = "";
		if (matchResult != null) {
			result = Integer.toString(matchResult.getHomeGoals()) + ":" + Integer.toString(matchResult.getAwayGoals());
		}
		return result;
	}

}
