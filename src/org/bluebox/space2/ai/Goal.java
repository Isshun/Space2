package org.bluebox.space2.ai;

import org.bluebox.space2.game.model.ShipModel;


public abstract class Goal {
	protected AIPlayerModel mPlayer;
	protected GSAI 			mGSAI;
	protected boolean 		mIsComplete;

	public Goal (AIPlayerModel player) {
		mPlayer = player;
		mGSAI = player.getGSAI();
	}

	/**
	 * Execute goal, return true if complete
	 * 
	 * @return 
	 */
	public abstract boolean execute();
	
	public boolean isComplete() {
		return mIsComplete;
	}
	
	protected void goalCompleted () {
		mIsComplete = true;
		
		onComplete();
	}
	
	public abstract void onComplete();

}
