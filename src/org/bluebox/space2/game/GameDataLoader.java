package org.bluebox.space2.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.Utils;
import org.bluebox.space2.game.model.BuildingClassModel;
import org.bluebox.space2.game.model.StructureModel;
import org.bluebox.space2.game.model.FleetModel;
import org.bluebox.space2.game.model.ILocation;
import org.bluebox.space2.game.model.PlanetClassModel;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.PlayerModel;
import org.bluebox.space2.game.model.ShipClassModel;
import org.bluebox.space2.game.model.ShipModel;
import org.bluebox.space2.game.model.SystemModel;
import org.bluebox.space2.game.model.TravelModel;
import org.bluebox.space2.game.service.GameService;
import org.bluebox.space2.path.PathResolver;

import com.badlogic.gdx.graphics.Color;

public class GameDataLoader {

	public static GameData load () {
		GameData data = new GameData();
		GameService.getInstance().setData(data);

		Utils.resetUUID();
		
		GameDataFactory.initShipClasses(data);
		GameDataFactory.initBuildingClasses(data);
		
		data.planets = new ArrayList<PlanetModel>();
		
		try (BufferedReader br = new BufferedReader(new FileReader("1.sav"))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				line = line.replace("\t", "");

				if (line.indexOf("SPACE_POS_X") == 0) {
					GameService.getInstance().getData().spacePosX = Integer.valueOf(line.substring(12));
				}

				if (line.indexOf("SPACE_POS_Y") == 0) {
					GameService.getInstance().getData().spacePosY = Integer.valueOf(line.substring(12));
				}

				// Start systems
				if ("BEGIN PLAYER".equals(line)) {
					PlayerModel player = loadPlayer(br, data);
					data.players.add(player);
					if (player.isAI() == false) {
						data.player = player;
					}
				}

				// Start systems
				if ("BEGIN SYSTEM".equals(line)) {
					SystemModel system = loadSystem(br, data, null);
					data.systems.add(system);
					data.planets.addAll(system.getPlanets());
				}

				// Travel lines
				if ("BEGIN TRAVEL".equals(line)) {
					TravelModel travel = TravelModel.load(br, data);
					data.travelLines.add(travel);
				}

				// Travel lines
				if ("BEGIN FLEET".equals(line)) {
					FleetModel fleet = loadFleet(br, data);
					data.fleets.add(fleet);
				}

			}
				
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		addPlanetsToPlayers(data);
		
		PathResolver.getInstance().init(data.systems, data.travelLines);
		
		return data;
	}

