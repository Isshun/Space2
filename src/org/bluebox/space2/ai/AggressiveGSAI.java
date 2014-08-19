package org.bluebox.space2.ai;

import org.bluebox.space2.ai.diplomacy.PlayerReport;
import org.bluebox.space2.ai.expension.ExpansionManager;

public class AggressiveGSAI extends GSAI {

	// Global development threshold before start to colonize a new planet
	private static final double PLANETS_DEVELOPMENT_THRESHOLD_TO_START_COLONIZE = 0.8;

	public void onUpdate () {
		if (mExpansionManager.getPlanetsDevelopement() > PLANETS_DEVELOPMENT_THRESHOLD_TO_START_COLONIZE) {
			mExpansionManager.need(ExpansionManager.Need.COLONIZE_NEW_PLANET);
		}
		
		for (PlayerReport report: mDiplomacyManager.getPlayerReports()) {
			if (report.globalForceIndice > mForcePoolManager.getGlobalForceIndice()) {
				//mForcePoolManager.need(need, filter);
			}
		}
	}

}
