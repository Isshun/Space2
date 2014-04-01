
package com.mojang.metagun.screen;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.mojang.metagun.Art;
import com.mojang.metagun.Constants;
import com.mojang.metagun.Input;
import com.mojang.metagun.Space2;
import com.mojang.metagun.model.SystemModel;
import com.mojang.metagun.service.GameService;

public abstract class Screen {
	private static final int TOUCH_INTERVAL = 60;
	private final String[] chars = {"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", ".,!?:;\"'+-=/\\< "};
	protected static Random random = new Random();
	private Space2 metagun;
	public SpriteBatch spriteBatch;
	private int time;
	private int mTouch;
	private int mTouchX;
	private int mTouchY;
	private int mLastTouchX;
	private int mLastTouchY;

	public void removed () {
		spriteBatch.dispose();
	}

	public final void init (Space2 metagun) {
		this.metagun = metagun;
		Matrix4 projection = new Matrix4();
		projection.setToOrtho(0, 320, 240, 0, -1, 1);

		spriteBatch = new SpriteBatch(100);
		spriteBatch.setProjectionMatrix(projection);
	}

	protected void setScreen (Screen screen) {
		metagun.setScreen(screen);
	}

	public void drawRectangle(int x, int y, int width, int height, int color) {
		Pixmap pixmap = new Pixmap(32, 32, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, 32, 32);
		Texture pixmaptex = new Texture(pixmap);
		TextureRegion region = new TextureRegion(pixmaptex);
////int width = region.getRegionWidth();
////if (width < 0) width = -width;
		spriteBatch.draw(region, x, y, width, height);
		pixmap.dispose();
		
		//		ShapeRenderer shapeRenderer = new ShapeRenderer(); 
		
		 
////		Texture texture = new Texture();
//		TextureRegion region = new TextureRegion();
//////		int width = region.getRegionWidth();
//////		if (width < 0) width = -width;
//		spriteBatch.draw(region, x, y, width, region.getRegionHeight());
	}
	
	public void draw (TextureRegion region, int x, int y) {
		int width = region.getRegionWidth();
		if (width < 0) width = -width;
		spriteBatch.draw(region, x, y, width, region.getRegionHeight());
	}

	public void drawString (String string, int x, int y) {
		string = string.toUpperCase();
		for (int i = 0; i < string.length(); i++) {
			char ch = string.charAt(i);
			if (ch == '(') {
				draw(Art.guys[18][10], x + i * 6, y);
			}
			if (ch == ')') {
				draw(Art.guys[19][10], x + i * 6, y);
			}
			for (int ys = 0; ys < chars.length; ys++) {
				int xs = chars[ys].indexOf(ch);
				if (xs >= 0) {
					draw(Art.guys[xs][ys + 9], x + i * 6, y);
				}
			}
		}
	}

	public void drawBigString (String string, int x, int y) {
		string = string.toUpperCase();
		for (int i = 0; i < string.length(); i++) {
			char ch = string.charAt(i);
			if (ch == '(') {
				draw(Art.guys[18][10], x + i * 12, y);
			}
			if (ch == ')') {
				draw(Art.guys[19][10], x + i * 12, y);
			}
			for (int ys = 0; ys < chars.length; ys++) {
				int xs = chars[ys].indexOf(ch);
				if (xs >= 0) {
					draw(Art.bigText[xs][ys + 7], x + i * 12, y);
				}
			}
		}
	}

	public abstract void render ();

	public abstract void onTouch(int x, int y);
	public abstract void onMove(int offsetX, int offsetY);

	public void tick (Input input) {
		time++;
		
		if (time < Constants.TOUCH_RECOVERY) {
			return;
		}
		
		// Start touch
		if (mTouch == -1 && Gdx.input.isTouched()) {
			mTouch = time;
			mTouchX = mLastTouchX = Gdx.input.getX() * Space2.GAME_WIDTH / Gdx.graphics.getWidth();
			mTouchY = mLastTouchY = Gdx.input.getY() * Space2.GAME_HEIGHT / Gdx.graphics.getHeight();
		}
		
		// Stop touch
		else if (mTouch != -1 && !Gdx.input.isTouched()) {
			if (mTouch + TOUCH_INTERVAL > time) {
				onTouch(mTouchX, mTouchY);
			}
			mTouch = -1;
		}
		
		// Move
		else if (mTouch != -1 && Gdx.input.isTouched()) {
			int x = Gdx.input.getX() * Space2.GAME_WIDTH / Gdx.graphics.getWidth();
			int y = Gdx.input.getY() * Space2.GAME_HEIGHT / Gdx.graphics.getHeight();
			
//			if (mTouch + TOUCH_INTERVAL < time) {
			System.out.println("onMove: " + (mLastTouchX - x) + ", " + (mLastTouchY - y));
				onMove(x - mLastTouchX, y - mLastTouchY);
//			}
			
			mTouchX = mLastTouchX = x;
			mTouchY = mLastTouchY = y;
		}

		input.releaseAllKeys();
	}
}