	private static FleetModel loadFleet (BufferedReader br, GameData data) {
		List<ShipModel> ships = new ArrayList<ShipModel>();
		ILocation fleetLocation = null;
		PlayerModel owner = null;
		String fleetName = null;
		double fleetSpeed = -1;
		int fleetId = -1;
		
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				line = line.replace("\t", "");

				if ("END FLEET".equals(line)) {
					FleetModel fleet = new FleetModel(owner);
					fleet.setId(fleetId);
					fleet.setLocation(fleetLocation);
					fleet.addShips(ships);
					owner.addFleet(fleet);
					return fleet;
				}
				if (line.indexOf("ID") == 0) { fleetId = Integer.valueOf(line.substring(3)); }
				if (line.indexOf("SPEED") == 0) { fleetSpeed = Double.valueOf(line.substring(6)); }
				if (line.indexOf("NAME") == 0) { fleetName = line.substring(5); }
				if (line.indexOf("LOCATION") == 0) { fleetLocation = data.getLocationFromId(Integer.valueOf(line.substring(9))); }
				if (line.indexOf("OWNER") == 0) { owner = data.getPlayerFromId(Integer.valueOf(line.substring(6))); }
				if (line.indexOf("BEGIN SHIP") == 0) {
					ShipModel ship = loadShip(br, data);
					ships.add(ship);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return null;
	}

	private static ShipModel loadShip (BufferedReader br, GameData data) {
		int shipId = -1;
		int classId = -1;
		int hull = -1;
		int crew = -1;
		
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				line = line.replace("\t", "");
				if ("END SHIP".equals(line)) {
					ShipModel ship = new ShipModel(data.getShipClassFromId(classId));
					ship.setCrew(crew);
					ship.setHull(hull);
					ship.setId(shipId);
					return ship;
				}
				if (line.indexOf("ID") == 0) { shipId = Integer.valueOf(line.substring(3)); }
				if (line.indexOf("CLASS") == 0) { classId = Integer.valueOf(line.substring(6)); }
				if (line.indexOf("HULL") == 0) { hull = Integer.valueOf(line.substring(5)); }
				if (line.indexOf("CREW") == 0) { crew = Integer.valueOf(line.substring(5)); }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return null;
	}

	private static PlayerModel loadPlayer (BufferedReader br, GameData data) {
		PlayerModel player = new PlayerModel(null, Color.RED, Color.RED, false);
		
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				line = line.replace("\t", "");

				if ("END PLAYER".equals(line)) { return player; }
				if (line.indexOf("ID") == 0) { player.setId(Integer.valueOf(line.substring(3))); }
				if (line.indexOf("HOME") == 0) { player.setHome(data.getPlanetFromId(Integer.valueOf(line.substring(5)))); }
				if (line.indexOf("COLOR1") == 0) { player.setColor(Color.valueOf(line.substring(7))); }
				if (line.indexOf("COLOR2") == 0) { player.setSpaceColor(Color.valueOf(line.substring(7))); }
				if (line.indexOf("NAME") == 0) { player.setName(line.substring(5)); }
				if (line.indexOf("IA") == 0) { player.setAI(1); }
				
				if ("BEGIN SYSTEM".equals(line)) {
					SystemModel system = loadSystem(br, data, player);
					system.setOwner(player);
					data.systems.add(system);
					data.planets.addAll(system.getPlanets());
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return null;
	}

	private static SystemModel loadSystem (BufferedReader br, GameData data, PlayerModel owner) {
		SystemModel system = new SystemModel(null, 0, 0);
		int capitalId = -1;
		
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				line = line.replace("\t", "");

				if ("END SYSTEM".equals(line)) {
					if (capitalId != -1) {
						system.setCapital(data.getPlanetFromId(capitalId));
					}
					return system;
				}
				
				if (line.indexOf("ID") == 0) { system.setId(Integer.valueOf(line.substring(3))); }
				if (line.indexOf("CAPITAL") == 0) { capitalId = Integer.valueOf(line.substring(8)); }
				if (line.indexOf("NAME") == 0) { system.setName(line.substring(5)); }
				if (line.indexOf("OWNER") == 0) { system.setOwner(owner); }
				if (line.indexOf("POS_X") == 0) { system.setX(Integer.valueOf(line.substring(6))); }
				if (line.indexOf("POS_Y") == 0) { system.setY(Integer.valueOf(line.substring(6))); }
				
				if ("BEGIN PLANET".equals(line)) {
					PlanetModel planet = loadPlanet(br, data, owner);
					system.addPlanet(planet);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return null;
	}

	private static PlanetModel loadPlanet (BufferedReader br, GameData data, PlayerModel owner) {
		PlanetModel planet = new PlanetModel(PlanetClassModel.CLASS_D, 0);
		
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				line = line.replace("\t", "");

				if ("END PLANET".equals(line)) { return planet; }
				if (line.indexOf("ID") == 0) { planet.setId(Integer.valueOf(line.substring(3))); }
				if (line.indexOf("SIZE") == 0) { planet.setSize(Integer.valueOf(line.substring(5))); }
				if (line.indexOf("SYSTEM") == 0) { data.addPlanetToSystemId(Integer.valueOf(line.substring(7)), planet); }
				if (line.indexOf("NAME") == 0) { planet.setName(line.substring(5)); }
				if (line.indexOf("OWNER") == 0) { planet.setOwner(owner); }
				if (line.indexOf("POPULATION=") == 0) { planet.setPopulation(Double.valueOf(line.substring(11))); }
				if (line.indexOf("POPULATION_MAX") == 0) { planet.setPopulationMax(Integer.valueOf(line.substring(15))); }
				if (line.indexOf("PROD_BASE") == 0) { planet.setBaseProd(Double.valueOf(line.substring(10))); }
				if (line.indexOf("FOOD_BASE") == 0) { planet.setBaseFood(Double.valueOf(line.substring(10))); }
				if (line.indexOf("MONEY_BASE") == 0) { planet.setBaseMoney(Double.valueOf(line.substring(11))); }
				if (line.indexOf("SCIENCE_BASE") == 0) { planet.setBaseScience(Double.valueOf(line.substring(13))); }
				if (line.indexOf("CULTURE_BASE") == 0) { planet.setBaseCulture(Double.valueOf(line.substring(13))); }
				if (line.indexOf("PROD_MODIFIER") == 0) { planet.setProdModifier(Double.valueOf(line.substring(14))); }
				if (line.indexOf("FOOD_MODIFIER") == 0) { planet.setFoodModifier(Double.valueOf(line.substring(14))); }
				if (line.indexOf("MONEY_MODIFIER") == 0) { planet.setMoneyModifier(Double.valueOf(line.substring(15))); }
				if (line.indexOf("SCIENCE_MODIFIER") == 0) { planet.setScienceModifier(Double.valueOf(line.substring(17))); }
				if (line.indexOf("CULTURE_MODIFIER") == 0) { planet.setCultureModifier(Double.valueOf(line.substring(17))); }
				if (line.indexOf("CLASS") == 0) { planet.setClass((PlanetClassModel.getPlanetClass(Integer.valueOf(line.substring(6))))); }
				if (line.indexOf("BEGIN BUILDINGS") == 0) {
					loadBuildings(br, data, planet);
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return null;
	}

	private static void loadBuildings (BufferedReader br, GameData data, PlanetModel planet) {
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				line = line.replace("\t", "");

				if ("END BUILDINGS".equals(line)) { return; }
				else {
					BuildingClassModel buildingClass = data.getBuildingClassFromId(line);
					StructureModel building = new StructureModel(buildingClass, planet);
					planet.addStructure(building);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void addPlanetsToPlayers (GameData data) {
		for (PlanetModel planet: data.planets) {
			if (planet.getOwner() != null) {
				
			}
		}
	}

}
