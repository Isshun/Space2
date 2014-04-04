package com.mojang.metagun.model;

import java.util.ArrayList;
import java.util.List;

public class FleetModel {
	private List<ShipModel>	mShips;
	private PlayerModel 		mOwner;
	private double 			mSpeed;
	private TravelModel 		mTravel;
	private String 			mName;
	private ILocation			mLocation;

	public FleetModel () {
		mSpeed = Double.MAX_VALUE;
		mShips = new ArrayList<ShipModel>();
		mName = "no-name";
	}

	public double getSpeed () {
		return mSpeed;
	}
	
	public void addShip(ShipModel ship) {
		mShips.add(ship);

		if (ship.getSpeed() < mSpeed) {
			mSpeed = ship.getSpeed();
		}
	}

	public void setTravel (TravelModel travel) {
		mTravel = travel;
	}
	
	public int getETA () {
		if (mTravel != null) {
			return (int)(mTravel.getLength() / mSpeed);
		}
		return -1;
	}

	public void setOwner (PlayerModel owner) {
		mOwner = owner;
	}

	public String getName () {
		return mName;
	}

	public void setName (String name) {
		mName = name;
	}

	public String getLocationName () {
		return mLocation.getName();
	}

	public int getNbShip () {
		return mShips.size();
	}

	public int getPower () {
		return 42;
	}

	public int getDefense () {
		return 42;
	}

	public List<ShipModel> getShips () {
		return mShips;
	}

	public void setLocation (PlanetModel planet) {
		if (mLocation != null) {
			mLocation.removeFleet(this);
		}
		mLocation = planet;
		planet.addFleet(this);
	}

	public void go (SystemModel system) {
		System.out.println("Go to " + system.getName());
		
		if (system.moveTo(this)) {
			setLocation(system.getCapital());
		}
		
		// Remove casualties
		List<FleetModel> destroyed = new ArrayList<FleetModel>();
		for (FleetModel f: system.getFleets()) {
			if (f.getNbShip() == 0) {
				destroyed.add(f);
			}
		}
		system.getFleets().removeAll(destroyed);
	}

	public PlayerModel getOwner () {
		return mOwner;
	}

}
