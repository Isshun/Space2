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
	private int 		mHullBase;
	private double 	mPhaserPower;
	private double 	mTorpedoPower;
	private int 		mTotalBuild;
	private int			mBuild;
	private PlanetModel		mPlanet;
	private SystemModel 		mSystem;
	private ShipClassModel 	mShipClass;
	
	public ShipModel(ShipClassModel shipClass) {
		mSpeed = 42;
		mMass = (int)(Math.random() * 100);
		mShipClass = shipClass;
		mArmory = (int)(Math.random() * 100);
		mShieldPower = (int)(Math.random() * 100);
		mTotalBuild = shipClass.getBuildValue();
		mGlobalIndice = (int)(Math.random() * 100);
		mAttackIndice = (int)(Math.random() * 100);
		mDefenseIndice = (int)(Math.random() * 100);
		mCrew = (int)(Math.random() * 100);
		mTotalCrew = 100;
		mHull = mHullBase = 250;
		mPhaserPower = (int)(Math.random() * 100);
		mTorpedoPower = (int)(Math.random() * 100);
	}

	public double			getSpeed () { return mSpeed; }
	public double 			getIndice () { return mGlobalIndice; }
	public double 			getAttackIndice () { return mAttackIndice; }
	public double 			getDefenseIndice () { return mDefenseIndice; }
	public int 				getMass () { return mMass; }
	public int 				getCrew () { return mCrew; }
	public int 				getTotalCrew () { return mTotalCrew; }
	public int 				getHull () { return mHull; }
	public int 				getHullBase () { return mHullBase; }
	public double			getHullRatio () { return (double)mHull / mHullBase; }
	public double 			getPhaserPower () { return mPhaserPower; }
	public double 			getTorpedoPower () { return mTorpedoPower; }
	public double 			getVelocity () { return mSpeed; }
	public String 			getClassName () { return mShipClass.getName(); }
	public double 			getShieldPower () { return mShieldPower; }
	public double 			getArmory () { return mArmory; }
	public String 			getSpecialDeviceName () { return "none"; }
	public int 				getBuildRemain () { return mTotalBuild - mBuild; }
	public PlanetModel 	getPlanet () { return mPlanet; }
	public SystemModel 	getSystem () { return mSystem; }

	public boolean build (double value) {
		mBuild = (int)Math.min(mBuild + value, mTotalBuild);
		return mBuild == mTotalBuild;
	}

	public void setPlanet (PlanetModel planet) {
		mPlanet = planet;
		mSystem = planet.getSystem();
	}

	public int getBuildETA () {
		return (int)(getBuildRemain() / (mPlanet.getBuild())) + 1;
	}

	public double damage (double damage) {
		if (mHull < damage) {
			mHull = 0;
			return damage - mHull;
		}
		mHull -= damage;
		return damage;
	}
}
