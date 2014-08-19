package org.bluebox.space2.ai;

import org.bluebox.space2.ai.defense.DefensiveManager;
import org.bluebox.space2.ai.diplomacy.DiplomaticManager;
import org.bluebox.space2.ai.economy.EconomicManager;
import org.bluebox.space2.ai.expension.ExpansionManager;
import org.bluebox.space2.ai.forcePool.ForcePoolManager;
import org.bluebox.space2.ai.planet.PlanetsManager;
import org.bluebox.space2.ai.research.ResearchManager;
import org.bluebox.space2.ai.research.ShipDesignManager;
import org.bluebox.space2.ai.war.WarPlanner;

public abstract class GSAI {
	protected DefensiveManager		mDefensiveManager;
	protected DiplomaticManager	mDiplomacyManager;
	protected EconomicManager		mEconomicManager;
	protected ResearchManager		mResearchManager;
	protected ExpansionManager		mExpansionManager;
	protected WarPlanner				mWarPlanner;
	protected ForcePoolManager		mForcePoolManager;
	protected ShipDesignManager	mShipDesignManager;
	protected AIPlayerModel 		mPlayer;
	protected PlanetsManager 		mPlanetsManager;
	
	public void init(AIPlayerModel player) {
		mPlayer = player;
		mDefensiveManager = new DefensiveManager(this, player);
		mDiplomacyManager = new DiplomaticManager(this, player);
		mEconomicManager = new EconomicManager(this, player);
		mResearchManager = new ResearchManager(this, player);
		mExpansionManager = new ExpansionManager(this, player);
		mWarPlanner = new WarPlanner(this, player);
		mForcePoolManager = new ForcePoolManager(this, player);
		mPlanetsManager = new PlanetsManager(this, player);
	}

	public abstract void onUpdate ();

	public ForcePoolManager getForcePoolManager() {
		return mForcePoolManager;
	}

	public ShipDesignManager getShipDesignManager () {
		return mShipDesignManager;
	}

	public void update () {
		mPlanetsManager.onUpdate();
		mDefensiveManager.onUpdate();
		mDiplomacyManager.onUpdate();
		mEconomicManager.onUpdate();
		mResearchManager.onUpdate();
		mExpansionManager.onUpdate();
		mWarPlanner.onUpdate();
		mForcePoolManager.onUpdate();
		
		onUpdate();
	}

	public PlanetsManager getPlanetsManager () { return mPlanetsManager; }
	public DefensiveManager getDefensiveManager () { return mDefensiveManager; }

}
