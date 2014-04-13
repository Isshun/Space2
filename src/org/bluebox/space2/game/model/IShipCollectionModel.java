package org.bluebox.space2.game.model;

import java.util.List;

public interface IShipCollectionModel {

	List<ShipModel> getShips ();
	void 			setCourse (SystemModel system);
	void 			addShip (ShipModel ship);
	String 		getLocationName ();
	String		getName ();
	int 			getNbShip ();
	ILocation 	getLocation ();
	boolean 		isDock ();
}
