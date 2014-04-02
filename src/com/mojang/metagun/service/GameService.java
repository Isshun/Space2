package com.mojang.metagun.service;

import java.util.ArrayList;
import java.util.List;

import com.mojang.metagun.Constants;
import com.mojang.metagun.model.PlanetModel;
import com.mojang.metagun.model.PlayerModel;
import com.mojang.metagun.model.SystemModel;

public class GameService {
	
	private static GameService sSelf;
	
	private List<SystemModel> mSystems;
	private List<PlanetModel> mPlanets;

	private ArrayList<PlayerModel> mPlayers;

	private PlayerModel mPlayer;

	private GameService() {
		mSystems = new ArrayList<SystemModel>();
		mPlanets = new ArrayList<PlanetModel>();
		mPlayers = new ArrayList<PlayerModel>();
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

	public List<PlanetModel> getPlanets () {
		return mPlanets;
	}

	public PlayerModel getPlayer () {
		return mPlayer;
	}

	public List<PlayerModel> getPlayers () {
		return mPlayers;
	}

}
