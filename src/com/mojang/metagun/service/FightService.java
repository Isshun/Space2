package com.mojang.metagun.service;

import java.util.ArrayList;
import java.util.List;

import com.mojang.metagun.model.FleetModel;
import com.mojang.metagun.model.ShipModel;

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
		
		// Fight
		for (ShipModel a: attacker.getShips()) {
			for (ShipModel d: defender.getShips()) {
				fight (d, a);
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
		
		// Remove attacker casualties
		destroyed.clear();
		for (ShipModel d: defender.getShips()) {
			if (d.getHull() == 0) {
				destroyed.add(d);
			}
		}
		defender.getShips().removeAll(destroyed);
		
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
			double damage = a.getAttackIndice() * diff * (Math.random() * 0.3 + 0.85);
			mAttackerDamageDone += d.damage(damage);
		}
		
		// Defender round
		{
			double diff = d.getAttackIndice() / a.getDefenseIndice();
			double damage = d.getAttackIndice() * diff * (Math.random() * 0.3 + 0.85);
			a.damage(damage);
			mDefenderDamageDone += d.damage(damage);
		}
		
	}

}
