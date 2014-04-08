package org.bluebox.space2.model;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.service.FightService;


public class SystemModel implements ILocation {
	private static int 			sCount;
	private String 				mName;
	private int 					mPosX;
	private int 					mPosY;
	private List<PlanetModel> 	mPlanets;
	private PlayerModel 			mOwner;
	private int 					mType;
	private PlanetModel 			mCapital;
	private List<FleetModel> 	mFleets;
	private int 					mId;

	public SystemModel (String name, int x, int y) {
		mId = sCount++;
		mPlanets = new ArrayList<PlanetModel>();
		mFleets = new ArrayList<FleetModel>();
		mName = name;
		mPosX = x;
		mPosY = y;
		mType = Math.min((int)(Math.random() * 7), 3);
	}

	public int getX () {
		return mPosX;
	}

	public int getY () {
		return mPosY;
	}

	public String getName () {
		return mName;
	}

	public List<PlanetModel> getPlanets () {
		return mPlanets;
	}

	public void addPlanet (PlanetModel planet) {
		planet.setSystem(this, mPlanets.size());
		mPlanets.add(planet);
	}

//	public void colonize (PlayerModel player, PlanetModel planet) {
//		if (mCapital == null) {
//			mCapital = planet;
//		}
//		planet.setOwner(player);
//		mOwner = player;
//	}

	public PlayerModel getOwner() {
		return mOwner;
	}
	
	public void setOwner (PlayerModel owner) {
		mOwner = owner;
	}

	public int getDistance (SystemModel s) {
		return (int)Math.sqrt(Math.pow(Math.abs(mPosX - s.getX()), 2) + Math.pow(Math.abs(mPosY - s.getY()), 2));
	}

	public int getType () {
		return mType;
	}

	public PlanetModel getCapital () {
		if (mCapital == null) {
			return mPlanets.get(0);
		}
		return mCapital;
	}

	public PlanetModel getRicherPlanet () {
		PlanetModel richerPlanet = null;
		int richer = -1;
		for (PlanetModel planet: mPlanets) {
			if (planet.getIndice() > richer) {
				richerPlanet = planet;
			}
		}
		return richerPlanet;
	}

	public boolean isFree () {
		return mOwner == null;
	}

	@Override
	public void removeFleet (FleetModel fleet) {
		mFleets.remove(fleet);
	}

	@Override
	public void addFleet (FleetModel fleet) {
		mFleets.add(fleet);
	}
	
	public int attack(FleetModel fleet) {
		boolean attackerWin = false;
		for (PlanetModel planet: mPlanets) {
			if (planet.attack(fleet) == FightService.DEFENDER_WIN) {
				System.out.println("system winner: defender");
				return FightService.DEFENDER_WIN;
			}
		}
		System.out.println("system winner: attacker");
		return FightService.ATTACKER_WIN;
	}

	public void moveTo (FleetModel fleet) {
		if (hasHostileForce(fleet.getOwner())) {
			int winner = attack(fleet);
			if (winner == FightService.ATTACKER_WIN) {
				fleet.setLocation(getCapital());
			}
		} else {
			fleet.setLocation(getCapital());
		}
	}

	private boolean hasHostileForce (PlayerModel player) {
		for (PlanetModel planet: mPlanets) {
			for (FleetModel fleet: planet.getOrbit()) {
				if (fleet.getOwner().getRelation(player) == RelationModel.RELATION_WAR) {
					return true;
				}
			}
		}
		return false;
	}

	public void setCapital (PlanetModel capital) {
		mCapital = capital;
	}

	public List<FleetModel> getFleets () {
		return mFleets;
	}

	public int getId () {
		return mId;
	}

}
