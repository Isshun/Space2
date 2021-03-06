package org.bluebox.space2.ai.expension;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bluebox.space2.ai.AIManager;
import org.bluebox.space2.ai.AIPlayerModel;
import org.bluebox.space2.ai.GSAI;
import org.bluebox.space2.ai.Goal;
import org.bluebox.space2.ai.expension.goal.ColonizeGoal;
import org.bluebox.space2.game.model.PlanetModel;

public class ExpansionManager extends AIManager<ExpansionManager.Need> {
	public static enum Need {
		COLONIZE_NEW_PLANET
	}
	
	protected Map<Need, Goal>	mGoals;

	public ExpansionManager (GSAI gsai, AIPlayerModel player) {
		super(gsai, player);
		
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
	public Goal need (final Need need) {
		if (mGoals.containsKey(need)) {
			return mGoals.get(need);
		}
		
		switch (need) {
		case COLONIZE_NEW_PLANET:
			final Goal goal = new ColonizeGoal(mPlayer, new ColonizeGoal.GoalListener() {
				@Override
				public void onComplete (ColonizeGoal goal, PlanetModel planet) {
					if (mPlayer.equals(planet.getOwner())) {
						System.out.println("ExpansionManager: new planet colonized");
						
						mGoals.remove(need, goal);
					}
				}
			});
			mGoals.put(need, goal);
			return goal;
		}
		return null;
	}

	@Override
	public void onUpdate () {
		Collection<Goal> goals = mGoals.values();
		for (Goal goal: goals) {
			goal.execute();
		}
	}
}
