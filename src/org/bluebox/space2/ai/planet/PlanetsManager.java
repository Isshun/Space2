package org.bluebox.space2.ai.planet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bluebox.space2.ai.AIManager;
import org.bluebox.space2.ai.AIPlayerModel;
import org.bluebox.space2.ai.GSAI;
import org.bluebox.space2.ai.Goal;
import org.bluebox.space2.game.model.PlanetModel;

public class PlanetsManager extends AIManager<PlanetsManager.Need> {
	public static enum Need {
	}
	
	private Map<Need, Goal>								mGoals;
	private Map<PlanetModel, PlanetaryGovernor>	mGovernators;

	public PlanetsManager (GSAI gsai, AIPlayerModel player) {
		super(gsai, player);
		
		mGovernators = new HashMap<PlanetModel, PlanetaryGovernor>();
		mGoals = new HashMap<Need, Goal>();
	}

	public double getPlanetsDevelopement () {
		double developement = 0;
		Collection<PlanetModel> planets = mPlayer.getPlanets();
		for (PlanetModel planet: planets) {
			developement += planet.getDevelopement();
		}
		return developement / planets.size();
	}

	@Override
	public Goal need (Need need) {
		if (mGoals.containsKey(need)) {
			return mGoals.get(need);
		}
//		
//		switch (need) {
//		case COLONIZE_NEW_PLANET:
//			Goal goal = new ColonizeGoal(mPlayer);
//			mGoals.put(need, goal);
//			return goal;
//		}
		return null;
	}

	@Override
	public void onUpdate () {
		Collection<Goal> goals = mGoals.values();
		for (Goal goal: goals) {
			goal.execute();
		}
		
		for (Entry<PlanetModel, PlanetaryGovernor> entry: mGovernators.entrySet()) {
			if (entry.getKey().hasDock() == false) {
				entry.getValue().need(PlanetaryGovernor.Need.BUILD_DOCK);
			}
		}
	}

	public void addGovernator (PlanetModel planet) {
		mGovernators.put(planet, new PlanetaryGovernor(planet));
	}
}
