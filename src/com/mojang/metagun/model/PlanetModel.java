package com.mojang.metagun.model;

public class PlanetModel {

	public static String sClassD = "Planetoid (D)";
	public static String sClassF = "Inert (F)";
	public static String sClassH = "Uninhabitable (H)";
	public static String sClassJ = "Gas giant (J)";
	public static String sClassK = "Adaptable (K)";
	public static String sClassL = "Hostile (L)";
	public static String sClassM = "Suitable (M)";
	public static String sClassN = "Sulfurique (N)";
	public static String sClassP = "Glaciated (P)";
	public static String sClassT = "Gas Giant (T)";
	public static String sClassY = "Deamon (Y)";

	private String mName;
	private double mInitialTick;
	private String mClass;
	private SystemModel mSystem;
	private int mPos;
	private int mPeople;
	private double mBaseFood;
	private double mBaseBuild;
	private double mBaseCulture;
	private double mBaseScience;
	private double mBaseMoney;

	public PlanetModel () {
		mClass = sClassM;
		mPeople = 1;
		mBaseBuild = Math.random() * 10;
		mBaseMoney = Math.random() * 10;
		mBaseScience = Math.random() * 10;
		mBaseCulture = Math.random() * 10;
		mBaseFood = Math.random() * 10;
	}

	public String getName() {
		return mName;
	}

	public String getShortName() {
		return getLatinNumber(mPos + 1);
	}

	public String getClassification() {
		return mClass;
	}

	public double getInitialTick () {
		return mInitialTick;
	}

	public void setInitialTick (double d) {
		mInitialTick = d;
	}

	public String getSizeName () {
		return "Medium";
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
		case 3: return "II";
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
		return "M";
	}

	public String getShortSizeName () {
		return "MED";
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

}
