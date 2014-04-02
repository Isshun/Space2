package com.mojang.metagun.service;

import java.util.ArrayList;
import java.util.List;

import com.mojang.metagun.Constants;
import com.mojang.metagun.model.PlanetModel;
import com.mojang.metagun.model.SystemModel;

public class GameService {
	
	private static GameService sSelf;
	
	private List<SystemModel> mSystems;
	private List<PlanetModel> mPlanets;

	private GameService() {
		mSystems = new ArrayList<SystemModel>();
		mPlanets = new ArrayList<PlanetModel>();
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
		
		{
			SystemModel system = new SystemModel("Regula", 100, 100);
			{
				PlanetModel p = new PlanetModel();
				p.setInitialTick(0);
				addPlanet(system, p);
			}
			{
				PlanetModel p = new PlanetModel();
				p.setInitialTick(0);
				addPlanet(system, p);
			}
			{
				PlanetModel p = new PlanetModel();
				p.setInitialTick(0);
				addPlanet(system, p);
			}
			mSystems.add(system);
		}

		{
			SystemModel system = new SystemModel("Wolf", 50, 140);
			addPlanet(system, new PlanetModel());
			mSystems.add(system);
		}
		
		{
			SystemModel system = new SystemModel("Idron", 600, 400);
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

}
