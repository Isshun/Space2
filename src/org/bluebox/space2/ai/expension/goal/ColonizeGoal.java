package org.bluebox.space2.ai.expension.goal;

import java.util.List;

import org.bluebox.space2.Log;
import org.bluebox.space2.ai.AIPlayerModel;
import org.bluebox.space2.ai.Goal;
import org.bluebox.space2.ai.forcePool.ForcePoolManager.Need;
import org.bluebox.space2.ai.ship.ShipCaptain;
import org.bluebox.space2.ai.ship.goal.ShipActionColonizeGoal;
import org.bluebox.space2.ai.ship.goal.GoBuildShip;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.ShipTemplateModel;
import org.bluebox.space2.game.model.ShipModel;
import org.bluebox.space2.game.service.GameService;

import com.badlogic.gdx.utils.Logger;

public class ColonizeGoal extends Goal {
	enum Step {
		FIND_PLANET,
		LOOKING_COLONY_SHIP,
		REQUEST_COLONY_SHIP,
		ORDER_TO_COLONIZE
	}
	
	private Step				mStep = Step.FIND_PLANET;
	private Goal 				mBuildColonyShipGoal;
	private ShipModel 		mShip;
	private PlanetModel 		mPlanet;
	private ShipCaptain 		mCaptain;
	
	public ColonizeGoal (AIPlayerModel player) {
		super(player);
	}

	@Override
	public boolean execute () {
		Log.debug("ColonizeGoal: " + mStep);
		
		// Colony ship has been destroyed
		if (mShip != null && mShip.isDestroyed()) {
			mShip = null;
		}
		
		// Planet is no more available to colonize
		if (mPlanet != null && mPlanet.isFree() == false && mPlanet.getOwner() != mPlayer) {
			mPlanet = null;
		}
		
		switch (mStep) {
		
		// Find planet
		case FIND_PLANET:
			mPlanet = findColonizablePlanet();
			if (mPlanet != null) {
				mStep = Step.LOOKING_COLONY_SHIP;
			}
			break;
			
		// Looking for colony ship
		case LOOKING_COLONY_SHIP:
			mShip = findColonyShip();
			if (mShip != null) {
				mStep = Step.ORDER_TO_COLONIZE;
			} else {
				mStep = Step.REQUEST_COLONY_SHIP;
			}
			break;
			
		// Request colony ship and check construction
		case REQUEST_COLONY_SHIP:
			if (mBuildColonyShipGoal == null) {
				mBuildColonyShipGoal = buildColonyShip();
			} else if (mBuildColonyShipGoal.execute()) {
				mStep = Step.LOOKING_COLONY_SHIP;
			}
			break;
			
		case ORDER_TO_COLONIZE:
			mCaptain = findCaptain(mShip);
			mCaptain.setGoal(new ShipActionColonizeGoal(mPlayer, mShip, mPlanet));
			break;
		}
		return false;
	}

	private ShipCaptain findCaptain (ShipModel ship) {
		List<ShipCaptain> captains = mPlayer.getCaptains();
		for (ShipCaptain captain: captains) {
			if (captain.getShip() == ship) {
				return captain;
			}
		}
		return mPlayer.createCaptain(ship);
	}

	private Goal buildColonyShip () {
		Log.debug("buildColonyShip");
		
		ShipFilter filter = new ShipFilter();
		filter.hasColonizer = true;
		return mGSAI.getForcePoolManager().need(Need.BUILD_SHIP, filter);
	}

	private ShipModel findColonyShip () {
		return null;
	}

	private PlanetModel findColonizablePlanet () {
		int bestIndice = 0;
		PlanetModel bestPlanet = null;
		for (PlanetModel planet: mPlayer.getKnowedPlanet()) {
			if (planet.getIndice() > bestIndice) {
				bestIndice = planet.getIndice();
				bestPlanet = planet;
			}
		}
		return bestPlanet;
	}

}
