package org.bluebox.space2.game.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.Utils;
import org.bluebox.space2.game.GameData;

import com.badlogic.gdx.graphics.Color;

public class TravelModel implements ILocation {
	private int						mId;
	private SystemModel 			mFrom;
	private SystemModel 			mTo;
	private int 					mLength;
	private List<FleetModel>	mFleets;

	public TravelModel (SystemModel from, SystemModel to) {
		mId = Utils.getUUID();
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

	@Override
	public String getName () {
		return mFrom.getName() + " to " + mTo.getName();
	}

	@Override
	public void removeFleet (FleetModel fleet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<FleetModel> getFleets () {
		return mFleets;
	}

	public void save (BufferedWriter bw) {
		// TODO Auto-generated method stub
		
	}

	public static TravelModel load (BufferedReader br, GameData data) {
		SystemModel from = null;
		SystemModel to = null;
		int id = -1;
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				line = line.replace("\t", "");

				if ("END TRAVEL".equals(line)) {
					if (from != null && to != null) {
						TravelModel travel = new TravelModel(from, to);
						travel.setId(id);
						return travel;
					}
				}
				if (line.indexOf("FROM") == 0) { from = data.getSystemFromId(Integer.valueOf(line.substring(5))); }
				if (line.indexOf("TO") == 0) { to = data.getSystemFromId(Integer.valueOf(line.substring(3))); }
				if (line.indexOf("ID") == 0) { id = Integer.valueOf(line.substring(3)); }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return null;
	}

	private void setId (int id) {
		mId = id;
	}

	public int getId () {
		return mId;
	}

	@Override
	public ILocation getPlanet () {
		return null;
	}

	@Override
	public ILocation getSystem () {
		return null;
	}
}
