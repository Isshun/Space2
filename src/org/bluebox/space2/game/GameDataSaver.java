package org.bluebox.space2.game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.bluebox.space2.game.model.BuildingModel;
import org.bluebox.space2.game.model.FleetModel;
import org.bluebox.space2.game.model.ILocation;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.PlayerModel;
import org.bluebox.space2.game.model.ShipModel;
import org.bluebox.space2.game.model.SystemModel;
import org.bluebox.space2.game.model.TravelModel;

public class GameDataSaver {

	public static void save (GameData mData) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("1.sav", false))) {
			
			// Players
			bw.write("BEGIN PLAYERS\n");
			for (PlayerModel player: mData.players) {
				savePlayer(bw, player);
			}
			bw.write("END PLAYERS\n\n");

			// Free systems
			bw.write("BEGIN SYSTEMS\n");
			for (SystemModel system: mData.systems) {
				if (system.isFree()) {
					saveSystem(bw, system);
				}
			}
			bw.write("END SYSTEMS\n");
			
			// Travel lines
			bw.write("BEGIN TRAVELS\n");
			for (TravelModel travel: mData.travelLines) {
				saveTravel(bw, travel);
			}
			bw.write("END TRAVELS\n");

			// Travel lines
			bw.write("BEGIN FLEETS\n");
			for (FleetModel fleet: mData.fleets) {
				saveFleet(bw, fleet);
			}
			bw.write("END FLEETS\n");

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void saveFleet (BufferedWriter bw, FleetModel fleet) {
		try {
			bw.write("BEGIN FLEET\n");
			bw.write("\tID=" + fleet.getId() + "\n");
			bw.write("\tOWNER=" + fleet.getOwner().getId() + "\n");
			bw.write("\tSPEED=" + fleet.getSpeed() + "\n");
			bw.write("\tNAME=" + fleet.getName() + "\n");
			bw.write("\tLOCATION=" + fleet.getLocation().getId() + "\n");
			
			for (ShipModel ship: fleet.getShips()) {
				saveShip(bw, ship);
			}
			
			bw.write("END FLEET\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void saveShip (BufferedWriter bw, ShipModel ship) {
		try {
			bw.write("\tBEGIN SHIP\n");
			bw.write("\t\tID=" + ship.getId() + "\n");
			bw.write("\t\tCLASS=" + ship.getShipClass().getId() + "\n");
			bw.write("\t\tHULL=" + ship.getHull() + "\n");
			bw.write("\t\tCREW=" + ship.getCrew() + "\n");
			bw.write("\tEND SHIP\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void saveTravel (BufferedWriter bw, TravelModel travel) {
		try {
			bw.write("BEGIN TRAVEL\n");
			bw.write("\tID=" + travel.getId() + "\n");
			bw.write("\tFROM=" + travel.getFrom().getId() + "\n");
			bw.write("\tTO=" + travel.getTo().getId() + "\n");
			bw.write("END TRAVEL\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void savePlayer (BufferedWriter bw, PlayerModel player) {
		try {
			bw.write("\tBEGIN PLAYER\n");
			bw.write("\t\tID=" + player.getId() + "\n");
			bw.write("\t\tNAME=" + player.getName() + "\n");
			bw.write("\t\tHOME=" + player.getHome().getId() + "\n");
			bw.write("\t\tCOLOR1=" + player.getColor().toString() + "\n");
			bw.write("\t\tCOLOR2=" + player.getSpaceColor().toString() + "\n");

			if (player.isAI()) {
				bw.write("\t\tAI=" + 1 + "\n");
			}
			
			// Systems
			bw.write("BEGIN SYSTEMS\n");
			for (SystemModel system: player.getSystems()) {
				saveSystem(bw, system);
			}
			bw.write("END SYSTEMS\n");
	
			bw.write("\t\tHOME=" + player.getHome().getId() + "\n");
			
			bw.write("\tEND PLAYER\n\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void saveSystem (BufferedWriter bw, SystemModel system) {
		try {
			bw.write("BEGIN SYSTEM\n");

			bw.write("\tID=" + system.getId() + "\n");
			bw.write("\tNAME=" + system.getName() + "\n");
			bw.write("\tPOS_X=" + system.getX() + "\n");
			bw.write("\tPOS_Y=" + system.getY() + "\n");
			
			if (system.getCapital() != null) {
			bw.write("\tCAPITAL=" + system.getCapital().getId() + "\n");
			}

			if (system.getOwner() != null) {
				bw.write("\tOWNER=" + system.getOwner().getId() + "\n");
			}
	
			bw.write("\tBEGIN PLANETS\n");
			for (PlanetModel planet: system.getPlanets()) {
				savePlanet(bw, planet);
			}
			bw.write("\tEND PLANETS\n");
			
			bw.write("END SYSTEM\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void savePlanet (BufferedWriter bw, PlanetModel planet) {
		System.out.println("Save planet: " + planet.getName());

		try {
			bw.write("\t\tBEGIN PLANET\n");
			bw.write("\t\t\tID=" + planet.getId() + "\n");
			bw.write("\t\t\tSYSTEM=" + planet.getSystem().getId() + "\n");
			bw.write("\t\t\tSIZE=" + planet.getSize() + "\n");
			bw.write("\t\t\tNAME=" + planet.getName() + "\n");
			bw.write("\t\t\tCLASS=" + planet.getClassification().id + "\n");
			bw.write("\t\t\tPROD_BASE=" + planet.getBaseProd() + "\n");
			bw.write("\t\t\tPROD_MODIFIER=" + planet.getProdModifier() + "\n");
			bw.write("\t\t\tFOOD_BASE=" + planet.getBaseFood() + "\n");
			bw.write("\t\t\tFOOD_MODIFIER=" + planet.getFoodModifier() + "\n");
			bw.write("\t\t\tMONEY_BASE=" + planet.getBaseMoney() + "\n");
			bw.write("\t\t\tMONEY_MODIFIER=" + planet.getMoneyModifier() + "\n");
			bw.write("\t\t\tCULTURE_BASE=" + planet.getBaseCulture() + "\n");
			bw.write("\t\t\tCULTURE_MODIFIER=" + planet.getCultureModifier() + "\n");
			bw.write("\t\t\tSCIENCE_BASE=" + planet.getBaseScience() + "\n");
			bw.write("\t\t\tSCIENCE_MODIFIER=" + planet.getScienceModifier() + "\n");
			bw.write("\t\t\tPOPULATION=" + planet.getPopulation() + "\n");
			bw.write("\t\t\tPOPULATION_MAX=" + planet.getPopulationMax() + "\n");

			if (planet.getOwner() != null) {
				bw.write("\t\t\tOWNER=" + planet.getOwner().getId() + "\n");
			}

			bw.write("\t\t\tBEGIN BUILDINGS\n");
			for (BuildingModel building: planet.getStructures()) {
				saveBuilding(bw, building);
			}
			bw.write("\t\t\tEND BUILDINGS\n");

			bw.write("\t\tEND PLANET\n\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

//			if (structureItem != null) {
//				bw.write(x + "\t" + y + "\t" + structureItem.getType().ordinal() + "\t" + structureItem.getMatterSupply() + "\n");
//			}
//
//			if (userItem != null) {
//				bw.write(x + "\t" + y + "\t" + userItem.getType().ordinal() + "\t" + userItem.getMatterSupply() + "\n");
//			}
//
//			if (ressource != null) {
//				bw.write(x + "\t" + y + "\t" + ressource.getType().ordinal() + "\t" + ressource.getMatterSupply() + "\n");
//			}

		System.out.println("Save planet: " + planet.getName() + " done");
	}

	private static void saveBuilding (BufferedWriter bw, BuildingModel building) {
		try {
			bw.write(building.getType() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
