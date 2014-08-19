package org.bluebox.space2.ai.ship.goal;

import org.bluebox.space2.ai.AIPlayerModel;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.ShipModel;

public class ShipActionColonizeGoal extends ShipActionGoal {
	public interface GoalListener {
		void onComplete(PlanetModel planet);
	}

	private PlanetModel 		mPlanet;
	private GoalListener 	mListener;

	public ShipActionColonizeGoal(AIPlayerModel player, ShipModel ship, PlanetModel planet, GoalListener listener) {
		super(player, ship, planet);
		
		mListener = listener;
		mPlanet = planet;
	}

	@Override
	protected boolean onAction(AIPlayerModel player) {
		mPlanet.colonize(player);
		
		if (mPlanet.getOwner() == player) {
			if (mListener != null) {
				mListener.onComplete(mPlanet);
			}
			return true;
		}
		
		System.out.println("Unable to colonize planet");
		
		return false;
	}

	@Override
	public void onComplete () {
		// TODO Auto-generated method stub
		
	}

}
