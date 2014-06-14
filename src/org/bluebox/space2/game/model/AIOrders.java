package org.bluebox.space2.game.model;

import java.util.ArrayList;
import java.util.List;

public class AIOrders {

	private List<ShipTemplateModel> mShipToBuilds;
	public boolean buildColonizer;

	public AIOrders() {
		mShipToBuilds = new ArrayList<ShipTemplateModel>();
	}
	
	public void addShipToBuild (ShipTemplateModel sc) {
		mShipToBuilds.add(sc);
	}

	public List<ShipTemplateModel> getShipToBuild () {
		return mShipToBuilds;
	}

}
