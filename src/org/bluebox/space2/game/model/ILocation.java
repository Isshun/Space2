package org.bluebox.space2.game.model;

import java.util.List;

public interface ILocation {
	String 				getName();
	int 					getX();
	int 					getY();
	void 					removeFleet(FleetModel fleet);
	void 					addFleet(FleetModel fleet);
	List<FleetModel> 	getFleets();
	int 					getId();
	ILocation			getPlanet();
	ILocation			getSystem();
}
