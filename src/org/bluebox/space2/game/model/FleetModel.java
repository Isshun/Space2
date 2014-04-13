package org.bluebox.space2.game.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bluebox.space2.game.model.DeviceModel.Device;
import org.bluebox.space2.path.PathResolver;
import org.bluebox.space2.path.Vertex;


public class FleetModel implements IShipCollectionModel {
	public enum Action {
		NONE,
		MOVE
	}
	
	private List<ShipModel>	mShips;
	private PlayerModel 		mOwner;
	private double 			mSpeed;
	private TravelModel 		mTravel;
	private String 			mName;
	private ILocation			mLocation;

	private LinkedList<Vertex> mPath;

	private Action mAction;
	private double mAttackIndice;
	private double mDefenseIndice;

	public FleetModel (PlayerModel owner) {
		mAction = Action.NONE;
		mSpeed = Double.MAX_VALUE;
		mShips = new ArrayList<ShipModel>();
		mName = NameGenerator.generate(NameGenerator.KLINGON, owner.getFleets().size());
		mOwner = owner;
		owner.addFleet(this);
	}

	public FleetModel (PlayerModel owner, String name) {
		mAction = Action.NONE;
		mSpeed = Double.MAX_VALUE;
		mShips = new ArrayList<ShipModel>();
		mName = name;
		mOwner = owner;
		owner.addFleet(this);
	}

	public double getSpeed () {
		return mSpeed;
	}
	
	public void addShip(ShipModel ship) throws GameException {

		// Ship is already in a fleet
		if (ship.getFleet() != null) {
			// new and old fleets are at the same location
			if (ship.getFleet().getLocation() == mLocation) {
				ship.getFleet().removeShip(ship);
			}
			// Throw GameException 
			else {
				throw new GameException("Cannot move ship to fleet with different location");
			}
		}
		
		mShips.add(ship);
		ship.setFleet(this);
		
		mAttackIndice += ship.getAttackIndice();
		mDefenseIndice += ship.getDefenseIndice();

		if (ship.getSpeed() < mSpeed) {
			mSpeed = ship.getSpeed();
		}
	}

	public void removeShip (ShipModel ship) {
		mShips.remove(ship);
		
		ship.setFleet(null);
		resetSpeed();
	}

	private void resetSpeed () {
		mSpeed = Double.MAX_VALUE;
		for (ShipModel ship: mShips) {
			if (ship.getSpeed() < mSpeed) {
				mSpeed = ship.getSpeed();
			}
		}
	}

	public void setTravel (TravelModel travel) {
		mTravel = travel;
	}
	
	public int getETA () {
		if (mTravel != null) {
			return (int)(mTravel.getLength() / mSpeed);
		}
		return -1;
	}

	public void setOwner (PlayerModel owner) {
		mOwner = owner;
	}

	public String getName () {
		return mName;
	}

	public void setName (String name) {
		mName = name;
	}

	public String getLocationName () {
		return mLocation.getName();
	}

	public int getNbShip () {
		return mShips.size();
	}

	public int getPower () {
		return 42;
	}

	public int getDefense () {
		return 42;
	}

	public List<ShipModel> getShips () {
		return mShips;
	}

	public void setLocation (ILocation location) {
		// Location is already set, nothing to do
		if (mLocation == location) {
			return;
		}
		
		// Location is different, remove fleet from old location
		if (mLocation != null) {
			mLocation.removeFleet(this);
		}
		mLocation = location;
		if (!location.getFleets().contains(this)) {
			location.addFleet(this);
		}
	}

	private void go (SystemModel system) {
		System.out.println("Go to " + system.getName());
		
		system.moveTo(this);
		
		// Remove casualties
		List<FleetModel> destroyed = new ArrayList<FleetModel>();
		for (FleetModel f: system.getFleets()) {
			if (f.getNbShip() == 0) {
				destroyed.add(f);
			}
		}
		system.getFleets().removeAll(destroyed);
	}

	public PlayerModel getOwner () {
		return mOwner;
	}

	public int getPathStep() {
		return mPath != null ? mPath.size() : 0;
	}
	
	public void move () {
		if (mPath != null && mPath.size() > 0) {
			Vertex v = mPath.pollFirst();
			go (v.getSystem());
			mAction = Action.MOVE;
		}
		else {
			mAction = Action.NONE;
		}
	}

	public void setCourse (SystemModel goal) {
		SystemModel current = null;
		if (mLocation instanceof PlanetModel) {
			current = ((PlanetModel)mLocation).getSystem();
		} else if (mLocation instanceof SystemModel) {
			current = (SystemModel)mLocation;
		}
		
		LinkedList<Vertex> path = PathResolver.getInstance().getPath(current, goal);
		
		if (path != null) {
			mPath = path;
		}
		
		move();
	}

	public Action getAction () {
		return mAction;
	}

	public boolean hasDevice (Device device) {
		for (ShipModel s: mShips) {
			if (s.hasDevice(device)) {
				return true;
			}
		}
		return false;
	}

	public ILocation getLocation () {
		return mLocation;
	}

	public LinkedList<Vertex> getPath() {
		return mPath;
	}

	public double getAttackIndice () {
		return mAttackIndice;
	}

	public double getDefenseIndice () {
		return mDefenseIndice;
	}

	public double getIndice () {
		return mAttackIndice + mDefenseIndice;
	}

	public void addShips (List<ShipModel> ships) {
		List<ShipModel> cpy = new ArrayList<ShipModel>(ships);
		for (ShipModel ship: cpy) {
			addShip(ship);
		}
	}

	public void destroy () {
		getOwner().removeFleet(this);
		getLocation().removeFleet(this);
	}

	@Override
	public boolean isDock () {
		return false;
	}

}
