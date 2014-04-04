package com.mojang.metagun.model;

import java.util.ArrayList;
import java.util.List;

public class SystemModel {

	private String 	mName;
	private int 		mPosX;
	private int 		mPosY;
	private List<PlanetModel> mPlanets;
	private PlayerModel mOwner;
	private int 		mType;

	public SystemModel (String name, int x, int y) {
		mPlanets = new ArrayList<PlanetModel>();
		mName = name;
		mPosX = x;
		mPosY = y;
		mType = (int)(Math.random() * 4);
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

}
