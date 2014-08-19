package org.bluebox.space2.ai.defense;

import org.bluebox.space2.ai.AIManager;
import org.bluebox.space2.ai.AIPlayerModel;
import org.bluebox.space2.ai.GSAI;
import org.bluebox.space2.ai.Goal;
import org.bluebox.space2.game.model.PlanetModel;

public class DefensiveManager extends AIManager {

	public DefensiveManager (GSAI gsai, AIPlayerModel player) {
		super(gsai, player);
	}

	@Override
	public Goal need (Object need) {
		return null;
		
	}

	@Override
	public void onUpdate () {
		// TODO Auto-generated method stub
		
	}

	// TODO
	public double getRouteRiskIndice (PlanetModel mPlanet) {
		return -1;
	}

}
