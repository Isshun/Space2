package org.bluebox.space2.ai.forcePool;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.ai.AIManager;
import org.bluebox.space2.ai.AIPlayerModel;
import org.bluebox.space2.ai.GSAI;
import org.bluebox.space2.ai.Goal;
import org.bluebox.space2.ai.expension.goal.ShipFilter;
import org.bluebox.space2.ai.ship.goal.BuildShipGoal;

public class ForcePoolManager extends AIManager<ForcePoolManager.Need> {
	public static enum Need {
		BUILD_SHIP
	}
	
	private List<BuildShipGoal>	mGoals;
	protected double 					mGlobalForceIndice;
	
	public ForcePoolManager (GSAI gsai, AIPlayerModel player) {
		super(gsai, player);

		mGoals = new ArrayList<BuildShipGoal>();
	}

	public Goal need (Need need, ShipFilter filter) {
//		switch (need) {
//		case BUILD_SHIP:
//			Goal goalBuildShip = new GoBuildShip(mPlayer, filter, new GoBuildShipListener() {
//				@Override
//				public void onComplete (ShipModel ship) {
//					mGlobalForceIndice += ship.getIndice();
//				}
//			});
//			mGoals.add(goalBuildShip);
//			return goalBuildShip;
//		}
		return null;
	}

	@Override
	public void onUpdate () {
		for (BuildShipGoal goal: mGoals) {
			if (goal.execute()) {
				mGlobalForceIndice += goal.getShip().getIndice();
			}
		}
	}

	@Override
	public Goal need (Need need) {
		return null;
	}

	public double getGlobalForceIndice () {
		return mGlobalForceIndice;
	}

	public void need (BuildShipGoal goal) {
		mGoals.add(goal);
	}

}
