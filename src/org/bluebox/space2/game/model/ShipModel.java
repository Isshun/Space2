package org.bluebox.space2.game.model;

import java.util.List;

import org.bluebox.space2.game.model.DeviceModel.Device;


public class ShipModel implements IBuilding  {
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
	private double				mBuild;
	private PlanetModel		mPlanet;
	private SystemModel 		mSystem;
	private ShipClassModel 	mShipClass;

	private FleetModel mFleet;
	
	public ShipModel(ShipClassModel shipClass) {
		mSpeed = 42;
		mMass = shipClass.getMass();
		mShipClass = shipClass;
		mArmory = shipClass.getArmory();
		mShieldPower = shipClass.getShieldPower();
		mTotalBuild = shipClass.getBuildValue();
		mTotalCrew = mCrew = shipClass.getTotalCrew();
		mHull = mHullBase = shipClass.getHull();
		mPhaserPower = shipClass.getPhaserPower();
		mTorpedoPower = shipClass.getTorpedoPower();
		
		mAttackIndice = mPhaserPower + mTorpedoPower;
		mDefenseIndice = mShieldPower + mArmory;
		mGlobalIndice = mAttackIndice + mDefenseIndice;
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
	public int 				getBuildRemain () { return (int)(mTotalBuild - mBuild); }
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
		return (int)(getBuildRemain() / (mPlanet.getProd())) + 1;
	}

	public double damage (double damage) {
		if (mHull < damage) {
			mHull = 0;
			return damage - mHull;
		}
		mHull -= damage;
		return damage;
	}

	public boolean hasDevice (Device device) {
		return mShipClass.hasDevice(device);
	}

	public FleetModel getFleet () {
		return mFleet;
	}

	public void setFleet (FleetModel fleet) {
		mFleet = fleet;
	}

	public ShipClassModel getShipClass () {
		return mShipClass;
	}
}
