package org.bluebox.space2.game.ai;

import org.bluebox.space2.game.model.BuildingClassModel;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.PlayerModel;

public class AIBuildStructure {

	public static void play (PlayerModel player) {
		buildDock(player);
	}

	private static void buildDock (PlayerModel player) {
		for (PlanetModel p: player.getPlanets()) {
			if (p.getStructuresToBuild().size() == 0 &&  p.hasBuilding(BuildingClassModel.Type.DOCK) == false) {
				p.addStructure(BuildingClassModel.Type.DOCK);
			}
		}
	}

}
