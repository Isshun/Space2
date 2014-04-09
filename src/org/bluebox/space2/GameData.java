package org.bluebox.space2;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.model.BuildingClassModel;
import org.bluebox.space2.model.FleetModel;
import org.bluebox.space2.model.PlanetModel;
import org.bluebox.space2.model.PlayerModel;
import org.bluebox.space2.model.ShipClassModel;
import org.bluebox.space2.model.SystemModel;
import org.bluebox.space2.model.TravelModel;

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
}
