package org.bluebox.space2.ai.ship;

import org.bluebox.space2.ai.AIPlayerModel;
import org.bluebox.space2.ai.AIUnit;
import org.bluebox.space2.ai.ship.goal.ShipActionGoal;
import org.bluebox.space2.game.model.ShipModel;

public class ShipCaptain extends AIUnit {
	private ShipActionGoal 	mGoal;
	private ShipModel			mShip;

	public ShipCaptain(AIPlayerModel player, ShipModel ship) {
		super(player);
		mShip = ship;
	}
	
	public void setGoal (ShipActionGoal goal) {
		mGoal = goal;
	}
	
	public void onUpdate() {
		// Nothing to do
		if (mGoal == null) {
			return;
		}
		
		mGoal.execute();
	}

	public ShipModel getShip () {
		return mShip;
	}
}
