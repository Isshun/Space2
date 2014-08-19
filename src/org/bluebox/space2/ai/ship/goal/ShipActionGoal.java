package org.bluebox.space2.ai.ship.goal;

import org.bluebox.space2.ai.AIPlayerModel;
import org.bluebox.space2.ai.Goal;
import org.bluebox.space2.game.model.ILocation;
import org.bluebox.space2.game.model.ShipModel;

public abstract class ShipActionGoal extends Goal {
	protected ILocation 	mTargetLocation;
	protected ShipModel 	mShip;
	
	public ShipActionGoal(AIPlayerModel player, ShipModel ship, ILocation location) {
		super(player);
		mShip = ship;
		mTargetLocation = location;
	}
	
	public ILocation getLocation () {
		return mTargetLocation;
	}

	@Override
	public boolean execute() {
		// Set course to location
		if (mShip.getFleet().getTargetLocation() != mTargetLocation) {
			mShip.getFleet().setCourse(mTargetLocation);
		}

		// Ship has reach target location
		// System.out.println("current: " + mShip.getLocation().getName() + ", target: " + mTargetLocation.getName());
		if (mShip.getLocation().getSystem() == mTargetLocation.getSystem()) {
			System.out.println(mShip.getFleet().getName() + " reached");
			
			onAction(mPlayer);
			return true;
		}
		
		// Move ship
		mShip.getFleet().move();
		
		return false;
	}

	protected abstract boolean onAction(AIPlayerModel player);
}
