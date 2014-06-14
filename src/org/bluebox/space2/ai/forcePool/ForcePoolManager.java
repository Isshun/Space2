package org.bluebox.space2.ai.forcePool;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.ai.AIManager;
import org.bluebox.space2.ai.AIPlayerModel;
import org.bluebox.space2.ai.GSAI;
import org.bluebox.space2.ai.Goal;
import org.bluebox.space2.ai.expension.goal.ShipFilter;
import org.bluebox.space2.ai.ship.goal.GoBuildShip;
import org.bluebox.space2.game.model.ShipTemplateModel;

public class ForcePoolManager extends AIManager<ForcePoolManager.Need> {
	public static enum Need {
		BUILD_SHIP
	}
	
	private List<Goal>	mGoals;
	
	public ForcePoolManager (GSAI gsai, AIPlayerModel player) {
		super(gsai, player);

		mGoals = new ArrayList<Goal>();
	}

	public Goal need (Need need, ShipFilter filter) {
		switch (need) {
		case BUILD_SHIP:
			Goal goalBuildShip = new GoBuildShip(mPlayer, filter);
			mGoals.add(goalBuildShip);
			return goalBuildShip;
		}
		return null;
	}

	@Override
	public void onUpdate () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Goal need (Need need) {
		return null;
		
	}

}
