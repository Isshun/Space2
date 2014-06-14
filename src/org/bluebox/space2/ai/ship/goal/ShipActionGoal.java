package org.bluebox.space2.ai.ship.goal;

import org.bluebox.space2.ai.AIPlayerModel;
import org.bluebox.space2.ai.Goal;
import org.bluebox.space2.game.model.ILocation;
import org.bluebox.space2.game.model.ShipModel;

public abstract class ShipActionGoal extends Goal {
	protected ILocation 	_location;
	protected ShipModel 	_ship;
	
	public ShipActionGoal(AIPlayerModel player, ShipModel ship, ILocation location) {
		super(player);
		_ship = ship;
		_location = location;
	}
	
	public ILocation getLocation () {
		return _location;
	}

	@Override
	public boolean execute() {
		// Ship is moving to target location
		if (_ship.getFleet().isMoving() && _ship.getFleet().getTargetLocation() == _location) {
			return false;
		}

		// Set course to location
		if (_ship.getFleet().isMoving() == false && _ship.getFleet().getTargetLocation() != _location) {
			_ship.getFleet().setCourse(_location);
			return false;
		}
		
		// Ship has reach target location 
		if (_ship.getLocation() == _location) {
			return action(mPlayer);
		}
		
		return false;
	}

	protected abstract boolean action(AIPlayerModel player);
}
