package org.bluebox.space2.game.model;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.game.service.GameService;

public class DockModel extends BuildingModel implements IShipCollectionModel {
	private List<ShipModel>	mShips;

	public DockModel(PlanetModel planet) {
		super(GameService.getInstance().getBuildingClass(BuildingClassModel.Type.DOCK), planet);
		mShips = new ArrayList<ShipModel>();
	}
	
	public void addShip(ShipModel ship) {
		mShips.add(ship);
	}

	public List<ShipModel> getShips () {
		return mShips;
	}

	@Override
	public void setCourse (SystemModel system) {
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

}
