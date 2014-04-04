package com.mojang.metagun.model;

import java.util.ArrayList;
import java.util.List;


public class PlanetModel {
	private String mName;
	private double mInitialTick;
	private PlanetClassModel mClass;
	private SystemModel mSystem;
	private int mPos;
	private int mPeople;
	private double mBaseFood;
	private double mBaseBuild;
	private double mBaseCulture;
	private double mBaseScience;
	private double mBaseMoney;
	private double mSatisfaction;
	private int mSize;
	private List<ShipModel>	mBuilds;

	public PlanetModel () {
		mBuilds = new ArrayList<ShipModel>();
		mClass = getRandomPlanetClass();
		mSize = (int)(Math.random() * 5);
		mPeople = 1;
		mBaseBuild = Math.random() * 10;
		mBaseMoney = Math.random() * 10;
		mBaseScience = Math.random() * 10;
		mBaseCulture = Math.random() * 10;
		mBaseFood = Math.random() * 10;
		mSatisfaction = Math.random() * 100;
	}

	private PlanetClassModel getRandomPlanetClass () {
		int totalRand = 0;
		
		for (PlanetClassModel pc: PlanetClassModel.sClass) {
			totalRand += pc.rand;
		}
		
		int r = (int)(Math.random() * totalRand);
		int sum = 0;
		for (PlanetClassModel pc: PlanetClassModel.sClass) {
			if (r <= sum + pc.rand) {
				return pc;
			}
			sum += pc.rand;
		}
		
		return null;
	}

	public String getName() {
		return mName;
	}

	public String getShortName() {
		return getLatinNumber(mPos + 1);
	}

	public String getClassName() {
		return mClass.name;
	}

	public PlanetClassModel getClassification() {
		return mClass;
	}

	public double getInitialTick () {
		return mInitialTick;
	}

	public void setInitialTick (double d) {
		mInitialTick = d;
	}

	public String getSizeName () {
		return PlanetSizeModel.getText(mSize);
	}

	public void setSystem (SystemModel system, int pos) {
		mSystem = system;
		mPos = pos;
		mName = system.getName() + "-" + getLatinNumber(pos + 1);
	}

	private String getLatinNumber (int nb) {
		switch (nb) {
		case 1: return "I";
		case 2: return "II";
		case 3: return "III";
		case 4: return "IV";
		case 5: return "V";
		case 6: return "VI";
		case 7: return "VII";
		case 8: return "VII";
		case 9: return "IX";
		}
		return null;
	}

	public String getShortClassification () {
		return mClass.shortName;
	}

	public String getShortSizeName () {
		return PlanetSizeModel.getShortText(mSize);
	}

	public double getBaseFood () {
		return mBaseFood;
	}

	public double getBaseCulture () {
		return mBaseCulture;
	}

	public double getBaseScience () {
		return mBaseScience;
	}

	public double getBaseBuild () {
		return mBaseBuild;
	}

	public double getBaseMoney() {
		return mBaseMoney;
	}

	public int getPeople () {
		return mPeople;
	}

	public double getFood () {
		return Math.ceil(mBaseFood * mPeople);
	}

	public double getBuild () {
		return Math.ceil(mBaseBuild * mPeople);
	}

	public double getMoney () {
		return Math.ceil(mBaseMoney * mPeople);
	}

	public double getCulture () {
		return Math.ceil(mBaseCulture * mPeople);
	}

	public double getScience () {
		return Math.ceil(mBaseScience * mPeople);
	}

	public SystemModel getSystem () {
		return mSystem;
	}

	public double getSatisfation () {
		return mSatisfaction;
	}

	public int getSize () {
		return mSize;
	}

	public void setClassification (int classIndex) {
		for (PlanetClassModel c: PlanetClassModel.sClass) {
			if (c.id == classIndex) {
				mClass = c;
			}
		}
	}

	public void setSize (int size) {
		mSize = size;
	}

	public void addBuild (ShipModel sc) {
		System.out.println("Add build: " + sc.getClassName());
		sc.setPlanet(this);
		mBuilds.add(sc);
	}

	public List<ShipModel> getBuilds () {
		return mBuilds;
	}

	public void update () {
		if (mBuilds.size() > 0 && mBuilds.get(0).build(mBaseBuild * mPeople)) {
			mBuilds.remove(0);
		}
	}

}
