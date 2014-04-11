
package org.bluebox.space2.screen.impl;

import org.bluebox.space2.model.FleetModel;
import org.bluebox.space2.model.TravelModel;
import org.bluebox.space2.screen.ScreenBase;
import org.bluebox.space2.screen.ScreenLayerBase;

public class TravelScreen extends ScreenBase {

	private TravelModel mTravel;
	private FleetModel mFleet;
	
	public TravelScreen (TravelModel travel) {
		mTravel = travel;
		if (travel.getFleet().size() > 0) {
			mFleet = travel.getFleet().get(0); 
		}
	}

	@Override
	public void onDraw (ScreenLayerBase mainLayer, ScreenLayerBase UILayer) {		
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
	public void onMove (int offsetX, int offsetY) {
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
