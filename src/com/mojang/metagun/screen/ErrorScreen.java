package com.mojang.metagun.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mojang.metagun.ui.TextView;

public class ErrorScreen extends Screen {

	public static final int RESOLUTION_NOT_SUPPORTED = 1;

	public ErrorScreen (int error) {
		TextView text = new TextView("error", 6, 6);
		addView(text);
	}

	@Override
	protected void onCreate () {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRender (SpriteBatch spriteBatch, int gameTime, int screenTime) {
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
	public void onLongTouch (int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
