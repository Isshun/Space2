package org.bluebox.space2.engine.ui;

import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ImageView extends View {

	private TextureRegion mRegion;

	public ImageView (TextureRegion region, int x, int y) {
		super(x, y);
		
		mWidth = region.getRegionWidth();
		mHeight = region.getRegionHeight();
		mRegion = region;
	}

	@Override
	public void draw (SpriteCache spriteBatch) {
		int width = mWidth;
		if (width < 0) width = -width;
		spriteBatch.add(mRegion, mPosX, mPosY, width, mRegion.getRegionHeight());
	}

	public void setClickable (boolean value) {
		mIsClickable = value;
	}

}