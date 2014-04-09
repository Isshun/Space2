
package org.bluebox.space2.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bluebox.space2.Art;
import org.bluebox.space2.Constants;
import org.bluebox.space2.Game;
import org.bluebox.space2.Game.Anim;
import org.bluebox.space2.StringConfig;
import org.bluebox.space2.model.PlayerModel;
import org.bluebox.space2.service.GameService;
import org.bluebox.space2.ui.View;

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

public abstract class Screen {
	private static final int 		TOUCH_INTERVAL = 750;
	private static final int 		LONG_TOUCH_INTERVAL = 500;
	
	public static final String[]	CHARS = {"abcdefghijklmnopqrstuvwxyz0123456789", ".,!?:;\"'+-=/\\<%   () $^&*@"};
	
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
	protected SpriteCache 			mCacheUI;
	protected int 						mCacheUIId;
	protected int 						mDeprecatedPosX;
	protected int 						mDeprecatedPosY;
	protected int 						mRealPosX;
	protected int 						mRealPosY;
	private boolean 					mIsChangeNotified;
	protected TextureRegion 		mParalax;
	protected boolean					mParalaxNotified;
	private int 						mOffsetX;
	private int 						mFinalOffsetX;
	protected Pixmap 					mPixmap;
	private int 						mOffsetY;
	private int 						mFinalOffsetY;
	private Color						mColorBad;
	private Color						mColorGood;
	private StringConfig				mStringConfig;
	protected boolean 				mRefreshOnUpdate;
	protected Anim 					mOutTransition;
	private boolean mIsInitialized;
	
	public Screen() {
		mOutTransition = Anim.NO_TRANSITION;
		mViews = new ArrayList<View>();
		mIsChangeNotified = true;
		mParalaxNotified = true;
		mSpriteCache = new SpriteCache(5000, true);
		mCacheUI = new SpriteCache(5000, true);
		mColorBad = new Color(220f/255, 40f/255, 50f/255, 1);
		mColorGood = new Color(0.5f, 1, 0.5f, 1);
		mStringConfig = new StringConfig();
	}

	public void dispose () {
		System.out.println("Screen dispose: " + this.getClass().getName());
		mSpriteBatch.dispose();
		mSpriteBatch = null;
	}

	public final void init (Game game, int gameTime) {
		System.out.println("Screen init: " + this.getClass().getName());
		
		if (mIsInitialized) {
			mIsChangeNotified = true;
			return;
		}
		
		mIsInitialized = true;
		mGameTime = gameTime;
		mGameTimeAtStart = gameTime;
		mPlayer = GameService.getInstance().getPlayer();
		mGame = game;
		mScreenTime = 0;//Constants.TOUCH_RECOVERY / 2;
		mBackHistory = 0;
		mIsChangeNotified = true;
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
		drawRectangle(null, mSpriteCache, x, y, width, height, color);
	}

	public void drawRectangle(SpriteCache cache, int x, int y, int width, int height, Color color) {
		drawRectangle(null, cache, x, y, width, height, color);
	}
	
	public void drawRectangle(SpriteBatch batch, int x, int y, int width, int height, Color color) {
		drawRectangle(batch, null, x, y, width, height, color);
	}
	
	public void drawRectangle(SpriteBatch bacth, SpriteCache cache, int x, int y, int width, int height, Color color) {
		Pixmap pixmap = new Pixmap(32, 32, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, 32, 32);
		Texture pixmaptex = new Texture(pixmap);
		TextureRegion region = new TextureRegion(pixmaptex);
		if (bacth != null) {
			bacth.draw(region, x, y, width, height);
		} else {
			cache.add(region, x, y, width, height);
		}
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
//		if (bacth != null) {
//			line.draw(bacth);
//		} else {
			mSpriteCache.add(line);
//		}
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
		draw(mSpriteCache, region, x, y);
	}

	public void draw (SpriteCache cache, TextureRegion region, int x, int y) {
		int width = region.getRegionWidth();
		if (width < 0) width = -width;
		cache.add(region, x, y, width, region.getRegionHeight());
	}

	protected void setStringMaxWidth (int width) {
		mStringConfig.maxWidth = width;
	}

	protected void setStringMultiline (boolean isMultiline) {
		mStringConfig.isMultiline = isMultiline;
	}

	protected void setStringColorNumbers (boolean isColored) {
		mStringConfig.isNumbersColored = isColored;
	}

	protected void setStringColor (Color color) {
		mStringConfig.color = color;
	}

	protected void setStringSize(int size) {
		mStringConfig.size = size;
	}

	public int drawString (SpriteBatch batch, String string, int x, int y) {
		
		boolean isBig = mStringConfig.size == StringConfig.SIZE_BIG;
		int lineHeight = isBig ? 18 : 10;
		int charWidth = isBig ? 12 : 6;
		int line = 0;
		
		// Multilines
		if (mStringConfig.isMultiline) {
			String rest = string;
			int lineLength = mStringConfig.maxWidth / charWidth;
			for (; rest.length() > lineLength; line++) {
				int index = rest.lastIndexOf(' ', lineLength);
				if (index == -1) {
					index = lineLength;
				}
				String sub = rest.substring(0, index);
				rest = rest.substring(index + 1);
				System.out.println("sub: " + index + ", " + sub);
				if (isBig) {
					drawBigString(batch, sub, x, y + line * lineHeight);
				} else {
					drawString(null, sub, x, y + line * lineHeight, Integer.MAX_VALUE);
				}
			}
			string = rest;
		}

		if (isBig) {
			drawBigString(batch, string, x, y + line * lineHeight);
		} else {
			drawString(batch, string, x, y + line * lineHeight, Integer.MAX_VALUE);
		}
		
		resetStringConfig();
		return line + 1;
	}
	
