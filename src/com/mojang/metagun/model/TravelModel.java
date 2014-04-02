package com.mojang.metagun.model;

public class TravelModel {
	private SystemModel 	mFrom;
	private SystemModel 	mTo;
	private FleetModel	mFleet;

	public TravelModel (FleetModel fleet, SystemModel from, SystemModel to) {
		mFleet = fleet;
		mFrom = from;
		mTo = to;
	}

	public int getX () {
		return (mFrom.getX() + mTo.getX()) / 2;
	}

	public int getY () {
		return (mFrom.getY() + mTo.getY()) / 2;
	}

	public SystemModel getFrom () {
		return mFrom;
	}

	public SystemModel getTo() {
		return mTo;
	}

	public int getAngle () {
		double offsetX = mFrom.getX() - mTo.getX();
		double offsetY = mFrom.getY() - mTo.getY();
		int angle = (int)(180 * Math.atan(offsetY / offsetX) / Math.PI);
		return angle;
	}
}
