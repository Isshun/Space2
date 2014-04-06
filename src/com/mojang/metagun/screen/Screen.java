
package com.mojang.metagun.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.mojang.metagun.Art;
import com.mojang.metagun.Constants;
import com.mojang.metagun.Game;
import com.mojang.metagun.model.PlayerModel;
import com.mojang.metagun.service.GameService;
import com.mojang.metagun.ui.View;

public abstract class Screen {
	private static final int 		TOUCH_INTERVAL = 750;
	private static final int 		LONG_TOUCH_INTERVAL = 500;
	
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
	private boolean 					mIsMoving;
	private int 						mLongTouch;
	protected SpriteCache 			mSystemSprite;
	protected int 						mCacheId;
	protected int 						mPosX;
	protected int 						mPosY;

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
		mScreenTime = 0;//Constants.TOUCH_RECOVERY / 2;
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
		onBack();
		return mGame.goBack();
	}

	protected void onBack () {
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
		drawRectangle(null, x, y, width, height, color);
	}
	
	public void drawRectangle(SpriteCache cache, int x, int y, int width, int height, Color color) {
		Pixmap pixmap = new Pixmap(32, 32, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, 32, 32);
		Texture pixmaptex = new Texture(pixmap);
		TextureRegion region = new TextureRegion(pixmaptex);
		mSpriteBatch.draw(region, x, y, width, height);
		pixmap.dispose();
	}

	protected void drawRectangle (int x, int y, int width, int height, Color color, int angle) {
		drawRectangle(null, x, y, width, height, color, angle);
	}
	
	protected void drawRectangle (SpriteCache cache, int x, int y, int width, int height, Color color, int angle) {
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, width, height);
		Texture pixmaptex = new Texture(pixmap);
		Sprite line = new Sprite(pixmaptex);
		line.setRotation(angle);
		line.setPosition(x, y);
		if (cache != null) {
			cache.add(line);
		} else {
			line.draw(mSpriteBatch);
		}
		pixmap.dispose();
	}

	public void draw (SpriteCache cache, TextureRegion region, int x, int y, Color color) {
		Sprite sprite = new Sprite(region);
		sprite.setPosition(x, y);
		if (color != null) {
			sprite.setColor(color);
		}
		if (cache != null) {
			cache.add(sprite);
		} else {
			sprite.draw(mSpriteBatch);
		}
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

	/**
	 * Draw SpriteCache
	 * 
	 * @param cache 
	 * @param cacheId
	 */
	public void draw (SpriteCache cache, int cacheId) {
		if (cache != null && cacheId != -1) {
			Gdx.gl.glEnable(GL30.GL_BLEND);
			Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
	
			Matrix4 projection = new Matrix4();
			projection.setToOrtho(-mPosX, Constants.GAME_WIDTH - mPosX, Constants.GAME_HEIGHT - mPosY, -mPosY, -1, 1);
	
			cache.setProjectionMatrix(projection);  
			cache.begin();  
			cache.draw(cacheId);  
			cache.end();
		}
	}

	public void drawString (String string, int x, int y, int truncate) {
		drawString(null, string, x, y, truncate, null);
	}
	
	public void drawString (SpriteCache cache, String string, int x, int y, Color color) {
		drawString(cache, string, x, y, Integer.MAX_VALUE, color);
	}

	public void drawString (String string, int x, int y, Color color) {
		drawString(null, string, x, y, Integer.MAX_VALUE, color);
	}
	
	public void drawString (String string, int x, int y) {
		drawString(null, string, x, y, Integer.MAX_VALUE, null);
	}
	
	public void drawString (SpriteCache cache, String string, int x, int y, int truncate, Color color) {
		string = string.toUpperCase();
		for (int i = 0; i < Math.min(string.length(), truncate); i++) {
			char ch = string.charAt(i);
			for (int ys = 0; ys < CHARS.length; ys++) {
				int xs = CHARS[ys].indexOf(ch);
				if (xs >= 0) {
					draw(cache, Art.guys[xs][ys + 9], x + i * 6, y, color);
				}
			}
		}

		if (string.length() >= truncate) {
			draw(cache, Art.guys[20][10], x + truncate * 6, y, color);
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

	public void render (int gameTime, int cycle, long renderTime) {
		long time = System.currentTimeMillis();
		
		mScreenTime = gameTime - mGameTimeAtStart;
		mGameTime = gameTime;
		mCycle = cycle;
		
		if (mParent != null) {
			mParent.render(gameTime, cycle, renderTime);
		}
		
		mSpriteBatch.begin();
		onRender(mSpriteBatch, mGameTime, mScreenTime);
		mSpriteBatch.end();

		// Cache
		if (mSystemSprite != null) {
			Gdx.gl.glEnable(GL30.GL_BLEND);
			Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
	
			Matrix4 projection = new Matrix4();
			projection.setToOrtho(-mPosX, Constants.GAME_WIDTH - mPosX, Constants.GAME_HEIGHT - mPosY, -mPosY, -1, 1);
	
			mSystemSprite.setProjectionMatrix(projection);  
			mSystemSprite.begin();  
			mSystemSprite.draw(mCacheId);  
			mSystemSprite.end();
		}
		
		// UI
		mSpriteBatch.begin();
		for (View view: mViews) {
			if (view.isVisible()) {
				view.draw(mSpriteBatch);
			}
		}

		drawString(String.valueOf(renderTime), 0, 0);

		mSpriteBatch.end();
		
		Game.sRender = System.currentTimeMillis() - time;
		if (System.currentTimeMillis() - time >= 2) {
			//System.out.println("time: " + (System.currentTimeMillis() - time) + "ms");
		}
	}

	public abstract void onRender(SpriteBatch spriteBatch, int gameTime, int screenTime);
	public abstract void onTouch(int x, int y);
	public abstract void onLongTouch(int x, int y);
	public abstract void onMove(int offsetX, int offsetY);

	public void tick (int gameTime, int cycle) {
		mScreenTime = gameTime - mGameTimeAtStart;

//		System.out.println("tick: " + mScreenTime + ", " + Constants.TOUCH_RECOVERY);
		
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
			onBack();
			mGame.goBack();
			mScreenTime = 0;
			return;
		}

		// Start touch
		if (mTouch == -1 && Gdx.input.isTouched()) {
//			System.out.println("start touch: " + mGameTime);
			mTouch = mScreenTime;
			mLongTouch = -1;
			mTouchX = mLastTouchX = Gdx.input.getX() * Constants.GAME_WIDTH / Gdx.graphics.getWidth();
			mTouchY = mLastTouchY = Gdx.input.getY() * Constants.GAME_HEIGHT / Gdx.graphics.getHeight();
		}
		
		// Stop long touch
		else if (mLongTouch != -1 && !Gdx.input.isTouched()) {
//			System.out.println("stop long touch: " + mGameTime);
			mTouch = -1;
			mLongTouch = -1;
		}
		
		// Stop touch
		else if (mLongTouch == -1 && mTouch != -1 && !Gdx.input.isTouched()) {
//			System.out.println("stop touch: " + mGameTime);
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

//	public void render (SpriteBatch spriteBatch) {
//		mSpriteBatch.begin();
////		
//////		onRender(mSpriteBatch);
////
////		for (View view: mViews) {
////			view.draw(mSpriteBatch);
////		}
////
//		mSpriteBatch.end();
//	}
//
//	public SpriteBatch getSpriteBatch () {
//		return mSpriteBatch;
//	}

}
