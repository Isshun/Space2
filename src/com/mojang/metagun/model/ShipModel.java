package com.mojang.metagun.model;

import java.util.List;

public class ShipModel {
	private List<ShipDeviceModel>	mDevices;

	private double 	mSpeed;
	private int 		mMass;
	private double 	mArmory;
	private double 	mShieldPower;
	private double 	mGlobalIndice;
	private double 	mAttackIndice;
	private double 	mDefenseIndice;
	private int 		mCrew;
	private int 		mTotalCrew;
	private int 		mHull;
	private int 		mTotalHull;
	private double 	mPhaserPower;
	private double 	mTorpedoPower;
	
	public ShipModel() {
		mSpeed = 42;
		mMass = (int)(Math.random() * 100);
		mArmory = (int)(Math.random() * 100);
		mShieldPower = (int)(Math.random() * 100);
		mGlobalIndice = (int)(Math.random() * 100);
		mAttackIndice = (int)(Math.random() * 100);
		mDefenseIndice = (int)(Math.random() * 100);
		mCrew = (int)(Math.random() * 100);
		mTotalCrew = 100;
		mHull = (int)(Math.random() * 250);
		mTotalHull = 250;
		mPhaserPower = (int)(Math.random() * 100);
		mTorpedoPower = (int)(Math.random() * 100);
	}

	public double getSpeed () {
		return mSpeed;
	}

	public double getIndice () {
		return mGlobalIndice;
	}

	public double getAttackIndice () {
		return mAttackIndice;
	}

	public double getDefenseIndice () {
		return mDefenseIndice;
	}

	public int getMass () {
		return mMass;
	}

	public int getCrew () {
		return mCrew;
	}

	public int getTotalCrew () {
		return mTotalCrew;
	}

	public int getHull () {
		return mHull;
	}

	public int getTotalHull () {
		return mTotalHull;
	}

	public double getPhaserPower () {
		return mPhaserPower;
	}

	public double getTorpedoPower () {
		return mTorpedoPower;
	}

	public double getVelocity () {
		return mSpeed;
	}

	public String getClassName () {
		return "fighter";
	}

	public double getShieldPower () {
		return mShieldPower;
	}

	public double getArmory () {
		return mArmory;
	}
}
