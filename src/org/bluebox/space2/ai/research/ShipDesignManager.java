package org.bluebox.space2.ai.research;

import org.bluebox.space2.ai.AIManager;
import org.bluebox.space2.ai.AIPlayerModel;
import org.bluebox.space2.ai.GSAI;
import org.bluebox.space2.ai.Goal;
import org.bluebox.space2.ai.expension.goal.ShipFilter;

public class ShipDesignManager extends AIManager<ShipDesignManager.Need> {
	public static enum Need {
		DESIGN_TEMPLATE
	}
	
	public ShipDesignManager (GSAI gsai, AIPlayerModel player) {
		super(gsai, player);
	}

	@Override
	public Goal need (Need need) {
		return null;
	}

	@Override
	public void onUpdate () {
		// TODO Auto-generated method stub
		
	}

	public Goal need (Need designTemplate, ShipFilter mFilter) {
		return null;
	}

}
