package org.bluebox.space2.game;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.game.model.BuildingClassModel;
import org.bluebox.space2.game.model.BuildingClassModel.Type;
import org.bluebox.space2.game.model.FleetModel;
import org.bluebox.space2.game.model.ILocation;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.PlayerModel;
import org.bluebox.space2.game.model.ShipTemplateModel;
import org.bluebox.space2.game.model.SystemModel;
import org.bluebox.space2.game.model.TravelModel;

public class GameData {
	private static GameData 			self;
	public int 								spacePosX;
	public int 								spacePosY;
	
	public List<ShipTemplateModel> 		shipTemplates;
	public List<SystemModel> 			systems;
	public List<PlanetModel> 			planets;
	public List<PlayerModel> 			players;
	public List<TravelModel> 			travelLines;
	public List<FleetModel> 			fleets;
	public List<BuildingClassModel> 	buildingClasses;
	public PlayerModel 					player;
	public int 								systemMapIndex;

	public GameData() {
		shipTemplates = new ArrayList<ShipTemplateModel>();
		systems = new ArrayList<SystemModel>();
		planets = new ArrayList<PlanetModel>();
		players = new ArrayList<PlayerModel>();
		buildingClasses = new ArrayList<BuildingClassModel>();
		travelLines = new ArrayList<TravelModel>();
		fleets = new ArrayList<FleetModel>();
		self = this;
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

	public TravelModel getTravelFromId (int id) {
		for (TravelModel travel: travelLines) {
			if (travel.getId() == id) {
				return travel;
			}
		}
		return null;
	}

	public PlayerModel getPlayerFromId (int id) {
		for (PlayerModel player: players) {
			if (player.getId() == id) {
				return player;
			}
		}
		return null;
	}

	public ShipTemplateModel getShipClassFromId (int id) {
		for (ShipTemplateModel shipClass: shipTemplates) {
			if (shipClass.getId() == id) {
				return shipClass;
			}
		}
		return null;
	}

	public ILocation getLocationFromId(int id) {
		ILocation location = getSystemFromId(id);
		if (location != null) {
			return location;
		}
		location = getTravelFromId(id);
		if (location != null) {
			return location;
		}
		location = getPlanetFromId(id);
		if (location != null) {
			return location;
		}
		return null;
	}

	public BuildingClassModel getBuildingClassFromId (String name) {
		for (BuildingClassModel buildingClass: buildingClasses) {
			if (buildingClass.getType().toString().equals(name)) {
				return buildingClass;
			}
		}
		return null;
	}

}
