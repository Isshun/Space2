package org.bluebox.space2.game.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bluebox.space2.Utils;
import org.bluebox.space2.engine.screen.BaseScreenLayer;
import org.bluebox.space2.game.Game;
import org.bluebox.space2.game.GameData;
import org.bluebox.space2.game.model.BuildingClassModel.Type;
import org.bluebox.space2.game.service.FightService;
import org.bluebox.space2.game.service.GameService;

public class PlanetModel implements ILocation {
	private int 							mId;
	private String 						mName;
	private double 						mInitialTick;
	private PlanetClassModel 			mClass;
	private SystemModel 					mSystem;
	private int 							mPos;
	private int 							mPeople;
	private int 							mPeopleTotal;
	private double 						mBaseFood;
	private double 						mBaseProd;
	private double 						mBaseCulture;
	private double 						mBaseScience;
	private double 						mBaseMoney;
	private double 						mSatisfaction;
	private int 							mSize;
	private List<ShipModel>				mShipsToBuild;
	private List<StructureModel>		mStructures;
	private List<StructureModel>		mStructuresToBuild;
	private List<FleetModel> 			mFleets;
	private DockModel 					mDock;
	private PlayerModel 					mOwner;
	private double 						mProdModifier;
	private double 						mFoodModifier;
	private double 						mScienceModifier;
	private double 						mCultureModifier;
	private double 						mHapinessModifier;
	private double 						mMoneyModifier;

	public PlanetModel (int classId, int size) {
		mId = Utils.getUUID();
		mProdModifier = 1;
		mFoodModifier = 1;
		mMoneyModifier = 1;
		mScienceModifier = 1;
		mCultureModifier = 1;
		mHapinessModifier = 1;
		mShipsToBuild = new ArrayList<ShipModel>();
		mStructures = new ArrayList<StructureModel>();
		mStructuresToBuild = new ArrayList<StructureModel>();
		mFleets = new ArrayList<FleetModel>();
		mPeople = 1;
		mClass = PlanetClassModel.getFromId(classId);
		mSize = size;
		mPeopleTotal = 3 + mSize * 2;
		mBaseProd = 5 + Game.sRandom.nextInt(8);
		mBaseMoney = Game.sRandom.nextInt(10);
		mBaseScience = Game.sRandom.nextInt(10);
		mBaseCulture = Game.sRandom.nextInt(10);
		mBaseFood = Game.sRandom.nextInt(10);
		mSatisfaction = Game.sRandom.nextInt(100);
	}
	
	public List<ShipModel> getShipToBuild () { return mShipsToBuild; }
	public PlanetClassModel getClassification() { return mClass; }
	public SystemModel getSystem () { return mSystem; }
	public String getName() { return mName; }
	public String getShortName() { return getLatinNumber(mPos + 1); }
	public String getClassName() { return mClass.name; }
	public String getSizeName () { return PlanetSizeModel.getText(mSize); }
	public String getShortClassification () { return mClass.shortName; }
	public String getShortSizeName () { return PlanetSizeModel.getShortText(mSize); }
	public double getInitialTick () { return mInitialTick; }
	public double getBaseFood () { return mBaseFood; }
	public double getBaseCulture () { return mBaseCulture; }
	public double getBaseScience () { return mBaseScience; }
	public double getBaseProd () { return mBaseProd; }
	public double getBaseMoney() { return mBaseMoney; }
	public double getFoodModifier () { return mFoodModifier; }
	public double getCultureModifier () { return mCultureModifier; }
	public double getScienceModifier () { return mScienceModifier; }
	public double getProdModifier () { return mProdModifier; }
	public double getMoneyModifier () { return mMoneyModifier; }
	public double getFood () { return Math.ceil(mBaseFood * mFoodModifier * mPeople); }
	public double getProd () { return Math.ceil(mBaseProd * mProdModifier * mPeople); }
	public double getMoney () { return Math.ceil(mBaseMoney * mMoneyModifier * mPeople); }
	public double getCulture () { return Math.ceil(mBaseCulture * mCultureModifier * mPeople); }
	public double getScience () { return Math.ceil(mBaseScience * mScienceModifier * mPeople); }
	public double getSatisfation () { return mSatisfaction; }
	public int getPopulation () { return mPeople; }
	public int getSize () { return mSize; }
	public int getBuildETA (int buildValue) { return (int)(buildValue / mBaseProd * mPeople) + 1; }
	public int getId () { return mId; }

	public void setId (Integer id) { mId = id; }
	public void setName (String name) { mName = name; }
	public void setClass (PlanetClassModel planetClass) { mClass = planetClass; }
	public void setSize (int size) { mSize = size; }
	public void setInitialTick (double d) { mInitialTick = d; }
	public void setBaseFood (double value) { mBaseFood = value; }
	public void setBaseCulture (double value) { mBaseCulture = value; }
	public void setBaseProd (double value) { mBaseProd = value; }
	public void setBaseMoney (double value) { mBaseMoney = value; }
	public void setBaseScience (double value) { mBaseScience = value; }
	public void setProdModifier (Double prodModifier) { mProdModifier = prodModifier; }
	public void setFoodModifier (Double foodModifier) { mFoodModifier = foodModifier; }
	public void setMoneyModifier (Double moneyModifier) { mMoneyModifier = moneyModifier; }
	public void setScienceModifier (Double scienceModifier) { mScienceModifier = scienceModifier; }
	public void setCultureModifier (Double cultureModifier) {mCultureModifier = cultureModifier; }

	public void addProdModifier (double value) { mProdModifier += value; }
	public void addMoneyModifier (double value) { mMoneyModifier += value; }
	public void addFoodModifier (double value) { mFoodModifier += value; }
	public void addScienceModifier (double value) { mScienceModifier += value; }
	public void addCultureModifier (double value) { mCultureModifier += value; }
	public void addHapinessModifier (double value) { mHapinessModifier += value; }

