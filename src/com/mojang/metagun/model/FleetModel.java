package com.mojang.metagun.model;

import java.util.ArrayList;
import java.util.List;

public class FleetModel {
	private List<ShipModel>	mShips;
	private PlayerModel mOwner;
	private double mSpeed;
	private TravelModel mTravel;

	public FleetModel (PlayerModel owner) {
		mOwner = owner;
		mSpeed = Double.MAX_VALUE;
		mShips = new ArrayList<ShipModel>();
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

}
