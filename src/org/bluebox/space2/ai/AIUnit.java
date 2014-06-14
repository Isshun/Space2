package org.bluebox.space2.ai;


public abstract class AIUnit {
	protected AIPlayerModel mPlayer;
	
	public AIUnit(AIPlayerModel player) {
		mPlayer = player;
	}
	
	public abstract void onUpdate();
}
