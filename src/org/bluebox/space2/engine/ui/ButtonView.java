package org.bluebox.space2.engine.ui;


import org.bluebox.space2.engine.Art;
import org.bluebox.space2.engine.screen.BaseScreenLayer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ButtonView extends RectangleView {

	private TextureRegion mIcon;

	public ButtonView (int x, int y, int width, int height, Color color) {
		super(x, y, width, height, null);
		mPadding = 4;
	}

	public ButtonView (String text, int y, int x) {
		super(x, y, text.length() * 6 + 8, 20, null);
		setText(text);
		mPadding = 4;
	}

	@Override
	public void draw (SpriteCache spriteBatch) {
		spriteBatch.add(Art.bt_bg, mPosX, mPosY);
		if (mIcon != null) {
			spriteBatch.add(mIcon, mPosX + 4, mPosY + 1);
		}
		super.draw(spriteBatch);
	}

	public void setIcon (TextureRegion icon) {
		mIcon = icon;
		if (mText != null && mIcon != null) {
			mText = "   " + mText;
		}
	}

	public void setText (String text) {
		mText = text.toLowerCase();
		if (mIcon != null) {
			mText = "   " + mText;
		}
	}

}
