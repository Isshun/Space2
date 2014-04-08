package org.bluebox.space2.model;

public interface ILocation {
	String getName();
	int getX();
	int getY();
	void removeFleet (FleetModel fleet);
	void addFleet (FleetModel fleet);
}
