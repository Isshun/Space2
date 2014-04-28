package org.bluebox.space2.game.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bluebox.space2.engine.screen.BaseScreenLayer;
import org.bluebox.space2.game.Game;
import org.bluebox.space2.game.model.BuildingClassModel.Type;
import org.bluebox.space2.game.service.FightService;
import org.bluebox.space2.game.service.GameService;


public class PlanetModel implements ILocation {
	private String 						mName;
	private double 						mInitialTick;
	private PlanetClassModel 			mClass;
	private SystemModel 					mSystem;
	private int 							mPos;
	private int 							mPeople;
	private int 							mPeopleTotal;
	private double 						mBaseFood;
	private double 						mBaseBuild;
	private double 						mBaseCulture;
	private double 						mBaseScience;
	private double 						mBaseMoney;
	private double 						mSatisfaction;
	private int 							mSize;
	private List<ShipModel>				mShipsToBuild;
	private List<BuildingModel>		mBuildings;
	private List<BuildingModel>		mStructuresToBuilds;
	private List<FleetModel> 			mFleets;
	private DockModel 					mDock;
	private PlayerModel 					mOwner;

	public PlanetModel (int classId, int size) {
		mShipsToBuild = new ArrayList<ShipModel>();
		mBuildings = new ArrayList<BuildingModel>();
		mStructuresToBuilds = new ArrayList<BuildingModel>();
		mFleets = new ArrayList<FleetModel>();
		mPeople = 1;
		mClass = PlanetClassModel.getFromId(classId);
		mSize = size;
		mPeopleTotal = 3 + mSize * 2;
		mBaseBuild = 5 + Game.sRandom.nextInt(8);
		mBaseMoney = Game.sRandom.nextInt(10);
		mBaseScience = Game.sRandom.nextInt(10);
		mBaseCulture = Game.sRandom.nextInt(10);
		mBaseFood = Game.sRandom.nextInt(10);
		mSatisfaction = Game.sRandom.nextInt(100);
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

	public List<ShipModel> getBuilds () {
		return mShipsToBuild;
	}

	public void update () {
		// Planet has ship in todo list
		if (mShipsToBuild.size() > 0) {
			
			// Ship construction is done
			if (mShipsToBuild.get(0).build(mBaseBuild * mPeople)) {
				mDock.addShip(mShipsToBuild.get(0));
				mShipsToBuild.remove(0);
			}
		}
		
		// Planet has structure in todo list
		if (mStructuresToBuilds.size() > 0) {
			
			// Ship construction is done
			if (mStructuresToBuilds.get(0).build(mBaseBuild * mPeople)) {
				mBuildings.add(mStructuresToBuilds.get(0));
				mStructuresToBuilds.remove(0);
			}
		}
		
	}

	public List<FleetModel> getOrbit () {
		return mFleets;
	}

	public int getBuildETA (int buildValue) {
		return (int)(buildValue / mBaseBuild * mPeople) + 1;
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
		return (int)((mBaseBuild + mBaseCulture + mBaseFood + mBaseMoney + mBaseScience) * mSize * classIndice);
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

	public int attack (FleetModel attacker) {
		for (FleetModel defender: mFleets) {
			if (attacker.getOwner().getRelation(defender.getOwner()) == RelationModel.RELATION_WAR) {
				if (FightService.getInstance().fight(defender, attacker) == FightService.DEFENDER_WIN) {
					removeDestroyedFleet();
					return FightService.DEFENDER_WIN;
				}
			}
		}

		return FightService.ATTACKER_WIN;
	}
	
	public void colonize (PlayerModel player) {
		setOwner(player);
		mSystem.setOwner(player);
	}

	private void removeDestroyedFleet () {
		List<FleetModel> destroyed = new ArrayList<FleetModel>();
		for (FleetModel defender: mFleets) {
			if (defender.getNbShip() == 0) {
				destroyed.add(defender);
			}
		}
		mFleets.removeAll(destroyed);
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
		if (mDock == null && type == BuildingClassModel.Type.DOCK) {
			mDock = new DockModel(this);
		}
		
		mStructuresToBuilds.add(GameService.getInstance().createBuilding(type, this));
	}

	public void addStructure(BuildingClassModel.Type type) {
		if (mDock == null && type == BuildingClassModel.Type.DOCK) {
			mDock = new DockModel(this);
		}
		
		mBuildings.add(GameService.getInstance().createBuilding(type, this));
	}

	public int getTotalPeople () {
		return mPeopleTotal;
	}

	@Override
	public List<FleetModel> getFleets () {
		return mFleets;
	}

	public int getShipBuildRemainder () {
		int eta = 0;
		for (ShipModel s: mShipsToBuild) {
			eta += getBuildETA(s.getBuildRemain());
		}
		return eta;
	}

	public boolean hasBuilding (Type type) {
		for (BuildingModel building: mBuildings) {
			if (building.getType().equals(type)) {
				return true;
			}
		}
		return false;
	}

	public List<BuildingModel> getStructuresToBuild () {
		return mStructuresToBuilds;
	}

	public boolean hasDock () {
		return mDock != null;
	}

	public List<BuildingModel> getStructures() {
		return mBuildings;
	}

}
