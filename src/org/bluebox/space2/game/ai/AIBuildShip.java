package org.bluebox.space2.game.ai;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.game.model.BuildingClassModel;
import org.bluebox.space2.game.model.BuildingModel;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.PlayerModel;
import org.bluebox.space2.game.model.ShipClassModel;

public class AIBuildShip {

	public static void play (PlayerModel player) {
		List<ShipClassModel> toRemove = new ArrayList<ShipClassModel>();
		for (ShipClassModel sc: player.aiOrders.getShipToBuild()) {
			if (build(player, sc)) {
				toRemove.add(sc);
			}
		}
		player.aiOrders.getShipToBuild().removeAll(toRemove);
	}

	private static boolean build (PlayerModel player, ShipClassModel sc) {
		PlanetModel planet = getPlanetWithLowerETA(player, sc);
		if (planet == null) {
			System.out.println("AIBuildShip: no planet");
			return false;
		}
		
		planet.buildShip(sc);
		
		return true;
	}

	private static PlanetModel getPlanetWithLowerETA (PlayerModel player, ShipClassModel sc) {
		int lowerETA = Integer.MAX_VALUE;
		PlanetModel bestPlanet = null;
		
		for (PlanetModel planet: player.getPlanets()) {
			if (planet.hasBuilding(BuildingClassModel.Type.DOCK)) {
				int eta = planet.getBuildETA(sc.getBuildValue());
				int timeBeforeAvailable = planet.getShipBuildRemainder();
				if (eta + timeBeforeAvailable < lowerETA) {
					lowerETA = eta + timeBeforeAvailable;
					bestPlanet = planet;
				}
			}
		}
		
		return bestPlanet;
	}

}
