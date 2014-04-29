package org.bluebox.space2.game;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.game.model.BuildingClassModel;
import org.bluebox.space2.game.model.FleetModel;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.PlayerModel;
import org.bluebox.space2.game.model.ShipClassModel;
import org.bluebox.space2.game.model.SystemModel;
import org.bluebox.space2.game.model.TravelModel;

public class GameData {

	public List<ShipClassModel> 	shipClasses;
	public List<SystemModel> 		systems;
	public List<PlanetModel> 		planets;
	public List<PlayerModel> 		players;
	public List<TravelModel> 		travelLines;
	public List<FleetModel> 		fleets;
	public List<BuildingClassModel> 	buildingClasses;
	public PlayerModel 				player;
	public int 							systemMapIndex;

	public GameData() {
		shipClasses = new ArrayList<ShipClassModel>();
		systems = new ArrayList<SystemModel>();
		planets = new ArrayList<PlanetModel>();
		players = new ArrayList<PlayerModel>();
		buildingClasses = new ArrayList<BuildingClassModel>();
		travelLines = new ArrayList<TravelModel>();
		fleets = new ArrayList<FleetModel>();
	}

	public void addPlanetToSystemId (int id, PlanetModel planet) {
		for (SystemModel system: systems) {
			if (system.getId() == id) {
				system.addPlanet(planet);
			}
		}
	}

	public PlanetModel getPlanetFromId (int id) {
		for (PlanetModel planet: planets) {
			if (planet.getId() == id) {
				return planet;
			}
		}
		return null;
	}

	public SystemModel getSystemFromId (int id) {
		for (SystemModel system: systems) {
			if (system.getId() == id) {
				return system;
			}
		}
		return null;
	}
}
