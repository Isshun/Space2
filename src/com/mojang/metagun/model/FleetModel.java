package com.mojang.metagun.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.mojang.metagun.path.Vertex;
import com.mojang.metagun.service.PathResolver;

public class FleetModel {
	private static int 		sCount;

	private List<ShipModel>	mShips;
	private PlayerModel 		mOwner;
	private double 			mSpeed;
	private TravelModel 		mTravel;
	private String 			mName;
	private ILocation			mLocation;

	private LinkedList<Vertex> mPath;

	public FleetModel () {
		mSpeed = Double.MAX_VALUE;
		mShips = new ArrayList<ShipModel>();
		mName = NameGenerator.generate(NameGenerator.KLINGON, sCount++);
	}

	public FleetModel (String name) {
		mSpeed = Double.MAX_VALUE;
		mShips = new ArrayList<ShipModel>();
		mName = name;
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
		
		system.moveTo(this);
		
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

	public void move () {
		if (mPath != null && mPath.size() > 0) {
			Vertex v = mPath.pollFirst();
			go (v.getSystem());
		}
	}

	public void setCourse (SystemModel goal) {
		if (mLocation instanceof PlanetModel) {
			LinkedList<Vertex> path = PathResolver.getInstance().getPath(((PlanetModel)mLocation).getSystem(), goal);
			
			if (path != null) {
				mPath = path;
				move();
			}
		}
	}

}
