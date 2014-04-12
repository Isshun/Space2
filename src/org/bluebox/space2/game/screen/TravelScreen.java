
package org.bluebox.space2.game.screen;

import org.bluebox.space2.engine.screen.BaseScreen;
import org.bluebox.space2.engine.screen.BaseScreenLayer;
import org.bluebox.space2.game.model.FleetModel;
import org.bluebox.space2.game.model.TravelModel;

public class TravelScreen extends BaseScreen {

	private TravelModel mTravel;
	private FleetModel mFleet;
	
	public TravelScreen (TravelModel travel) {
		mTravel = travel;
		if (travel.getFleet().size() > 0) {
			mFleet = travel.getFleet().get(0); 
		}
	}

	@Override
	public void onDraw (BaseScreenLayer mainLayer, BaseScreenLayer UILayer) {		
		int posY = 6;
		mainLayer.drawString("from: " + mTravel.getFrom().getName(), 6, posY);
		
		posY += 12;
		mainLayer.drawString("to: " + mTravel.getTo().getName(), 6, posY);

		posY += 12;
		mainLayer.drawString("length: " + mTravel.getLength(), 6, posY);

		if (mFleet != null) {
			posY += 12;
			mainLayer.drawString("ETA: " + mFleet.getETA(), 6, posY);
		}
	}

	@Override
	public void onTouch (int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMove (int startX, int startY, int offsetX, int offsetY) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onCreate () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLongTouch (int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
