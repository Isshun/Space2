package com.mojang.metagun.model;

public class ShipClassModel {
	private String 	mName;
	private int 		mBuildValue;

	public ShipClassModel (String name) {
		mName = name;
	}

	public String getName () {
		return mName;
	}

	public boolean build (double value) {
		return false;
	}

	public void setBuildValue (int buildValue) {
		mBuildValue = buildValue;
	}

	public int getBuildValue () {
		return mBuildValue;
	}
}
