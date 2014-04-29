package org.bluebox.space2.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.bluebox.space2.game.model.PlanetClassModel;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.PlayerModel;
import org.bluebox.space2.game.model.SystemModel;
import org.bluebox.space2.game.model.TravelModel;

import com.badlogic.gdx.graphics.Color;

public class GameDataLoader {

	public static GameData load () {
		GameData data = new GameData();
		data.planets = new ArrayList<PlanetModel>();
		
		try (BufferedReader br = new BufferedReader(new FileReader("1.sav"))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				line = line.replace("\t", "");

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
					TravelModel travel = TravelModel.load(data, br);
					data.travelLines.add(travel);
				}

			}
				
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		addPlanetsToPlayers(data);
		
		return data;
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
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return null;
	}

	private static void addPlanetsToPlayers (GameData data) {
		for (PlanetModel planet: data.planets) {
			if (planet.getOwner() != null) {
				
			}
		}
	}

}
