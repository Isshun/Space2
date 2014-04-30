package org.bluebox.space2.game.service;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.game.Game;
import org.bluebox.space2.game.model.FleetModel;
import org.bluebox.space2.game.model.ILocation;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.PlayerRelationModel;
import org.bluebox.space2.game.model.ShipModel;
import org.bluebox.space2.game.model.SystemModel;


public class FightService {

	private static FightService sSelf;
	public final static int ATTACKER_WIN = 0;
	public final static int DEFENDER_WIN = 1;

	public static FightService getInstance () {
		return new FightService();
	}
	
	private double mAttackerDamageDone;
	private double mDefenderDamageDone;
	
	public int fight (FleetModel defender, FleetModel attacker) {
		assert defender != attacker;
		
		mAttackerDamageDone = 0;
		mDefenderDamageDone = 0;

		// Fight
		for (ShipModel a: attacker.getShips()) {
			for (ShipModel d: defender.getShips()) {
				fight (a, d);
			}
		}
		
		// Remove attacker casualties
		List<ShipModel> destroyed = new ArrayList<ShipModel>();
		for (ShipModel a: attacker.getShips()) {
			if (a.getHull() == 0) {
				destroyed.add(a);
			}
		}
		attacker.getShips().removeAll(destroyed);
		if (attacker.getShips().size() == 0) {
			attacker.getLocation().removeFleet(attacker);
			attacker.getOwner().removeFleet(attacker);
		}
		
		// Remove defender casualties
		destroyed.clear();
		for (ShipModel d: defender.getShips()) {
			if (d.getHull() == 0) {
				destroyed.add(d);
			}
		}
		defender.getShips().removeAll(destroyed);
		if (defender.getShips().size() == 0) {
			defender.getLocation().removeFleet(defender);
			defender.getOwner().removeFleet(defender);
		}
		
		// End
		if (mAttackerDamageDone > mDefenderDamageDone) {
			System.out.println("Winner: attacker (" + mAttackerDamageDone + " / " + mDefenderDamageDone + ")");
			return ATTACKER_WIN;
		} else {
			System.out.println("Winner: defender (" + mDefenderDamageDone + " / " + mAttackerDamageDone + ")");
			return DEFENDER_WIN;
		}
	}

	private void fight (ShipModel d, ShipModel a) {
		if (a.getHull() <= 0 || d.getHull() <= 0) {
			return;
		}

		// Attacker round
		{
			double diff = a.getAttackIndice() / d.getDefenseIndice();
			double damage = a.getAttackIndice() * diff * (Game.sRandom.nextFloat() * 0.3 + 0.85);
			mAttackerDamageDone += d.damage(damage);
		}
		
		// Defender round
		{
			double diff = d.getAttackIndice() / a.getDefenseIndice();
			double damage = d.getAttackIndice() * diff * (Game.sRandom.nextFloat() * 0.3 + 0.85);
			a.damage(damage);
			mDefenderDamageDone += d.damage(damage);
		}
		
	}

	public int attack(SystemModel system, FleetModel attacker) {
		System.out.println("\nFightService: " + attacker.getName() + " (" + attacker.getOwner().getName() + ") attack " + system.getName() + " (" + system.getOwner().getName() + ")");

		boolean attackerWin = false;
		
		// Attack system
		for (FleetModel defender: system.getFleets()) {
			if (attacker.getOwner().getRelation(defender.getOwner()) == PlayerRelationModel.RELATION_WAR) {
				if (fight(defender, attacker) == FightService.DEFENDER_WIN) {
					removeDestroyedFleet(system);
					System.out.println("system winner: defender");
					return FightService.DEFENDER_WIN;
				}
			}
		}
		
		// Attack planet
		for (PlanetModel planet: system.getPlanets()) {
			for (FleetModel defender: planet.getFleets()) {
				if (attacker.getOwner().getRelation(defender.getOwner()) == PlayerRelationModel.RELATION_WAR) {
					if (fight(defender, attacker) == FightService.DEFENDER_WIN) {
						removeDestroyedFleet(planet);
						System.out.println("system winner: defender");
						return FightService.DEFENDER_WIN;
					}
				}
			}
		}
		System.out.println("system winner: attacker");
		return ATTACKER_WIN;
	}

	private void removeDestroyedFleet (ILocation location) {
		List<FleetModel> destroyed = new ArrayList<FleetModel>();
		for (FleetModel fleet: location.getFleets()) {
			if (fleet.getNbShip() == 0) {
				destroyed.add(fleet);
			}
		}
		location.getFleets().removeAll(destroyed);
	}

}
