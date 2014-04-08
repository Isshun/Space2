package org.bluebox.space2.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	public void draw (SpriteBatch spriteBatch) {
		int width = mWidth;
		if (width < 0) width = -width;
		spriteBatch.draw(mRegion, mPosX, mPosY, width, mRegion.getRegionHeight());
	}

}