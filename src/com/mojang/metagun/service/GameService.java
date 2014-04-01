package com.mojang.metagun.service;

import java.util.ArrayList;
import java.util.List;

import com.mojang.metagun.Constants;
import com.mojang.metagun.model.PlanetModel;
import com.mojang.metagun.model.SystemModel;

public class GameService {
	
	private static GameService sSelf;
	
	private List<SystemModel> mSystems;

	private GameService() {
		mSystems = new ArrayList<SystemModel>();
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
				system.addPlanet(p);
			}
			{
				PlanetModel p = new PlanetModel();
				p.setInitialTick(0);
				system.addPlanet(p);
			}
			{
				PlanetModel p = new PlanetModel();
				p.setInitialTick(0);
				system.addPlanet(p);
			}
			mSystems.add(system);
		}

		{
			SystemModel system = new SystemModel("Wolf", 50, 140);
			system.addPlanet(new PlanetModel());
			mSystems.add(system);
		}
		
		{
			SystemModel system = new SystemModel("Idron", 600, 400);
			system.addPlanet(new PlanetModel());
			system.addPlanet(new PlanetModel());
			mSystems.add(system);
		}

	}

	public SystemModel getSystemAtPos (int x, int y) {
		for (SystemModel s: mSystems) {
			if (x >= s.getX() && x <= s.getX() + Constants.SYSTEM_SIZE && y >= s.getY() && y <= s.getY() + Constants.SYSTEM_SIZE) {
				return s;
			}
		}
		return null;
	}

}
