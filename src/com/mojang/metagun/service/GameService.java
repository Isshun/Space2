package com.mojang.metagun.service;

import java.util.ArrayList;
import java.util.List;

import com.mojang.metagun.Constants;
import com.mojang.metagun.model.FleetModel;
import com.mojang.metagun.model.PlanetModel;
import com.mojang.metagun.model.PlayerModel;
import com.mojang.metagun.model.ShipModel;
import com.mojang.metagun.model.SystemModel;
import com.mojang.metagun.model.TravelModel;

public class GameService {
	
	private static final int TRAVEL_MAX_DISTANCE = 150;

	private static GameService sSelf;
	
	private List<SystemModel> mSystems;
	private List<PlanetModel> mPlanets;

	private ArrayList<PlayerModel> mPlayers;

	private PlayerModel mPlayer;

	private ArrayList<TravelModel> mTravels;

	private ArrayList<TravelModel> mTravelLines;

	private GameService() {
		mSystems = new ArrayList<SystemModel>();
		mPlanets = new ArrayList<PlanetModel>();
		mPlayers = new ArrayList<PlayerModel>();
		mTravels = new ArrayList<TravelModel>();
	}
	
	public List<SystemModel> getSystems () {
		return mSystems;
	}

	public static GameService getInstance () {
		if (sSelf == null) {
			sSelf = new GameService();
		}
		return sSelf;
	}

	public void initDebug () {
		
		mPlayer = new PlayerModel("me");
		mPlayers.add(new PlayerModel("player-1"));
		mPlayers.add(new PlayerModel("player-2"));
		mPlayers.add(new PlayerModel("player-3"));
		mPlayers.add(mPlayer);
		
		{
			SystemModel system = new SystemModel("Archer", (int)(Math.random() * 800), (int)(Math.random() * 400));
			addPlanet(system, new PlanetModel());
			addPlanet(system, new PlanetModel());
			addPlanet(system, new PlanetModel());
			mSystems.add(system);
			mPlayers.get(0).addSystem(system);
		}

		{
			SystemModel system = new SystemModel("Cerberus", (int)(Math.random() * 800), (int)(Math.random() * 400));
			addPlanet(system, new PlanetModel());
			addPlanet(system, new PlanetModel());
			addPlanet(system, new PlanetModel());
			mSystems.add(system);
			mPlayers.get(1).addSystem(system);
		}

		{
			SystemModel system = new SystemModel("Proxima", (int)(Math.random() * 800), (int)(Math.random() * 400));
			addPlanet(system, new PlanetModel());
			addPlanet(system, new PlanetModel());
			addPlanet(system, new PlanetModel());
			mSystems.add(system);
			mPlayers.get(2).addSystem(system);
		}

		{
			SystemModel system = new SystemModel("Vega", (int)(Math.random() * 800), (int)(Math.random() * 400));
			addPlanet(system, new PlanetModel());
			addPlanet(system, new PlanetModel());
			addPlanet(system, new PlanetModel());
			mSystems.add(system);
			mPlayers.get(3).addSystem(system);
		}

		{
			SystemModel system = new SystemModel("Regula", (int)(Math.random() * 800), (int)(Math.random() * 400));
			addPlanet(system, new PlanetModel());
			addPlanet(system, new PlanetModel());
			addPlanet(system, new PlanetModel());
			mSystems.add(system);
		}

		{
			SystemModel system = new SystemModel("Tigen", (int)(Math.random() * 800), (int)(Math.random() * 400));
			addPlanet(system, new PlanetModel());
			addPlanet(system, new PlanetModel());
			addPlanet(system, new PlanetModel());
			mSystems.add(system);
		}

		{
			SystemModel system = new SystemModel("Remus", (int)(Math.random() * 800), (int)(Math.random() * 400));
			addPlanet(system, new PlanetModel());
			addPlanet(system, new PlanetModel());
			addPlanet(system, new PlanetModel());
			mSystems.add(system);
		}

		{
			SystemModel system = new SystemModel("Deridia", (int)(Math.random() * 800), (int)(Math.random() * 400));
			addPlanet(system, new PlanetModel());
			addPlanet(system, new PlanetModel());
			addPlanet(system, new PlanetModel());
			mSystems.add(system);
		}

		{
			SystemModel system = new SystemModel("Wolf", (int)(Math.random() * 800), (int)(Math.random() * 400));
			addPlanet(system, new PlanetModel());
			mSystems.add(system);
		}
		
		{
			SystemModel system = new SystemModel("Idron", (int)(Math.random() * 800), (int)(Math.random() * 400));
			addPlanet(system, new PlanetModel());
			addPlanet(system, new PlanetModel());
			mSystems.add(system);
		}

		FleetModel f1 = new FleetModel(mPlayer);
		f1.addShip(new ShipModel());
		FleetModel f2 = new FleetModel(mPlayer);
		f2.addShip(new ShipModel());
		f2.addShip(new ShipModel());
		FleetModel f3 = new FleetModel(mPlayer);
		f3.addShip(new ShipModel());
		f3.addShip(new ShipModel());
		f3.addShip(new ShipModel());
		
//		mTravels.add(new TravelModel(f1, mSystems.get(0), mSystems.get(4)));
//		mTravels.add(new TravelModel(f2, mSystems.get(1), mSystems.get(5)));
		
		// Create travel lines
		//mTravelLines = new ArrayList<TravelModel>();
		for (SystemModel s1: mSystems) {
			for (SystemModel s2: mSystems) {
				int length = (int)Math.sqrt(Math.pow(Math.abs(s1.getX() - s2.getX()), 2) + Math.pow(Math.abs(s1.getY() - s2.getY()), 2));
				System.out.println(s1.getName() + " <> " + s2.getName() + ", length: " + length + " a: " + Math.pow(Math.abs(s1.getX() - s2.getX()), 2) + ", b: " + Math.pow(Math.abs(s1.getY() - s2.getY()), 2));
				if (s1 != s2 && length < TRAVEL_MAX_DISTANCE) {
					System.out.println("Add travel line between " + s1.getName() + " and " + s2.getName());
					mTravels.add(new TravelModel(s1, s2));
				}
			}
		}
		
		mTravels.get(mTravels.size() / 2).addFleet(f1);
		
	}

	private void addPlanet (SystemModel system, PlanetModel p) {
		mPlanets.add(p);
		system.addPlanet(p);
	}

	public SystemModel getSystemAtPos (int x, int y) {
		for (SystemModel s: mSystems) {
			if (x >= s.getX() && x <= s.getX() + Constants.SYSTEM_SIZE && y >= s.getY() && y <= s.getY() + Constants.SYSTEM_SIZE) {
				return s;
			}
		}
		return null;
	}

	public TravelModel getTravelAtPos (int x, int y) {
		for (TravelModel t: mTravels) {
			if (x >= t.getX() && x <= t.getX() + Constants.SYSTEM_SIZE && y >= t.getY() && y <= t.getY() + Constants.SYSTEM_SIZE) {
				return t;
			}
		}
		return null;
	}

	public List<PlanetModel> getPlanets () {
		return mPlanets;
	}

	public PlayerModel getPlayer () {
		return mPlayer;
	}

	public List<PlayerModel> getPlayers () {
		return mPlayers;
	}

	public List<TravelModel> getTravels () {
		return mTravels;
	}

}
