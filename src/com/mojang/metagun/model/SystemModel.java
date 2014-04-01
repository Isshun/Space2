package com.mojang.metagun.model;

import java.util.ArrayList;
import java.util.List;

public class SystemModel {

	private String 	mName;
	private int 		mPosX;
	private int 		mPosY;
	private List<PlanetModel> mPlanets;

	public SystemModel (String name, int x, int y) {
		mPlanets = new ArrayList<PlanetModel>();
		mName = name;
		mPosX = x;
		mPosY = y;
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

}
