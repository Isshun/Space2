package org.bluebox.space2.engine.screen;

import org.bluebox.space2.engine.ui.View;
import org.bluebox.space2.game.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;

public class CacheScreenLayer extends BaseScreenLayer {
	private SpriteCache 				mSpriteCache;
	private int 						mSpriteCacheId;
	private boolean 					mIsChangeNotified;

	public CacheScreenLayer() {
		mIsChangeNotified = true;
		mSpriteCache = new SpriteCache(5000, true);
	}
	
	public void draw (int offsetX, int offsetY, float zoom) {
		Gdx.gl.glEnable(GL30.GL_BLEND);
		Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

		//mFinalAnimationWidth
		
		
		int left = -mRealPosX + offsetX;
		int right = (int)(Constants.GAME_WIDTH * zoom - mRealPosX + offsetX);
		int bottom = (int)(Constants.GAME_HEIGHT * zoom - mRealPosY + offsetY);
		int top = -mRealPosY + offsetY;
		
		int width = right - left;
//		mProjectionWidth = 200;
//		int rest = (width - mProjectionWidth) / 2;
//		System.out.println("VALUE = " + (left) + " x " + right);
		
		Matrix4 projection = new Matrix4();
		projection.setToOrtho(
			left,
			right,
			bottom,
			top,
			-1,
			1);
		
		float scaleX = (float)(mProjectionWidth) / Constants.GAME_WIDTH;
		float scaleY = (float)(mProjectionHeight) / Constants.GAME_HEIGHT;
		
		int animOffsetX = (Constants.GAME_WIDTH / 2) - (mProjectionWidth / 2);
		int animOffsetY = (Constants.GAME_HEIGHT / 2) - (mProjectionHeight / 2);
		projection.translate(animOffsetX, animOffsetY, 0);
		projection.scl(scaleX, scaleY, 0);

		System.out.println("scale = " + animOffsetY + " x " + Constants.GAME_HEIGHT);
		
		mSpriteCache.setProjectionMatrix(projection);
		mSpriteCache.begin();  
		mSpriteCache.draw(mSpriteCacheId);  
		mSpriteCache.end();
		
		mIsChangeNotified = false;
	}

	@Override
	void draw (Sprite sprite) {
		mSpriteCache.add(sprite);
	}

	@Override
	void draw (View view) {
		view.draw(mSpriteCache);
	}

	@Override
	void draw (TextureRegion region, int x, int y, int width, int height) {
		mSpriteCache.add(region, x, y, width, height);
	}
	
	public void clear () {
		mSpriteCache.clear();
	}

	public void end () {
		mSpriteCacheId = mSpriteCache.endCache();
	}

	public boolean isChange () {
		 return mIsChangeNotified;
	}

	@Override
	public void dispose () {
		mSpriteCache.dispose();
	}

	@Override
	public void begin () {
		mSpriteCache.beginCache();
	}

	@Override
	void notifyChange () {
		mIsChangeNotified = true;
	}


}
