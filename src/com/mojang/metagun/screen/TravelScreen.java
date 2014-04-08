
package com.mojang.metagun.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mojang.metagun.model.FleetModel;
import com.mojang.metagun.model.TravelModel;

public class TravelScreen extends Screen {

	private TravelModel mTravel;
	private FleetModel mFleet;
	
	public TravelScreen (TravelModel travel) {
		mTravel = travel;
		if (travel.getFleet().size() > 0) {
			mFleet = travel.getFleet().get(0); 
		}
	}

	@Override
	public void onDraw (SpriteBatch spriteBatch, int gameTime, int screenTime) {		
		int posY = 6;
		drawString("from: " + mTravel.getFrom().getName(), 6, posY);
		
		posY += 12;
		drawString("to: " + mTravel.getTo().getName(), 6, posY);

		posY += 12;
		drawString("length: " + mTravel.getLength(), 6, posY);

		if (mFleet != null) {
			posY += 12;
			drawString("ETA: " + mFleet.getETA(), 6, posY);
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
