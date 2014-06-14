package org.bluebox.space2.ai;

import org.bluebox.space2.ai.defense.DefensiveManager;
import org.bluebox.space2.ai.diplomacy.DiplomaticManager;
import org.bluebox.space2.ai.economy.EconomicManager;
import org.bluebox.space2.ai.expension.ExpansionManager;
import org.bluebox.space2.ai.forcePool.ForcePoolManager;
import org.bluebox.space2.ai.research.ResearchManager;
import org.bluebox.space2.ai.research.ShipDesignManager;
import org.bluebox.space2.ai.ship.ShipCaptain;
import org.bluebox.space2.ai.war.WarPlanner;

public class GSAI {
	private static final double PLANETS_DEVELOPEMENT_THRESOLD_TO_START_COLONIZE = 0.8;
	private DefensiveManager	mDefensiveManager;
	private DiplomaticManager	mDiplomacyManager;
	private EconomicManager		mEconomicManager;
	private ResearchManager		mResearchManager;
	private ExpansionManager	mExpansionManager;
	private WarPlanner			mWarPlanner;
	private ForcePoolManager	mForcePoolManager;
	private ShipDesignManager	mShipDesignManager;
	private AIPlayerModel 		mPlayer;
	
	public GSAI (AIPlayerModel player) {
		mPlayer = player;
		mDefensiveManager = new DefensiveManager(this, player);
		mDiplomacyManager = new DiplomaticManager(this, player);
		mEconomicManager = new EconomicManager(this, player);
		mResearchManager = new ResearchManager(this, player);
		mExpansionManager = new ExpansionManager(this, player);
		mWarPlanner = new WarPlanner(this, player);
		mForcePoolManager = new ForcePoolManager(this, player);
		
		
	}

	public void onUpdate () {
		if (mExpansionManager.getPlanetsDevelopement() > PLANETS_DEVELOPEMENT_THRESOLD_TO_START_COLONIZE) {
			mExpansionManager.need(ExpansionManager.Need.COLONIZE_NEW_PLANET);
		}
		
		mDefensiveManager.onUpdate();
		mDiplomacyManager.onUpdate();
		mEconomicManager.onUpdate();
		mResearchManager.onUpdate();
		mExpansionManager.onUpdate();
		mWarPlanner.onUpdate();
		mForcePoolManager.onUpdate();
	}

	public ForcePoolManager getForcePoolManager() {
		return mForcePoolManager;
	}

	public ShipDesignManager getShipDesignManager () {
		return mShipDesignManager;
	}

}
