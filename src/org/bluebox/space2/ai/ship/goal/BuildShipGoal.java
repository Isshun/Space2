package org.bluebox.space2.ai.ship.goal;

import org.bluebox.space2.ai.AIPlayerModel;
import org.bluebox.space2.ai.Goal;
import org.bluebox.space2.game.model.ShipModel;

public abstract class BuildShipGoal extends Goal {

	private ShipModel mShip;

	public BuildShipGoal (AIPlayerModel player) {
		super(player);
	}

	public ShipModel getShip () {
		return mShip;
	}

}
