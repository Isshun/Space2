package org.bluebox.space2.game.model;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.game.service.GameService;

public class DockModel extends StructureModel implements IShipCollectionModel {
	private List<ShipModel>		mShipsToBuild;
	private List<ShipModel>		mShips;

	public DockModel(PlanetModel planet) {
		super(GameService.getInstance().getBuildingClass(BuildingClassModel.Type.DOCK), planet);
		mShips = new ArrayList<ShipModel>();
		mShipsToBuild = new ArrayList<ShipModel>();
	}

	public List<ShipModel> getShipToBuild () { return mShipsToBuild; }
	
	public void addShip(ShipModel ship) {
		mShips.add(ship);
	}

	public List<ShipModel> getShips () {
		return mShips;
	}

	@Override
	public String getLocationName () {
		return mPlanet.getName();
	}

	@Override
	public int getNbShip () {
		return mShips.size();
	}

	@Override
	public ILocation getLocation () {
		return mPlanet;
	}

	@Override
	public boolean isDock () {
		return true;
	}

	@Override
	public void setCourse (ILocation system) {
	}

	public int getQueueDuration () {
		return 0;
	}

	public ShipModel buildShip(ShipTemplateModel sc) {
		ShipModel ship = new ShipModel(sc);
		ship.setLocation(mPlanet.getSystem());
		return buildShip(ship);
	}

	public ShipModel buildShip(ShipModel sc) {
		System.out.println("Add build: " + sc.getClassName());
		sc.setPlanet(mPlanet);
		mShipsToBuild.add(sc);
		return sc;
	}

	public void update () {
		// Dock has ship in todo list
		if (mShipsToBuild.size() > 0) {
			
			// Ship construction is done
			if (mShipsToBuild.get(0).build(mPlanet.getBuildCoef())) {
				addShip(mShipsToBuild.get(0));
				mShipsToBuild.remove(0);
			}
		}
	}
	
	public int getShipBuildRemainder () {
		int eta = 0;
		for (ShipModel s: mShipsToBuild) {
			eta += mPlanet.getBuildETA(s.getBuildRemain());
		}
		return eta;
	}

	public List<ShipModel> getBuildList () {
		return mShipsToBuild;
	}



}
