package org.bluebox.space2.ai.ship.goal;

import org.bluebox.space2.Log;
import org.bluebox.space2.ai.AIPlayerModel;
import org.bluebox.space2.ai.Goal;
import org.bluebox.space2.ai.expension.goal.ShipFilter;
import org.bluebox.space2.ai.forcePool.ForcePoolManager;
import org.bluebox.space2.ai.research.ShipDesignManager.Need;
import org.bluebox.space2.game.model.DockModel;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.ShipModel;
import org.bluebox.space2.game.model.ShipTemplateModel;

public class GoBuildShip extends Goal {
	enum Step {
		GET_TEMPLATE,
		WAIT_TEMPLATE_DESIGN,
		BUILD_SHIP,
		WAIT_SHIP_BUILDING
	}
	
	private ShipTemplateModel	mTemplate;
	private ShipModel				mShip;
	private ShipFilter 			mFilter;
	private Step					mStep = Step.GET_TEMPLATE;
	private Goal					mTemplateDesignGoal;
	private DockModel mDock;
	
	public GoBuildShip (AIPlayerModel player, ShipFilter filter) {
		super(player);
		mFilter = filter;
	}

	public boolean isComplete () {
		return false;
	}

	public ShipModel getShip () {
		return mShip;
	}

	@Override
	public boolean execute () {
		Log.debug("GoBuildShip: " + mStep);
		
		switch (mStep) {

		// Get template
		case GET_TEMPLATE:
			mTemplate = mPlayer.getShipTemplate(mFilter);
			if (mTemplate == null) {
				mStep = Step.WAIT_TEMPLATE_DESIGN;
				mTemplateDesignGoal = mGSAI.getShipDesignManager().need(Need.DESIGN_TEMPLATE, mFilter);
			} else {
				mStep = Step.BUILD_SHIP;
			}
			break;
			
			// Wait template design
			case WAIT_TEMPLATE_DESIGN:
				if (mTemplateDesignGoal.isComplete()) {
					mStep = Step.BUILD_SHIP;
				}
				break;
				
			// Build ship
			case BUILD_SHIP:
				mDock = getDockToBuild();
				if (mDock != null) {
					mShip = mDock.buildShip(mTemplate);
					mStep = Step.WAIT_SHIP_BUILDING;
				}
				break;
				
			// Wait ship building
			case WAIT_SHIP_BUILDING:
				if (mShip.isComplete()) {
					mPlayer.createCaptain(mShip);
					goalCompleted();
					return true;
				}
				break;
		}

		return false;
	}

	private DockModel getDockToBuild () {
		DockModel bestDock = null;
		int minDuration = Integer.MAX_VALUE;
		
		for (PlanetModel planet: mPlayer.getPlanets()) {
			if (planet.getDock() != null) {
				int duration = planet.getDock().getQueueDuration() + planet.getBuildETA(mTemplate.getBuildValue());
				if (planet.hasDock() && duration < minDuration) {
					minDuration = duration;
					bestDock = planet.getDock();
				}
			}
		}
		return bestDock;
	}
}
