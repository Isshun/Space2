package org.bluebox.space2.ai.ship.goal;

import org.bluebox.space2.ai.AIPlayerModel;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.ShipModel;

public class ShipActionColonizeGoal extends ShipActionGoal {
	private PlanetModel _planet;

	public ShipActionColonizeGoal(AIPlayerModel player, ShipModel ship, PlanetModel planet) {
		super(player, ship, planet);
		
		_planet = planet;
	}

	@Override
	protected boolean action(AIPlayerModel player) {
		_planet.colonize(player);
		
		if (_planet.getOwner() == player) {
			return true;
		}
		
		return false;
	}

}
