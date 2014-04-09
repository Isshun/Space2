package org.bluebox.space2.model;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.service.GameService;

public class DockModel extends BuildingModel {
	private List<ShipModel>	mShips;

	public DockModel() {
		super(GameService.getInstance().getBuildingClass(BuildingClassModel.Type.DOCK));
		mShips = new ArrayList<ShipModel>();
	}
	
	public void addShip(ShipModel ship) {
		mShips.add(ship);
	}

}
