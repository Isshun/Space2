package org.bluebox.space2.ai.planet;

import org.bluebox.space2.game.GameData;
import org.bluebox.space2.game.model.BuildingClassModel;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.service.GameService;

public class PlanetaryGovernor {
	private PlanetModel	mPlanet;
	
	public enum Need {
		BUILD_DOCK
	}

	public PlanetaryGovernor(PlanetModel planet) {
		mPlanet = planet;
	}
	
	public int need (Need need) {
		
		switch (need) {
		case BUILD_DOCK:
			BuildingClassModel dockModel = GameService.getInstance().getBuildingClass(BuildingClassModel.Type.DOCK);
			int eta = mPlanet.getBuildListETA() + mPlanet.getBuildETA(dockModel.getBuildValue());
			mPlanet.buildStructure(dockModel.type);
			return eta;
		}
		
		return -1;
	}

}
