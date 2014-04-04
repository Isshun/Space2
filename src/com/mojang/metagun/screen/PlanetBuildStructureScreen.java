package com.mojang.metagun.screen;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mojang.metagun.Constants;
import com.mojang.metagun.model.PlanetModel;
import com.mojang.metagun.model.ShipClassModel;
import com.mojang.metagun.service.GameService;

public class PlanetBuildStructureScreen extends Screen {

	public PlanetBuildStructureScreen (Screen parent, PlanetModel mPlanet) {
		mParent = parent;
	}

	@Override
	protected void onCreate () {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRender (SpriteBatch spriteBatch, int gameTime, int screenTime) {
		int posY = Constants.GAME_HEIGHT - 100;

		drawRectangle(0, posY, Constants.GAME_WIDTH, 100, new Color(0.5f, 0.5f, 1, 0.5f));
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
