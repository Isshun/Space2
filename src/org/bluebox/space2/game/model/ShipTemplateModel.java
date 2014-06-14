package org.bluebox.space2.game.model;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.Utils;
import org.bluebox.space2.game.model.DeviceModel.Device;


public class ShipTemplateModel {
	private int 					mId;
	private String 				mName;
	private int 					mBuildValue;
	private int 					mMass;
	private double 				mArmory;
	private double 				mShieldPower;
	private int 					mTotalCrew;
	private int 					mHull;
	private double 				mTorpedoPower;
	private double 				mPhaserPower;
	private List<DeviceModel>	mDevices;

	public ShipTemplateModel (String name, int hull) {
		mId = Utils.getUUID();
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
		mDevices.add(device);
		mPhaserPower += device.attack;
		mShieldPower += device.defense;
	}

	public boolean hasDevice (Device device) {
		for (DeviceModel d: mDevices) {
			if (d.id == device) {
				return true;
			}
		}
		return false;
	}

	public List<DeviceModel> getDevices () {
		return mDevices;
	}

	public void addDevice (Device deviceId) {
		addDevice(DeviceModel.get(deviceId));
	}

	public int getId () {
		return mId;
	}

	public void setId (int id) {
		mId = id;
	}
}
