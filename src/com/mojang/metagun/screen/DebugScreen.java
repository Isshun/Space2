package com.mojang.metagun.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mojang.metagun.Constants;
import com.mojang.metagun.service.GameService;

public class DebugScreen extends Screen {

	@Override
	protected void onCreate () {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTouch (int x, int y) {
		if (x < Constants.GAME_WIDTH / 2) {
			GameService.getInstance().initDebug(-1);
		} else {
			GameService.getInstance().initDebug(1);
		}
	}

	@Override
	public void onMove (int offsetX, int offsetY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRender (SpriteBatch spriteBatch) {
		drawString("map index: " + GameService.getInstance().mMapIndex, 4, 4);
	}

}
