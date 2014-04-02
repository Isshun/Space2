
package com.mojang.metagun.screen;

import com.mojang.metagun.model.TravelModel;

public class TravelScreen extends Screen {

	private TravelModel mTravel;

	public TravelScreen (TravelModel travel) {
		mTravel = travel;
	}

	@Override
	public void render () {
		mSpriteBatch.begin();
		
		int posY = 6;
		drawString("from: " + mTravel.getFrom().getName(), 6, posY);
		
		posY += 12;
		drawString("to: " + mTravel.getTo().getName(), 6, posY);

		posY += 12;
		drawString("length: " + mTravel.getLength(), 6, posY);

		posY += 12;
//		drawString("ETA: " + mTravel.getETA(), 6, posY);
		
		mSpriteBatch.end();
	}

	@Override
	public void onTouch (int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMove (int offsetX, int offsetY) {
		// TODO Auto-generated method stub

	}

}
