package org.bluebox.space2.ai;


public abstract class AIManager<TNeed> extends AIUnit {
	protected GSAI mGsai;

	public AIManager (GSAI gsai, AIPlayerModel player) {
		super(player);
		
		mGsai = gsai;
	}

	public abstract Goal need(TNeed need);
}
