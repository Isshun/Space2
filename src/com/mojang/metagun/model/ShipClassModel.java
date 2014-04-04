package com.mojang.metagun.model;

public class ShipClassModel {
	private String mName;

	public ShipClassModel (String name) {
		mName = name;
	}

	public String getName () {
		return mName;
	}

	public boolean build (double value) {
		return false;
	}
}
