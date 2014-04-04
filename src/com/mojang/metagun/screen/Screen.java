
package com.mojang.metagun.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
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
import com.mojang.metagun.model.PlayerModel;
import com.mojang.metagun.service.GameService;
import com.mojang.metagun.ui.View;

public abstract class Screen {
	private static final int 		TOUCH_INTERVAL = 1000;
	private static final int 		LONG_TOUCH_INTERVAL = 750;
	
	public static final String[]	CHARS = {"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", ".,!?:;\"'+-=/\\<    ()"};
	
	protected static Random 		sRandom = new Random();
	private Game 						mGame;
	protected SpriteBatch 			mSpriteBatch;
	private int 						mScreenTime;
	private int 						mBackHistory;
	private int 						mTouch;
	private int 						mTouchX;
	private int 						mTouchY;
	private int 						mLastTouchX;
	private int 						mLastTouchY;
	private List<View>				mViews;
	protected PlayerModel 			mPlayer;
	protected Screen 					mParent;
	protected int 						mCycle;
	private int 						mGameTime;
	private int 						mGameTimeAtStart;
	private boolean mIsMoving;
	private int mLongTouch;

	public Screen() {
		mViews = new ArrayList<View>();
	}

	public void removed () {
		mSpriteBatch.dispose();
	}

	public final void init (Game game, int gameTime) {
		System.out.println("Screen init");
		
		mGameTime = gameTime;
		mGameTimeAtStart = gameTime;
		mPlayer = GameService.getInstance().getPlayer();
		mGame = game;
		mScreenTime = Constants.TOUCH_RECOVERY / 2;
		mBackHistory = 0;
		mTouch = -1;
		mLongTouch = -1;
		Matrix4 projection = new Matrix4();
		projection.setToOrtho(0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT, 0, -1, 1);

		mSpriteBatch = new SpriteBatch(100);
		mSpriteBatch.setProjectionMatrix(projection);
		
		onCreate();
	}

	protected abstract void onCreate ();

	public boolean isTop () {
		return mGame.isTop(this);
	}
	
	protected void addScreen (Screen screen) {
		mGame.addScreen(screen);
	}

	protected void setScreen (Screen screen) {
		mGame.setScreen(screen);
	}

	protected Screen back () {
		return mGame.goBack();
	}

