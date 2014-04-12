package org.bluebox.space2.game.model;

import java.util.ArrayList;
import java.util.List;

public class AIOrders {

	private List<ShipClassModel> mShipToBuilds;
	public boolean buildColonizer;

	public AIOrders() {
		mShipToBuilds = new ArrayList<ShipClassModel>();
	}
	
	public void addShipToBuild (ShipClassModel sc) {
		mShipToBuilds.add(sc);
	}

	public List<ShipClassModel> getShipToBuild () {
		return mShipToBuilds;
	}

}
