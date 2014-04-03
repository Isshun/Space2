package com.mojang.metagun.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mojang.metagun.Art;

public class PlayerModel {
	
	private String 				mName;
	private TextureRegion 		mFlag;
	private Color					mColor;
	private List<SystemModel> 	mSystems;
	private List<PlanetModel> 	mPlanets;
	
	public PlayerModel(String name) {
		mName = name;
		mFlag = Art.flags[(int)(Math.random() * 9)];
		mSystems = new ArrayList<SystemModel>();
		mPlanets = new ArrayList<PlanetModel>();
		mColor = new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 1);
	}

	public void addSystem (SystemModel system) {
		mSystems.add(system);
		system.setOwner(this);
		for (PlanetModel planet: system.getPlanets()) {
			mPlanets.add(planet);
		}
	}

	public List<PlanetModel> getPlanets () {
		return mPlanets;
	}
	
	public List<SystemModel> getSystems () {
		return mSystems;
	}

	public Color getColor() {
		return mColor;
	}

	public String getName () {
		return mName;
	}

	public TextureRegion getFlag () {
		return mFlag;
	}

	public String getRelation () {
		return "war";
	}
	
}
