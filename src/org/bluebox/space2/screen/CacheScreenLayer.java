package org.bluebox.space2.screen;

import org.bluebox.space2.Constants;
import org.bluebox.space2.ui.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;

public class CacheScreenLayer extends ScreenLayerBase {
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

		Matrix4 projection = new Matrix4();
		projection.setToOrtho(-mRealPosX + offsetX, Constants.GAME_WIDTH * zoom - mRealPosX + offsetX, Constants.GAME_HEIGHT * zoom - mRealPosY + offsetY, -mRealPosY + offsetY, -1, 1);

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
