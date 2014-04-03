package com.mojang.metagun.screen;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mojang.metagun.Art;
import com.mojang.metagun.Constants;
import com.mojang.metagun.model.FleetModel;
import com.mojang.metagun.model.ShipModel;

public class PanelFleetScreen extends Screen {

	private static final int GRID_WIDTH = 46;
	private static final int GRID_HEIGHT = 50;
	private static final int GRID_PADDING = 0;
	private static final int GRID_COLUMNS = 4;
	private static final int GRID_CONTENT_WIDTH = GRID_WIDTH - GRID_PADDING - GRID_PADDING;
	private static final int GRID_CONTENT_HEIGHT = GRID_HEIGHT - GRID_PADDING - GRID_PADDING;
	private static final int START_Y = 32;
	private static final int START_X = 5;
	
	private FleetModel mFleet;
	private int mSelected;
	private double mTotInd;
	private double mAttInd;
	private double mDefInd;

	public PanelFleetScreen (FleetModel fleet) {
		mFleet = fleet;
		
		for (ShipModel ship: mFleet.getShip()) {
			mTotInd += ship.getIndice();
			mAttInd += ship.getAttackIndice();
			mDefInd += ship.getDefenseIndice();
		}
	}

	@Override
	protected void onCreate () {
	}

	@Override
	public void onRender (SpriteBatch spriteBatch) {
		draw(Art.bg_1, 0, 0);
		
		drawBigString(String.format("%s (%d/%d/%d)", mFleet.getName(), (int)mTotInd, (int)mAttInd, (int)mDefInd), 6, 6);
		draw(Art.ship_big, Constants.GAME_WIDTH - 132, 12);

		ShipModel ship = mFleet.getShip().get(mSelected);
		
		drawBigString(String.valueOf((int)ship.getIndice()), 220, 130);
//		draw(Art.ic_info_32, 215, 120);
		drawString("class:      " + ship.getClassName(), 256, 120);
		drawString("mass:           " + ship.getMass(), 256, 120 + 12 * 1);
		drawString("crew:         " + ship.getCrew() + "/" + ship.getTotalCrew(), 256, 120 + 12 * 2);
		
		drawRectangle(213, 156, 157, 1, Color.rgba8888(1, 1, 1, 0.65f));

		drawBigString(String.valueOf((int)ship.getAttackIndice()), 220, 172);
//		draw(Art.ic_attack_32, 215, 162);
		drawString("pow. phaser:    " + (int)ship.getPhaserPower(), 256, 126 + 12 * 3);
		drawString("pow. torpedo:   " + (int)ship.getTorpedoPower(), 256, 126 + 12 * 4);
		drawString("speed:           " + (int)ship.getVelocity(), 256, 126 + 12 * 5);

		drawRectangle(213, 198, 157, 1, Color.rgba8888(1, 1, 1, 0.65f));
		
		drawBigString(String.valueOf((int)ship.getDefenseIndice()), 220, 214);
//		draw(Art.shield, 215, 204);
		drawString("hull:       " + ship.getHull() + "/" + ship.getTotalHull(), 256, 132 + 12 * 6);
		drawString("armory:          " + (int)ship.getArmory(), 256, 132 + 12 * 7);
		drawString("shield:          " + (int)ship.getShieldPower(), 256, 132 + 12 * 8);
		
		//drawRectangle(6, 32, 183, 600, Color.rgba8888(0.85f, 0.85f, 1, 0.45f)); 
		
		List<ShipModel> ships = mFleet.getShip();
		int i = 0;
		for (ShipModel shipModel : ships) {
			
			if (i == mSelected) {
				drawRectangle(
					START_X + GRID_PADDING + (i % GRID_COLUMNS) * GRID_WIDTH,
					START_Y + GRID_PADDING + (int)(i / GRID_COLUMNS) * GRID_HEIGHT,
					GRID_CONTENT_WIDTH,
					GRID_CONTENT_HEIGHT,
					Color.rgba8888(0.85f, 1, 0.85f, 0.65f));
			}
			
			draw(Art.ship_32,
				START_X + 7 + GRID_PADDING + (i % GRID_COLUMNS) * GRID_WIDTH,
				START_Y + 6 + GRID_PADDING + (int)(i / GRID_COLUMNS) * GRID_HEIGHT);
			
			drawRectangle(
				START_X + 7 + GRID_PADDING + (i % GRID_COLUMNS) * GRID_WIDTH,
				START_Y + 40 + GRID_PADDING + (int)(i / GRID_COLUMNS) * GRID_HEIGHT,
				GRID_CONTENT_WIDTH - 14,
				2,
				Color.rgba8888(0, 1, 0, 1));
			
			i++;
		}
	}

	@Override
	public void onTouch (int x, int y) {
		if (x > START_X && y > START_Y) {
			int i = (x - START_X) / GRID_WIDTH + (y - START_Y) / GRID_HEIGHT * GRID_COLUMNS;
			if (i < mFleet.getNbShip()) {
				mSelected = i;
			}
		}
	}

	@Override
	public void onMove (int offsetX, int offsetY) {
		// TODO Auto-generated method stub

	}

}