	protected void addView(View v) {
		mViews.add(v);
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
	
	public void drawRectangle(int x, int y, int width, int height, Color color) {
		Pixmap pixmap = new Pixmap(32, 32, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, 32, 32);
		Texture pixmaptex = new Texture(pixmap);
		TextureRegion region = new TextureRegion(pixmaptex);
		mSpriteBatch.draw(region, x, y, width, height);
		pixmap.dispose();
	}

	protected void drawRectangle (int x, int y, int width, int height, Color color, int angle) {
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, width, height);
		Texture pixmaptex = new Texture(pixmap);
		Sprite line = new Sprite(pixmaptex);
		line.setRotation(angle);
		line.setPosition(x, y);
		line.draw(mSpriteBatch);
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

	protected void draw (TextureRegion region, int x, int y, int angle) {
		Sprite sprite = new Sprite(region);
		sprite.setRotation(angle);
		sprite.setPosition(x, y);
		sprite.draw(mSpriteBatch);
	}

	public void draw (TextureRegion region, int x, int y) {
		int width = region.getRegionWidth();
		if (width < 0) width = -width;
		mSpriteBatch.draw(region, x, y, width, region.getRegionHeight());
	}

	public void drawString (String string, int x, int y, int truncate) {
		drawString(string, x, y, truncate, null);
	}
	
	public void drawString (String string, int x, int y, Color color) {
		drawString(string, x, y, Integer.MAX_VALUE, color);
	}
	
	public void drawString (String string, int x, int y) {
		drawString(string, x, y, Integer.MAX_VALUE, null);
	}
	
	public void drawString (String string, int x, int y, int truncate, Color color) {
		string = string.toUpperCase();
		for (int i = 0; i < Math.min(string.length(), truncate); i++) {
			char ch = string.charAt(i);
			for (int ys = 0; ys < CHARS.length; ys++) {
				int xs = CHARS[ys].indexOf(ch);
				if (xs >= 0) {
					draw(Art.guys[xs][ys + 9], x + i * 6, y, color);
				}
			}
		}

		if (string.length() >= truncate) {
			draw(Art.guys[20][10], x + truncate * 6, y, color);
		}
	}

	public void drawBigString (String string, int x, int y) {
		string = string.toUpperCase();
		for (int i = 0; i < string.length(); i++) {
			char ch = string.charAt(i);
			for (int ys = 0; ys < CHARS.length; ys++) {
				int xs = CHARS[ys].indexOf(ch);
				if (xs >= 0) {
					draw(Art.bigText[xs][ys + 7], x + i * 12, y);
				}
			}
		}
	}

	public void render (int gameTime, int cycle) {
		mScreenTime = gameTime - mGameTimeAtStart;
		mGameTime = gameTime;
		mCycle = cycle;
		
		if (mParent != null) {
			mParent.render(gameTime, cycle);
		}
		
		mSpriteBatch.begin();
		
		onRender(mSpriteBatch, mGameTime, mScreenTime);

		for (View view: mViews) {
			view.draw(mSpriteBatch);
		}

		mSpriteBatch.end();
	}

	public abstract void onRender(SpriteBatch spriteBatch, int gameTime, int screenTime);
	public abstract void onTouch(int x, int y);
	public abstract void onLongTouch(int x, int y);
	public abstract void onMove(int offsetX, int offsetY);

	public void tick (int gameTime, int cycle) {
		mScreenTime = gameTime - mGameTimeAtStart;

//		System.out.println(mScreenTime + ", " + Constants.TOUCH_RECOVERY);
		
		if (mScreenTime < Constants.TOUCH_RECOVERY) {
			return;
		}

		if (!mIsMoving && Gdx.input.isTouched() && Gdx.input.getDeltaX() > 30) {
			onPrev();
			mGameTimeAtStart = mGameTime;
			return;
			//mBackHistory = 4;
		}

		if (!mIsMoving && Gdx.input.isTouched() && Gdx.input.getDeltaX() < -30) {
			onNext();
			mGameTimeAtStart = mGameTime;
			return;
			//mBackHistory = 4;
		}

		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			mGame.goBack();
			mScreenTime = 0;
			return;
		}

		// Start touch
		if (mTouch == -1 && Gdx.input.isTouched()) {
			System.out.println("start touch: " + mGameTime);
			mTouch = mScreenTime;
			mLongTouch = -1;
			mTouchX = mLastTouchX = Gdx.input.getX() * Constants.GAME_WIDTH / Gdx.graphics.getWidth();
			mTouchY = mLastTouchY = Gdx.input.getY() * Constants.GAME_HEIGHT / Gdx.graphics.getHeight();
		}
		
		// Stop long touch
		else if (mLongTouch != -1 && !Gdx.input.isTouched()) {
			System.out.println("stop long touch: " + mGameTime);
			mTouch = -1;
			mLongTouch = -1;
		}
		
		// Stop touch
		else if (mLongTouch == -1 && mTouch != -1 && !Gdx.input.isTouched()) {
			System.out.println("stop touch: " + mGameTime);
			if (mGame.getHistoryScreen().size() > 0 && mBackHistory > 0) {
				mScreenTime = 0;
				mBackHistory = 0;
				mIsMoving = false;
				mGame.goBack();
				return;
			}
			if (Math.abs(mTouchX - mLastTouchX) < 5  && Math.abs(mTouchY - mLastTouchY) < 5 &&  mTouch + TOUCH_INTERVAL > mScreenTime) {
				for (View view: mViews) {
					if (view.isClickable() && view.contains(mTouchX, mTouchY)) {
						view.click();
						mTouch = -1;
						mGameTimeAtStart = mGameTime;
						return;
					}
				}
				onTouch(mTouchX, mTouchY);
			}

			mTouch = -1;
		}
		
		// Move
		if (mTouch != -1 && Gdx.input.isTouched()) {
			// Long touch
			if (Math.abs(mTouchX - mLastTouchX) < 5  && Math.abs(mTouchY - mLastTouchY) < 5 &&  mTouch + LONG_TOUCH_INTERVAL < mScreenTime) {
				System.out.println("Long touch: " + mGameTime);
				mLongTouch = mScreenTime;
				mGameTimeAtStart = mGameTime;
				onLongTouch(mTouchX, mTouchY);
				return;
			}

			mIsMoving = true;
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

	protected void onPrev () {
	}

	protected void onNext () {
	}

	public void render (SpriteBatch spriteBatch) {
		mSpriteBatch.begin();
//		
////		onRender(mSpriteBatch);
//
//		for (View view: mViews) {
//			view.draw(mSpriteBatch);
//		}
//
		mSpriteBatch.end();
	}

	public SpriteBatch getSpriteBatch () {
		return mSpriteBatch;
	}

}
