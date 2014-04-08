
package com.mojang.metagun.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
	protected Game 					mGame;
	protected SpriteBatch 			mSpriteBatch;
	private int 						mScreenTime;
	private int 						mBackHistory;
	private List<View>				mViews;
	protected PlayerModel 			mPlayer;
	protected Screen 					mParent;
	protected int 						mCycle;
	private int 						mGameTime;
	private int 						mGameTimeAtStart;
	private SpriteCache 				mSpriteCache;
	private int 						mSpriteCacheId;
	protected int 						mDeprecatedPosX;
	protected int 						mDeprecatedPosY;
	protected int 						mRealPosX;
	protected int 						mRealPosY;
	private boolean 					mIsChangeNotified;
	protected TextureRegion 		mParalax;
	protected boolean					mParalaxNotified;
	private int 						mOffsetX;
	private int 						mFinalOffsetX;
	protected Pixmap mPixmap;

	public Screen() {
		mViews = new ArrayList<View>();
		mIsChangeNotified = true;
		mParalaxNotified = true;
		mSpriteCache = new SpriteCache(5000, true);
	}

	public void dispose () {
		mSpriteBatch.dispose();
		mSpriteBatch = null;
	}

	public final void init (Game game, int gameTime) {
		System.out.println("Screen init");
		
		
		mGameTime = gameTime;
		mGameTimeAtStart = gameTime;
		mPlayer = GameService.getInstance().getPlayer();
		mGame = game;
		mScreenTime = 0;//Constants.TOUCH_RECOVERY / 2;
		mBackHistory = 0;
//		mTouch = -1;
//		mLongTouch = -1;
		Matrix4 projection = new Matrix4();
		projection.setToOrtho(0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT, 0, -1, 1);

		mSpriteBatch = new SpriteBatch(100);
		mSpriteBatch.setProjectionMatrix(projection);
		
		onCreate();
	}

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

	public void onBack () {
	}

	protected void addView(View v) {
		mViews.add(v);
	}

	public void drawRectangle(int x, int y, int width, int height, int color) {
		Pixmap pixmap = new Pixmap(32, 32, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, 32, 32);
		Texture pixmaptex = new Texture(pixmap);
		TextureRegion region = new TextureRegion(pixmaptex);
		mSpriteCache.add(region, x, y, width, height);
		//mSpriteBatch.draw(region, x, y, width, height);
		pixmap.dispose();
	}
	
	public void drawRectangle(int x, int y, int width, int height, Color color) {
		drawRectangle(null, x, y, width, height, color);
	}
	
	public void drawRectangle(SpriteBatch bacth, int x, int y, int width, int height, Color color) {
		Pixmap pixmap = new Pixmap(32, 32, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, 32, 32);
		Texture pixmaptex = new Texture(pixmap);
		TextureRegion region = new TextureRegion(pixmaptex);
		if (bacth != null) {
			bacth.draw(region, x, y, width, height);
		} else {
			mSpriteCache.add(region, x, y, width, height);
		}
		pixmap.dispose();
	}

	protected void drawRectangle (int x, int y, int width, int height, Color color, int angle) {
		drawRectangle(null, x, y, width, height, color, angle);
	}
	
	protected void drawRectangle (SpriteBatch bacth, int x, int y, int width, int height, Color color, int angle) {
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, width, height);
		Texture pixmaptex = new Texture(pixmap);
		Sprite line = new Sprite(pixmaptex);
		line.setRotation(angle);
		line.setPosition(x, y);
		if (bacth != null) {
			line.draw(bacth);
		} else {
			mSpriteCache.add(line);
		}
		pixmap.dispose();
	}
	
	public void draw (SpriteBatch batch, TextureRegion region, int x, int y) {
		draw(batch, region, x, y, null);
	}
	
	public void draw (SpriteBatch batch, TextureRegion region, int x, int y, Color color) {
		Sprite sprite = new Sprite(region);
		sprite.setPosition(x, y);
		if (color != null) {
			sprite.setColor(color);
		}
		if (batch != null) {
			sprite.draw(batch);
		} else {
			mSpriteCache.add(sprite);
		}
	}

	protected void draw (TextureRegion region, int x, int y, int angle) {
		Sprite sprite = new Sprite(region);
		sprite.setRotation(angle);
		sprite.setPosition(x, y);
		//sprite.draw(mSpriteBatch);
		mSpriteCache.add(sprite);
	}

	protected void draw (Pixmap pixmap, int x, int y) {
		Texture pixmaptex = new Texture(pixmap);
		TextureRegion region = new TextureRegion(pixmaptex);
		mSpriteCache.add(region, x, y);
	}

	public void draw (TextureRegion region, int x, int y) {
		int width = region.getRegionWidth();
		if (width < 0) width = -width;
		//mSpriteBatch.draw(region, x, y, width, region.getRegionHeight());
		mSpriteCache.add(region, x, y, width, region.getRegionHeight());
	}

	public void drawString (String string, int x, int y, int truncate) {
		drawString(null, string, x, y, truncate, null);
	}

	public void drawString (SpriteBatch batch, String string, int x, int y, Color color) {
		drawString(batch, string, x, y, Integer.MAX_VALUE, color);
	}

	public void drawString (String string, int x, int y, Color color) {
		drawString(null, string, x, y, Integer.MAX_VALUE, color);
	} 
	
	public void drawString (SpriteBatch batch, String string, int x, int y) {
		drawString(batch, string, x, y, Integer.MAX_VALUE, null);
	}
	
	public void drawString (String string, int x, int y) {
		drawString(null, string, x, y, Integer.MAX_VALUE, null);
	}
	
	public void drawString (SpriteBatch batch, String string, int x, int y, int truncate, Color color) {
		string = string.toUpperCase();
		for (int i = 0; i < Math.min(string.length(), truncate); i++) {
			char ch = string.charAt(i);
			for (int ys = 0; ys < CHARS.length; ys++) {
				int xs = CHARS[ys].indexOf(ch);
				if (xs >= 0) {
					draw(batch, Art.guys[xs][ys + 9], x + i * 6, y, color);
				}
			}
		}

		if (string.length() >= truncate) {
			draw(batch, Art.guys[20][10], x + truncate * 6, y, color);
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
		
		if (mCycle != cycle) {
			mIsChangeNotified = true;
		}
		
		mScreenTime = gameTime - mGameTimeAtStart;
		mGameTime = gameTime;
		mCycle = cycle;
		
		Matrix4 projection = new Matrix4();
		projection.setToOrtho(mOffsetX, Constants.GAME_WIDTH + mOffsetX, Constants.GAME_HEIGHT, 0, -1, 1);
		mSpriteBatch.setProjectionMatrix(projection);

		if (mParalax != null) {
			mSpriteBatch.begin();
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			draw(mSpriteBatch, mParalax, mRealPosX / 8 - 320, mRealPosY / 8 - 240);
			mSpriteBatch.end();
			mParalaxNotified = false;
		}

		if (mParent != null) {
			mParent.render(gameTime, cycle, renderTime);
		}
		
		if (mIsChangeNotified) {
			mSpriteCache.clear();
			mSpriteCache.beginCache();
			onDraw(mGameTime, mScreenTime);
			mSpriteCacheId = mSpriteCache.endCache();
			mIsChangeNotified = false;
		}

		// Cache
		if (mSpriteCache != null) {
			Gdx.gl.glEnable(GL30.GL_BLEND);
			Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

			//mSpriteCache.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	
			Matrix4 projection2 = new Matrix4();
			projection2.setToOrtho(-mRealPosX + mOffsetX, Constants.GAME_WIDTH - mRealPosX + mOffsetX, Constants.GAME_HEIGHT - mRealPosY, -mRealPosY, -1, 1);
	
			mSpriteCache.setProjectionMatrix(projection2);  
			mSpriteCache.begin();  
			mSpriteCache.draw(mSpriteCacheId);  
			mSpriteCache.end();
		}
		
		mSpriteBatch.begin();

		// Render dynamic elements
		onRender(mSpriteBatch, mGameTime, mScreenTime);

		// UI
		for (View view: mViews) {
			if (view.isVisible()) {
				view.draw(mSpriteBatch);
			}
		}

		drawString(mSpriteBatch, String.valueOf(renderTime), 0, 0);

		mSpriteBatch.end();
		
		Game.sRender = System.currentTimeMillis() - time;
		if (System.currentTimeMillis() - time >= 2) {
			//System.out.println("time: " + (System.currentTimeMillis() - time) + "ms");
		}
		
		if (mOffsetX < mFinalOffsetX) {
			mOffsetX = Math.min(mFinalOffsetX, mOffsetX + 42);
			Gdx.graphics.requestRendering();
		}
		
		if (mOffsetX > mFinalOffsetX) {
			mOffsetX = Math.max(mFinalOffsetX, mOffsetX - 42);
			Gdx.graphics.requestRendering();
		}

	}

	public void onRender(SpriteBatch spriteBatch, int gameTime, int screenTime) {
		
	}
	
	protected abstract void onCreate ();
	protected abstract void onDraw(int gameTime, int screenTime);
	public abstract void onTouch(int x, int y);
	public abstract void onLongTouch(int x, int y);
	public abstract void onMove(int offsetX, int offsetY);

	public void tick (int gameTime, int cycle) {
		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			onBack();
			mGame.goBack();
			mScreenTime = 0;
			return;
		}
	}

	public void onPrev () {
	}

	public void onNext () {
	}
	
	protected void notifyChange () {
		mIsChangeNotified = true;
	}

	public void tap (int x, int y) {
		for (View view: mViews) {
			if (view.isClickable() && view.contains(x, y)) {
				view.click();
				mGameTimeAtStart = mGameTime;
				return;
			}
		}
		
		onTouch(x, y);
	}

	public void setOffset (int offset, int finalOffsetX) {
		mOffsetX = offset;
		mFinalOffsetX = finalOffsetX;
	}

	public boolean isEnded () {
		return mOffsetX == mFinalOffsetX;
	}

}
