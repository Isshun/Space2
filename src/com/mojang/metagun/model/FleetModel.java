package com.mojang.metagun.model;

import java.util.ArrayList;
import java.util.List;

public class FleetModel {
	private List<ShipModel>	mShips;
	private PlayerModel mOwner;
	private double mSpeed;
	private TravelModel mTravel;
	private String mName;

	public FleetModel () {
		mSpeed = Double.MAX_VALUE;
		mShips = new ArrayList<ShipModel>();
		mName = "f1";
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
		return "go to nowhere asdsadasdsad";
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

	public List<ShipModel> getShip () {
		return mShips;
	}

}
