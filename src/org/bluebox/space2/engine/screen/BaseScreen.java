
package org.bluebox.space2.engine.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bluebox.space2.engine.Art;
import org.bluebox.space2.engine.ui.View;
import org.bluebox.space2.game.Constants;
import org.bluebox.space2.game.Game;
import org.bluebox.space2.game.Game.Anim;
import org.bluebox.space2.game.model.PlayerModel;
import org.bluebox.space2.game.service.GameService;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public abstract class BaseScreen {
	private static final int 		TOUCH_INTERVAL = 750;
	private static final int 		LONG_TOUCH_INTERVAL = 500;
	private static final String 	CLASS_NAME = "ScreenBase";
	
	private BaseScreenLayer				mMainLayer;
	private BaseScreenLayer				mTopLayer;
	private BaseScreenLayer				mDynamicLayer;
	private BaseScreenLayer				mUILayer;
	
	protected static Random 		sRandom = new Random();
	protected Game 					mGame;
	private int 						mScreenTime;
	private int 						mBackHistory;
	private List<View>				mViews;
	public PlayerModel 				mPlayer;
	public BaseScreen 				mParent;
	protected int 						mCycle;
	private int 						mGameTime;
	private int 						mGameTimeAtStart;
	protected int 						mRealPosX;
	protected int 						mRealPosY;
	protected float					mZoom;

	protected int 						mDeprecatedPosX;
	protected int 						mDeprecatedPosY;
	protected TextureRegion 		mParalax;
	protected boolean					mParalaxNotified;
	private int 						mOffsetX;
	private int 						mOffsetY;
	private int 						mFinalOffsetX;
	private int 						mFinalOffsetY;
	protected boolean 				mRefreshOnUpdate;
	protected Anim 					mOutTransition;
	private boolean 					mIsInitialized;
	
	public BaseScreen() {
		mMainLayer = BaseScreenLayer.create(BaseScreenLayer.CACHE);
		mTopLayer = BaseScreenLayer.create(BaseScreenLayer.CACHE);
		mUILayer = BaseScreenLayer.create(BaseScreenLayer.CACHE);
		mDynamicLayer = BaseScreenLayer.create(BaseScreenLayer.DYNAMIC);
		mOutTransition = Anim.NO_TRANSITION;
		mViews = new ArrayList<View>();
		mParalaxNotified = true;
		mZoom = 1;
	}

	public void dispose () {
		System.out.println("Screen dispose: " + this.getClass().getName());
		mDynamicLayer.dispose();
		mDynamicLayer = null;
	}

	public final void init (Game game, int gameTime) {
		System.out.println("Screen init: " + this.getClass().getName());
		
		if (mIsInitialized) {
			mMainLayer.notifyChange();
			return;
		}
		
		mIsInitialized = true;
		mGameTime = gameTime;
		mGameTimeAtStart = gameTime;
		mPlayer = GameService.getInstance().getPlayer();
		mGame = game;
		mScreenTime = 0;//Constants.TOUCH_RECOVERY / 2;
		mBackHistory = 0;
		mMainLayer.notifyChange();

		onCreate();
	}

	public boolean isTop () {
		return mGame.isTop(this);
	}
	
	public void addScreen (BaseScreen screen) {
		mGame.addScreen(screen);
	}

	protected void setScreen (BaseScreen screen) {
		mGame.setScreen(screen);
	}

	public BaseScreen back () {
		return mGame.goBack();
	}

	public void onBack () {
	}

	public void onReturn() {
	}
	
	protected void addView(View v) {
		mViews.add(v);
	}


	public void render (int gameTime, int cycle, long renderTime) {
		long time = System.currentTimeMillis();
		
		if (mCycle != cycle && mRefreshOnUpdate) {
			mMainLayer.notifyChange();
		}
		
		mScreenTime = gameTime - mGameTimeAtStart;
		mGameTime = gameTime;
		mCycle = cycle;
		
//		if (mParalax != null) {
//			mSpriteBatch.begin();
//			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//			draw(mSpriteBatch, mParalax, mRealPosX / 8 - 320, mRealPosY / 8 - 240);
//			mSpriteBatch.end();
//			mParalaxNotified = false;
//		}

		// Redraw CacheScreenLayer if changed
		if (mMainLayer.isChange() || mTopLayer.isChange()) {
			Gdx.app.log(CLASS_NAME, "Redraw");
			
			mTopLayer.clear();
			mMainLayer.clear();
			mTopLayer.begin();
			mMainLayer.begin();
			onDraw(mMainLayer, mTopLayer);
			mTopLayer.end();
			mMainLayer.end();
		}
		
		// Redraw CacheScreenLayer if changed
		if (mUILayer.isChange()) {
			mUILayer.clear();
			mUILayer.begin();
			for (View view: mViews) {
				if (view.isVisible()) {
					mUILayer.draw(view);
				}
			}
			mUILayer.end();
		}
		
		// Render parent
		if (mParent != null) {
			mParent.render(gameTime, cycle, renderTime);
		}

		// Render main layer
		mMainLayer.draw(mOffsetX - mRealPosX, mOffsetY - mRealPosY, mZoom);
		mTopLayer.draw(mOffsetX, mOffsetY, 1);
		
		// Render dynamic elements
		mDynamicLayer.clear();
		mDynamicLayer.draw(mOffsetX - mRealPosX, mOffsetY - mRealPosY, mZoom);
		mDynamicLayer.begin();
		onRender(mDynamicLayer, mGameTime, mScreenTime);
		mDynamicLayer.drawString(String.valueOf(mCycle), 0, 0);
		mDynamicLayer.end();

		// Render UI
		mUILayer.draw(mOffsetX, mOffsetY, 1);

		renderTransition();
	}

	private void renderTransition () {
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

	public void onRender(BaseScreenLayer dynamicLayer, int gameTime, int screenTime) {
	}
	
	protected abstract void onCreate ();
	protected abstract void onDraw(BaseScreenLayer mainLayer, BaseScreenLayer UILayer);
	public abstract void onTouch(int x, int y);
	public abstract void onLongTouch(int x, int y);
	public abstract void onMove(int startX, int startY, int offsetX, int offsetY);
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
	
	public void notifyChange () {
		mMainLayer.notifyChange();
		mUILayer.notifyChange();
	}

	public void tap (int x, int y) {
		for (View view: mViews) {
			if (view.isVisible() && view.isClickable() && view.contains(x + mOffsetX, y + mOffsetY)) {
				view.click();
				mGameTimeAtStart = mGameTime;
				return;
			}
		}
		
		onTouch(x, y);
		
		mMainLayer.notifyChange();
		mUILayer.notifyChange();
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

	public void onPinch () {
		mGame.goBack();
	}

	public void onZoom () {
	}

	public void onDown () {
	}

	public void onUp (int x, int y) {
	}

	public void onDown (int x, int y) {
	}

}
