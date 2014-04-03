
package com.mojang.metagun.screen;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.mojang.metagun.Art;
import com.mojang.metagun.Constants;
import com.mojang.metagun.Game;

public abstract class Screen {
	private static final int 		TOUCH_INTERVAL = 32;
	private static final String[]	CHARS = {"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", ".,!?:;\"'+-=/\\< "};
	protected static Random 		sRandom = new Random();
	private Game 						mGame;
	public SpriteBatch 				mSpriteBatch;
	private int 						mTime;
	private int 						mBackHistory;
	private int 						mTouch;
	private int 						mTouchX;
	private int 						mTouchY;
	private int 						mLastTouchX;
	private int 						mLastTouchY;

	public void removed () {
		mSpriteBatch.dispose();
	}

	public final void init (Game game) {
		this.mGame = game;
		this.mTime = Constants.TOUCH_RECOVERY / 2;
		mBackHistory = 0;
		mTouch = -1;
		Matrix4 projection = new Matrix4();
		projection.setToOrtho(0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT, 0, -1, 1);

		mSpriteBatch = new SpriteBatch(100);
		mSpriteBatch.setProjectionMatrix(projection);
	}

	protected void addScreen (Screen screen) {
		mGame.addScreen(screen);
	}

	protected void setScreen (Screen screen) {
		mGame.setScreen(screen);
	}

//	public void drawLine(int fromX, int fromY, int toX, int toY, int color) {
//		Pixmap pixmap = new Pixmap(32, 32, Format.RGBA8888);
//		pixmap.setColor(color);
//		pixmap.fillRectangle(0, 0, 32, 32);
//		Texture pixmaptex = new Texture(pixmap);
//		TextureRegion region = new TextureRegion(pixmaptex);
//		mSpriteBatch.draw(region, x, y, width, height);
//		pixmap.dispose();
//	}

	public void drawRectangle(int x, int y, int width, int height, int color) {
		Pixmap pixmap = new Pixmap(32, 32, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, 32, 32);
		Texture pixmaptex = new Texture(pixmap);
		TextureRegion region = new TextureRegion(pixmaptex);
		mSpriteBatch.draw(region, x, y, width, height);
		pixmap.dispose();
	}
	
	public void draw (TextureRegion region, int x, int y, Color color) {
		Sprite sprite = new Sprite(region);
		sprite.setPosition(x, y);
		if (color != null) {
			sprite.setColor(color);
		}
		sprite.draw(mSpriteBatch);
	}

	public void draw (TextureRegion region, int x, int y) {
		int width = region.getRegionWidth();
		if (width < 0) width = -width;
		mSpriteBatch.draw(region, x, y, width, region.getRegionHeight());
	}

	public void drawString (String string, int x, int y) {
		drawString(string, x, y, null);
	}
	
	public void drawString (String string, int x, int y, Color color) {
		string = string.toUpperCase();
		for (int i = 0; i < string.length(); i++) {
			char ch = string.charAt(i);
			if (ch == '(') {
				draw(Art.guys[18][10], x + i * 6, y, color);
			}
			if (ch == ')') {
				draw(Art.guys[19][10], x + i * 6, y, color);
			}
			for (int ys = 0; ys < CHARS.length; ys++) {
				int xs = CHARS[ys].indexOf(ch);
				if (xs >= 0) {
					draw(Art.guys[xs][ys + 9], x + i * 6, y, color);
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
			for (int ys = 0; ys < CHARS.length; ys++) {
				int xs = CHARS[ys].indexOf(ch);
				if (xs >= 0) {
					draw(Art.bigText[xs][ys + 7], x + i * 12, y);
				}
			}
		}
	}

	public abstract void render ();

	public abstract void onTouch(int x, int y);
	public abstract void onMove(int offsetX, int offsetY);

	public void tick () {
		mTime++;
		
		if (mTime < Constants.TOUCH_RECOVERY) {
			return;
		}

		if (Gdx.input.getDeltaX() > 30) {
			mBackHistory = 4;
		}

		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			mGame.goBack();
			mTime = 0;
			return;
		}

		// Start touch
		if (mTouch == -1 && Gdx.input.isTouched()) {
			mTouch = mTime;
			mTouchX = mLastTouchX = Gdx.input.getX() * Constants.GAME_WIDTH / Gdx.graphics.getWidth();
			mTouchY = mLastTouchY = Gdx.input.getY() * Constants.GAME_HEIGHT / Gdx.graphics.getHeight();
		}
		
		// Stop touch
		else if (mTouch != -1 && !Gdx.input.isTouched()) {
			if (mGame.getHistoryScreen().size() > 0 && mBackHistory > 0) {
				mTime = 0;
				mBackHistory = 0;
				mGame.goBack();
				return;
			}
			if (Math.abs(mTouchX - mLastTouchX) < 5  && Math.abs(mTouchY - mLastTouchY) < 5 &&  mTouch + TOUCH_INTERVAL > mTime) {
				onTouch(mTouchX, mTouchY);
			}
			mTouch = -1;
		}
		
		// Move
		if (mTouch != -1 && Gdx.input.isTouched()) {
			int x = Gdx.input.getX() * Constants.GAME_WIDTH / Gdx.graphics.getWidth();
			int y = Gdx.input.getY() * Constants.GAME_HEIGHT / Gdx.graphics.getHeight();
			
			mBackHistory--;
			
			if (mLastTouchX != x || mLastTouchY != y) {
//				System.out.println("onMove: " + (mLastTouchX - x) + ", " + (mLastTouchY - y));
				onMove(x - mLastTouchX, y - mLastTouchY);
			}
			
			mLastTouchX = x;
			mLastTouchY = y;
		}
	}

}
