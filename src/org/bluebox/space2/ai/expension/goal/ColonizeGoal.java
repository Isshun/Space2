package org.bluebox.space2.ai.expension.goal;

import java.util.List;

import org.bluebox.space2.Log;
import org.bluebox.space2.ai.AIPlayerModel;
import org.bluebox.space2.ai.Goal;
import org.bluebox.space2.ai.ship.ShipCaptain;
import org.bluebox.space2.ai.ship.goal.GoBuildShip;
import org.bluebox.space2.ai.ship.goal.GoBuildShip.GoBuildShipListener;
import org.bluebox.space2.ai.ship.goal.ShipActionColonizeGoal;
import org.bluebox.space2.game.model.FleetModel;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.ShipModel;

public class ColonizeGoal extends Goal {
	public interface GoalListener {
		void onComplete(ColonizeGoal goal, PlanetModel planet);
	}

	enum Step {
		FIND_PLANET,
		LOOKING_COLONY_SHIP,
		REQUEST_COLONY_SHIP,
		ORDER_TO_COLONIZE
	}
	
	private Step				mStep = Step.FIND_PLANET;
	private Goal 				mBuildColonyShipGoal;
	private PlanetModel 		mPlanet;
	private ShipCaptain 		mCaptain;
	protected ShipModel 		mShip;
	protected FleetModel		mFleet;
	protected GoalListener 	mListener;
	private double mRouteRiskIndice;
	
	public ColonizeGoal (AIPlayerModel player, GoalListener listener) {
		super(player);
		
		mListener = listener;
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
			mRouteRiskIndice = mGSAI.getDefensiveManager().getRouteRiskIndice(mPlanet);

			// Create fleet
			if (mFleet == null) {
				mFleet = new FleetModel(mPlayer);
				mFleet.addShip(mShip);
				mFleet.setLocation(mShip.getLocation());
			}

			// Create captain if not exists
			if (mFleet.hasCaptain() == false) {
				mFleet.setCaptain(findCaptain(mFleet, mShip));
			}
			
			// Go to colonize if route is safe
			if (mRouteRiskIndice < mFleet.getIndice()) {
				mFleet.moveCaptainToStrongestShip();
				mFleet.getCaptain().setGoal(new ShipActionColonizeGoal(mPlayer, mShip, mPlanet, new ShipActionColonizeGoal.GoalListener() {
					@Override
					public void onComplete (PlanetModel planet) {
						if (mListener != null) {
							mListener.onComplete(ColonizeGoal.this, planet);
						}
					}
				}));
			}

			break;
		}
		return false;
	}

	private ShipCaptain findCaptain (FleetModel fleet, ShipModel ship) {
		List<ShipCaptain> captains = mPlayer.getCaptains();
		for (ShipCaptain captain: captains) {
			if (fleet.equals(captain.getFleet())) {
				return captain;
			}
		}
		return mPlayer.createCaptain(fleet, ship);
	}

	private Goal buildColonyShip () {
		Log.debug("buildColonyShip");
		
		ShipFilter filter = new ShipFilter();
		filter.hasColonizer = true;
		
		GoBuildShip goal = new GoBuildShip(mPlayer, filter, new GoBuildShipListener() {
			@Override
			public void onComplete (ShipModel ship) {
				mShip = ship;
			}
		});
		mGSAI.getForcePoolManager().need(goal);
		
		return goal;
	}

	private ShipModel findColonyShip () {
		return null;
	}

	private PlanetModel findColonizablePlanet () {
		int bestIndice = 0;
		PlanetModel bestPlanet = null;
		for (PlanetModel planet: mPlayer.getKnowedPlanet()) {
			if (planet.isFree() && planet.getIndice() > bestIndice) {
				bestIndice = planet.getIndice();
				bestPlanet = planet;
			}
		}
		return bestPlanet;
	}

	@Override
	public void onComplete () {
		// TODO Auto-generated method stub
		
	}

}
