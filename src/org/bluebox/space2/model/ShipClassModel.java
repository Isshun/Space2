package org.bluebox.space2.model;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.model.DeviceModel.Device;


public class ShipClassModel {
	private String 	mName;
	private int 		mBuildValue;
	private int 		mMass;
	private double 	mArmory;
	private double 	mShieldPower;
	private int 		mTotalCrew;
	private int 		mHull;
	private double 	mTorpedoPower;
	private double 	mPhaserPower;
	private List<DeviceModel>	mDevices;

	public ShipClassModel (String name, int hull) {
		mName = name;
		mHull = hull;
		mTotalCrew = hull / 3;
		mDevices = new ArrayList<DeviceModel>();
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

	public int getMass () {
		return mMass;
	}

	public double getArmory () {
		return mArmory;
	}

	public double getShieldPower () {
		return mShieldPower;
	}

	public int getTotalCrew () {
		return mTotalCrew;
	}

	public int getHull () {
		return mHull;
	}

	public double getPhaserPower () {
		return mPhaserPower;
	}

	public double getTorpedoPower () {
		return mTorpedoPower;
	}

	public void addDevice (DeviceModel device) {
		mPhaserPower += device.attack;
		mShieldPower += device.defense;
	}
}
