package com.mojang.metagun.model;

public interface ILocation {
	String getName();
	int getX();
	int getY();
	void removeFleet (FleetModel fleet);
	void addFleet (FleetModel fleet);
}
