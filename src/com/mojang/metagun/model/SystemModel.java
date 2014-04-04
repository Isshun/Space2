package com.mojang.metagun.model;

import java.util.ArrayList;
import java.util.List;

import com.mojang.metagun.service.FightService;

public class SystemModel implements ILocation {
	private String 	mName;
	private int 		mPosX;
	private int 		mPosY;
	private List<PlanetModel> mPlanets;
	private PlayerModel mOwner;
	private int 		mType;
	private PlanetModel mCapital;

	public SystemModel (String name, int x, int y) {
		mPlanets = new ArrayList<PlanetModel>();
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFleet (FleetModel fleet) {
		// TODO Auto-generated method stub
		
	}
	
	public int attack(FleetModel fleet) {
		boolean attackerWin = false;
		for (PlanetModel planet: mPlanets) {
			if (planet.attack(fleet) == FightService.DEFENDER_WIN) {
				return FightService.DEFENDER_WIN;
			}
		}
		return FightService.ATTACKER_WIN;
	}

	public boolean moveTo (FleetModel fleet) {
		if (hasHostileForce(fleet.getOwner())) {
			return attack(fleet) == FightService.ATTACKER_WIN;
		}
		return true;
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

}
