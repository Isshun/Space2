package org.bluebox.space2.game;

import java.util.ArrayList;
import java.util.List;

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

public class IA {

	private static IA sSelf;

	public static IA getInstance () {
		if (sSelf == null) {
			sSelf = new IA();
		}
		return sSelf;
	}

	public void play (PlayerModel player) {
		playColonize(player);
	}

	private void playColonize (PlayerModel player) {
		boolean startColonize = isStartColonize(player);
		ShipModel ship = getColonizer(player);
		SystemModel system = getSystemToColonize(player);
		System.out.println("debug: " + startColonize + ", " + ship + ", " + system);
		if (startColonize && ship != null && system != null) {
			System.out.println("debug: " + ship.getFleet().getAction());
			if (ship.getFleet().getAction() == Action.NONE) {
				colonizeNewSystem(player, system, ship);
			}
		}
	}

	private SystemModel getSystemToColonize (PlayerModel player) {
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

	private void colonizeNewSystem (PlayerModel player, SystemModel system, ShipModel ship) {
		System.out.println(player.getName() + " aim colonize: " + system.getName());
		
		// Fleet on orbit -> colonize
		if (system.getFleets().contains(ship.getFleet())) {
			system.colonize(player);
			Game.notifyChange();
		}
		
		// Fleet away from system -> move to
		else {
			ship.getFleet().setCourse(system);
		}
	}

	private ShipModel getColonizer (PlayerModel player) {
		List<FleetModel> fleets = player.getFleets();
		for (FleetModel f: fleets) {
			for (ShipModel s: f.getShips()) {
				if (s.hasDevice(Device.COLONIZER)) {
					return s;
				}
			}
		}
		return null;
	}

	// Colonize new planet if all planets - 2 has more of 80% population
	private boolean isStartColonize (PlayerModel player) {
		int nbPlanetBellowPopulationThresold = 0;
		for (PlanetModel p: player.getPlanets()) {
			if ((float)p.getPeople() / p.getTotalPeople() < 0.8) {
				nbPlanetBellowPopulationThresold++;
			}
		}
		System.out.println(player.getName() + " isStartColonize: " + (player.getPlanets().size() - 2 < 2) + " x " + (nbPlanetBellowPopulationThresold < 2));
		return player.getPlanets().size() - 2 < 2 || nbPlanetBellowPopulationThresold < 2;
	}
}
