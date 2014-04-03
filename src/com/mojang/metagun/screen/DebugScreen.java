package com.mojang.metagun.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DebugScreen extends Screen {

	@Override
	protected void onCreate () {
		// TODO Auto-generated method stub

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
	public void onRender (SpriteBatch spriteBatch) {
		drawString("DEBUG", 4, 4);
	}

}