	@Override
	public List<FleetModel> getFleets () {
		return mFleets;
	}

	private static int getRandomPlanetClass () {
		int totalRand = 0;
		
		for (PlanetClassModel pc: PlanetClassModel.sClass) {
			totalRand += pc.rand;
		}
		
		int r = (int)(Game.sRandom.nextInt(totalRand));
		int sum = 0;
		for (PlanetClassModel pc: PlanetClassModel.sClass) {
			if (r <= sum + pc.rand) {
				return pc.id;
			}
			sum += pc.rand;
		}
		
		return 0;
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

	public void setClassification (int classIndex) {
		for (PlanetClassModel c: PlanetClassModel.sClass) {
			if (c.id == classIndex) {
				mClass = c;
			}
		}
	}

	public void buildShip(ShipClassModel sc) {
		buildShip(new ShipModel(sc));
	}

	public void buildShip(ShipModel sc) {
		if (mDock == null) {
			System.out.println("addBuild: planet has no spacedock");
		}
		
		System.out.println("Add build: " + sc.getClassName());
		sc.setPlanet(this);
		mShipsToBuild.add(sc);
	}

	public void update () {
		// Planet has ship in todo list
		if (mShipsToBuild.size() > 0) {
			
			// Ship construction is done
			if (mShipsToBuild.get(0).build(mBaseProd * mPeople)) {
				mDock.addShip(mShipsToBuild.get(0));
				mShipsToBuild.remove(0);
			}
		}
		
		// Planet has structure in todo list
		if (mStructuresToBuild.size() > 0) {
			
			// Ship construction is done
			if (mStructuresToBuild.get(0).build(mBaseProd * mPeople)) {
				addStructure(mStructuresToBuild.get(0));
				mStructuresToBuild.remove(0);
			}
		}
		
	}

	public void setOwner (PlayerModel owner) {
		if (mOwner != owner) {
			mOwner = owner;
			owner.addPlanet(this);
		}
	}

	public int getIndice () {
		double classIndice = 1;
		switch (mClass.id) {
		case PlanetClassModel.CLASS_M:
			classIndice = 6;
			break;
		case PlanetClassModel.CLASS_K:
		case PlanetClassModel.CLASS_K_LOW_OXYGEN:
		case PlanetClassModel.CLASS_K_OCEAN:
			classIndice = 4;
			break;
		case PlanetClassModel.CLASS_L:
		case PlanetClassModel.CLASS_L_DESERT:
		case PlanetClassModel.CLASS_L_JUNGLE:
			classIndice = 2;
			break;
		}
		//return (int)(mSize / 4);
		return (int)((mBaseProd + mBaseCulture + mBaseFood + mBaseMoney + mBaseScience) * mSize * classIndice);
	}

	public boolean isFree () {
		return mOwner == null;
	}

	public void setPeople (int people) {
		mPeople = people;
	}

	@Override
	public int getX () {
		return mSystem.getX();
	}

	@Override
	public int getY () {
		return mSystem.getY();
	}

	@Override
	public void removeFleet (FleetModel fleet) {
		mFleets.remove(fleet);
		mSystem.removeFleet(fleet);
	}

	@Override
	public void addFleet (FleetModel fleet) {
		mFleets.add(fleet);
		mSystem.addFleet(fleet);
	}
	
	public void colonize (PlayerModel player) {
		setOwner(player);
		mSystem.setOwner(player);
	}

	public DockModel getDock () {
		return mDock;
	}

	public static PlanetModel create (int pos) {
		int classId = getRandomPlanetClass();
		
		int size = 0;
		if (pos <= 1) {
			size = (int)(Game.sRandom.nextInt(3));
		} else {
			size = (int)(Game.sRandom.nextInt(5));
		}
		
		PlanetModel planet = new PlanetModel(classId, size);

		return planet;
	}

	public PlayerModel getOwner () {
		return mOwner;
	}

	public void buildStructure(BuildingClassModel.Type type) {
		
		// Structure to build already contains this building
		for (StructureModel building: mStructuresToBuild) {
			if (building.getType().equals(type)) {
				return;
			}
		}

		// Structure already contains this building
		for (StructureModel building: mStructures) {
			if (building.getType().equals(type)) {
				return;
			}
		}
		
		if (mDock == null && type == BuildingClassModel.Type.DOCK) {
			mDock = new DockModel(this);
		}
		
		mStructuresToBuild.add(GameService.getInstance().createStructure(type, this));
	}

	public void addStructure(BuildingClassModel.Type type) {
		addStructure(GameService.getInstance().createStructure(type, this));
	}

	public void addStructure(StructureModel structure) {
		if (mDock == null && structure.getType() == BuildingClassModel.Type.DOCK) {
			mDock = new DockModel(this);
		}
		
		structure.addEffect(mOwner, this);
		
		mStructures.add(structure);
	}

	public int getPopulationMax () {
		return mPeopleTotal;
	}

	public int getShipBuildRemainder () {
		int eta = 0;
		for (ShipModel s: mShipsToBuild) {
			eta += getBuildETA(s.getBuildRemain());
		}
		return eta;
	}

	public boolean hasBuilding (Type type) {
		for (StructureModel building: mStructures) {
			if (building.getType().equals(type)) {
				return true;
			}
		}
		return false;
	}

	public List<StructureModel> getStructuresToBuild () {
		return mStructuresToBuild;
	}

	public boolean hasDock () {
		return mDock != null;
	}

	public List<StructureModel> getStructures() { return mStructures; }
	
}
