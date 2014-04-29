package org.bluebox.space2.game.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bluebox.space2.game.Game;
import org.bluebox.space2.game.model.FleetModel;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.PlayerModel;
import org.bluebox.space2.game.model.ShipClassModel;
import org.bluebox.space2.game.model.ShipModel;
import org.bluebox.space2.game.model.SystemModel;
import org.bluebox.space2.game.model.TravelModel;
import org.bluebox.space2.game.model.DeviceModel.Device;
import org.bluebox.space2.game.model.FleetModel.Action;
import org.bluebox.space2.game.service.GameService;

public class AIColonize {

	public static void play(PlayerModel player) {
		int nbUnderDevPlanet = getNbPlanetUnderPopulationThresold(player, 0.8);
		if (nbUnderDevPlanet > 3) {
			System.out.println("debug: sub developped planet (" + nbUnderDevPlanet + ")");
			return;
		}
		
		ShipModel ship = getColonizer(player);
		if (ship == null) {
			buildColonizer(player);
			System.out.println("debug: no ship");
			return;
		}
		player.aiOrders.buildColonizer = false;
		
		SystemModel system = getSystemToColonize(player, 1);
		if (system == null) {
			System.out.println("debug: no system");
			return;
		}

		if (ship.getFleet().getLocation() != system) {
			if (ship.getFleet().getAction() == Action.NONE) {
				System.out.println("colonize: go to new system");
				ship.getFleet().setCourse(system);
			}
			return;
		}
		
		System.out.println("debug: startColonize, " + ship + ", " + system);
		System.out.println("debug: " + ship.getFleet().getAction());
		colonizeNewSystem(player, system, ship);
	}

	private static int getNbPlanetUnderPopulationThresold (PlayerModel player, double thresold) {
		int nbPlanetBellowPopulationThresold = 0;
		for (PlanetModel p: player.getPlanets()) {
			if ((float)p.getPopulation() / p.getPopulationMax() < thresold) {
				nbPlanetBellowPopulationThresold++;
			}
		}
		return nbPlanetBellowPopulationThresold;
	}

	private static void buildColonizer (PlayerModel player) {
		if (player.aiOrders.buildColonizer) {
			System.out.println("debug: colonizer already in queue");
			return;
		}
		
		ShipClassModel shipClass = null;
		for (ShipClassModel sc: GameService.getInstance().getShipClasses()) {
			if (sc.hasDevice(Device.COLONIZER)) {
				player.aiOrders.addShipToBuild(sc);
				player.aiOrders.buildColonizer = true;
				return;
			}
		}
	}

	private static SystemModel getSystemToColonize (PlayerModel player, int level) {
		if (level == 0) {
			return null;
		}
		
		// Get free system at proximity
		List<SystemModel> neighbors = new ArrayList<SystemModel>();
		for (TravelModel t: GameService.getInstance().getTraveLines()) {
			if (t.getTo().getOwner() == null && player.equals(t.getFrom().getOwner())) {
				neighbors.add(t.getTo());
			}
			if (t.getFrom().getOwner() == null && player.equals(t.getTo().getOwner())) {
				neighbors.add(t.getFrom());
			}
		}
		
		// Get best system
		SystemModel bestSystem = null;
		int bestIndice = 0;
		for (SystemModel s: neighbors) {
			int indice = s.getRicherPlanet().getIndice();
			if (indice > bestIndice) {
				bestSystem = s;
				bestIndice = indice;
			}
		}
		
		return bestSystem;
	}

	private static void colonizeNewSystem (PlayerModel player, SystemModel system, ShipModel ship) {
		if (ship.getFleet().getAction() != Action.NONE) {
			System.out.println(player.getName() + " 'ship already in action");
			return;
		}
		
		// Fleet on orbit -> colonize
		if (system.getFleets().contains(ship.getFleet())) {
			System.out.println(player.getName() + " colonize new system: " + system.getName());
			system.colonize(player);
			Game.notifyChange();
			return;
		}
		
		// Fleet away from system -> move to
		System.out.println(player.getName() + " go to colonize new system: " + system.getName());
		ship.getFleet().setCourse(system);
	}

	private static ShipModel getColonizer (PlayerModel player) {
		
		// Check fleets
		List<FleetModel> fleets = new ArrayList<FleetModel>(player.getFleets());
		for (FleetModel f: fleets) {
			for (ShipModel s: f.getShips()) {
				if (s.hasDevice(Device.COLONIZER)) {
					return s;
				}
			}
		}

		// Check docks
		Set<PlanetModel> planets = player.getPlanets();
		for (PlanetModel p: planets) {
			if (p.hasDock()) {
				for (ShipModel s: p.getDock().getShips()) {
					if (s.hasDevice(Device.COLONIZER)) {
						FleetModel f = new FleetModel(player);
						f.setLocation(p);
						f.addShip(s);
						p.getDock().getShips().remove(s);
						p.addFleet(f);
						return s;
					}
				}
			}
		}
		
		return null;
	}
}
