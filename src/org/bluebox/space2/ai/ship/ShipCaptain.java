package org.bluebox.space2.ai.ship;

import org.bluebox.space2.ai.AIPlayerModel;
import org.bluebox.space2.ai.AIUnit;
import org.bluebox.space2.ai.ship.goal.ShipActionGoal;
import org.bluebox.space2.game.model.FleetModel;
import org.bluebox.space2.game.model.ShipModel;

public class ShipCaptain extends AIUnit {
	private ShipActionGoal 	mGoal;
	private ShipModel			mShip;
	private FleetModel 		mFleet;

	public ShipCaptain(AIPlayerModel player, FleetModel fleet, ShipModel ship) {
		super(player);
		mFleet = fleet;
		mShip = ship;
	}

	public void setGoal (ShipActionGoal goal) {
		mGoal = goal;
	}


	public void onUpdate() {
//		// Nothing to do
//		if (mGoal == null) {
//			return;
//		}
//		
//		mGoal.execute();
	}

	public ShipModel getShip () {
		return mShip;
	}

	public void update () {
		if (mGoal != null) {
			if (mGoal.execute()) {
				mGoal = null;
			}
		}
	}

	public FleetModel getFleet () {
		return mFleet;
	}

	public void setShip (ShipModel ship) {
		mShip = ship;
	}
}