	private void resetStringConfig () {
		mStringConfig.color = null;
		mStringConfig.isMultiline = false;
		mStringConfig.maxWidth = Integer.MAX_VALUE;
		mStringConfig.size = StringConfig.SIZE_REGULAR;
	}

	public int drawString (String string, int x, int y) {
		return drawString(null, string, x, y);
	}
	
	private void drawString (SpriteBatch batch, String string, int x, int y, int truncate) {
		string = string.toLowerCase();
		for (int i = 0; i < Math.min(string.length(), truncate); i++) {
			char ch = string.charAt(i);
			if (ch == 'é' || ch == 'è') {
				ch = 'e';
			}
			if (ch == 'à') {
				ch = 'a';
			}
			if (mStringConfig.isNumbersColored) {
				if (ch == ' ') {
					mStringConfig.color = null;
				}
				if (ch == '+') {
					mStringConfig.color = mColorGood;
				}
				if (ch == '-') {
					mStringConfig.color = mColorBad;
				}
			}
			for (int ys = 0; ys < CHARS.length; ys++) {
				int xs = CHARS[ys].indexOf(ch);
				if (xs >= 0) {
					draw(batch, Art.guys[xs][ys + 9], x + i * 6, y, mStringConfig.color);
				}
			}
		}

		if (string.length() >= truncate) {
			draw(batch, Art.guys[20][10], x + truncate * 6, y, mStringConfig.color);
		}
	}

	private void drawBigString (SpriteBatch batch, String string, int x, int y) {
		string = string.toLowerCase();
		for (int i = 0; i < string.length(); i++) {
			char ch = string.charAt(i);
			for (int ys = 0; ys < CHARS.length; ys++) {
				int xs = CHARS[ys].indexOf(ch);
				if (xs >= 0) {
					draw(batch, Art.bigText[xs][ys + 7], x + i * 12, y, mStringConfig.color);
				}
			}
		}
	}

	public void render (int gameTime, int cycle, long renderTime) {
		long time = System.currentTimeMillis();
		
		if (mCycle != cycle && mRefreshOnUpdate) {
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
			mCacheUI.clear();
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
			projection2.setToOrtho(-mRealPosX + mOffsetX, Constants.GAME_WIDTH - mRealPosX + mOffsetX, Constants.GAME_HEIGHT - mRealPosY + mOffsetY, -mRealPosY + mOffsetY, -1, 1);

			mSpriteCache.setProjectionMatrix(projection2);  
			mSpriteCache.begin();  
			mSpriteCache.draw(mSpriteCacheId);  
			mSpriteCache.end();
		}
		
		
		// Render non cached
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

		
		// Cache UI
		if (mCacheUI != null && mCacheUIId != 0) {
			Gdx.gl.glEnable(GL30.GL_BLEND);
			Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

			//mSpriteCache.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	
			Matrix4 projection2 = new Matrix4();
			projection2.setToOrtho(mOffsetX, Constants.GAME_WIDTH - mOffsetX, Constants.GAME_HEIGHT - mOffsetY, mOffsetY, -1, 1);
	
			mCacheUI.setProjectionMatrix(projection2);  
			mCacheUI.begin();  
			mCacheUI.draw(mCacheUIId);  
			mCacheUI.end();
		}

		
		// Render transition
		if (mOffsetX < mFinalOffsetX) {
			mOffsetX = Math.min(mFinalOffsetX, mOffsetX + Constants.SCREEN_TRANSITION_OFFSET);
			Gdx.graphics.requestRendering();
		}
		
		if (mOffsetX > mFinalOffsetX) {
			mOffsetX = Math.max(mFinalOffsetX, mOffsetX - Constants.SCREEN_TRANSITION_OFFSET);
			Gdx.graphics.requestRendering();
		}

		if (mOffsetY < mFinalOffsetY) {
			mOffsetY = Math.min(mFinalOffsetY, mOffsetY + Constants.SCREEN_TRANSITION_OFFSET);
			Gdx.graphics.requestRendering();
		}
		
		if (mOffsetY > mFinalOffsetY) {
			mOffsetY = Math.max(mFinalOffsetY, mOffsetY - Constants.SCREEN_TRANSITION_OFFSET);
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
	public void onMoveEnd(int x, int y) {
		
	}

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
			if (view.isVisible() && view.isClickable() && view.contains(x, y)) {
				view.click();
				mGameTimeAtStart = mGameTime;
				return;
			}
		}
		
		onTouch(x, y);
		
		mIsChangeNotified = true;
	}

	public void setOffset (int offset, int finalOffsetX) {
		mOffsetX = offset;
		mFinalOffsetX = finalOffsetX;
	}

	public boolean isEnded () {
		return mOffsetX == mFinalOffsetX && mOffsetY == mFinalOffsetY;
	}

	public void setTransition (Anim anim) {
		switch (anim) {
		case FLIP_BOTTOM:
			mOffsetY = -Constants.GAME_HEIGHT;
			mFinalOffsetY = 0;
			break;
		case FLIP_TOP:
			break;
		case FLIP_LEFT:
			break;
		case FLIP_RIGHT:
			break;
		}
	}

	public Anim getAnimOut () {
		return mOutTransition;
	}

	public void setOffsetY (int startOffset, int endOffset) {
		mOffsetY = startOffset;
		mFinalOffsetY = endOffset;
	}

	public void goOut (Anim anim) {
		mParent = null;
		
		if (anim == null) {
			switch (mOutTransition) {
			case GO_DOWN:
				setOffsetY(0, -Constants.GAME_HEIGHT);
				break;
			}
		}
	}

}
