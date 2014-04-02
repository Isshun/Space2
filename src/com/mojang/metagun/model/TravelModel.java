package com.mojang.metagun.model;

import java.util.ArrayList;
import java.util.List;

public class TravelModel {
	private SystemModel 			mFrom;
	private SystemModel 			mTo;
	private int 					mLength;
	private List<FleetModel>	mFleets;

	public TravelModel (SystemModel from, SystemModel to) {
		mFleets = new ArrayList<FleetModel>();
		mFrom = from;
		mTo = to;
		mLength = (int)Math.sqrt(Math.pow(Math.abs(mFrom.getX() - mTo.getX()), 2) + Math.pow(Math.abs(mFrom.getY() - mTo.getY()), 2));
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

	public int getLength () {
		return mLength;
	}

	public void addFleet (FleetModel fleet) {
		fleet.setTravel(this);
		mFleets.add(fleet);
	}

	public List<FleetModel> getFleet () {
		return mFleets;
	}

	public int getNbFleet () {
		return mFleets.size();
	}
}
