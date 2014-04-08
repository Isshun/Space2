package com.mojang.metagun.screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.mojang.metagun.Art;
import com.mojang.metagun.Constants;
import com.mojang.metagun.model.FleetModel;
import com.mojang.metagun.model.PlanetModel;
import com.mojang.metagun.service.GameService;

public class PanelArmadaScreen extends Screen {

	private static final int START_Y = 36;
	private static final int LINE_HEIGHT = 15;
	private static final int SPACING_X = 50;
	private static final int START_X = 120;

	private List<FleetModel> 	mFleets;

	@Override
	protected void onCreate () {
		mFleets = mPlayer.getfleets();
	}

	@Override
	public void onDraw (int gameTime, int screenTime) {
		draw(Art.bg_1, 0, 0);
		drawBigString("ARMADA", 6, 6);

		drawString("Name", 		6, 24);
		
		drawString("Loc.", 	START_X, 24);
		drawString("Ships", 	START_X + 60 + SPACING_X * 1, 24);
		drawString("Pow.", 	START_X + 60 + SPACING_X * 2, 24);
		drawString("Def.",	START_X + 60 + SPACING_X * 3, 24);
		
		int x = 6;
		int i = 0;
		for (FleetModel fleet : mFleets) {
			drawRectangle(4, START_Y + i * (LINE_HEIGHT + 1), Constants.GAME_WIDTH - 8, LINE_HEIGHT, i % 2 == 0 ? Color.rgba8888(0.85f, 1, 0.85f, 0.55f) : Color.rgba8888(0.85f, 1, 0.85f, 0.4f));
			drawString(fleet.getName(), 8, START_Y + 5 + i * (LINE_HEIGHT + 1), 16);
			drawString(fleet.getLocationName(), START_X + 2, START_Y + 5 + i * (LINE_HEIGHT + 1), 16);
			drawString(String.valueOf((int)fleet.getNbShip()), START_X + 62 + SPACING_X * 1, START_Y + 5 + i * (LINE_HEIGHT + 1));
			drawString(String.valueOf((int)fleet.getPower()), START_X + 62 + SPACING_X * 2, START_Y + 5 + i * (LINE_HEIGHT + 1));
			drawString(String.valueOf((int)fleet.getDefense()), START_X + 62 + SPACING_X * 3, START_Y + 5 + i * (LINE_HEIGHT + 1));
			i++;
		}
	}

	@Override
	public void onTouch (int x, int y) {
		if (y > START_Y) {
			int pos = (y - START_Y) / (LINE_HEIGHT + 1);
			if (mFleets.size() > pos) {
				addScreen(new PanelFleetScreen(mFleets.get(pos)));
			}
		}
	}

	@Override
	public void onMove (int offsetX, int offsetY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLongTouch (int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
