package com.mojang.metagun.model;

import java.util.List;

public class ShipModel {
	private List<ShipDeviceModel>	mDevices;
	private double mSpeed;
	
	public ShipModel() {
		mSpeed = 42;
	}

	public double getSpeed () {
		return mSpeed;
	}
}
