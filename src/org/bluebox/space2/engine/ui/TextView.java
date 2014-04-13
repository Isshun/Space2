package org.bluebox.space2.engine.ui;

import org.bluebox.space2.engine.Art;
import org.bluebox.space2.engine.screen.BaseScreenLayer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextView extends View {

	private String mText;
	private Color mColor;

	public TextView (String text, int x, int y, Color color) {
		super(x, y);

		mWidth = text.length() * 6;
		mHeight = 6;
		mText = text.toUpperCase();
		mColor = color;
		mPadding = 2;
	}

	public TextView (String text, int x, int y) {
		super(x, y);

		mWidth = text.length() * 6;
		mHeight = 6;
		mText = text.toLowerCase();
	}

	@Override
	public void draw (SpriteCache spriteBatch) {
		int j = 0;
		int i = 0;
		for (int l = 0; l < mText.length(); l++) {
			char ch = mText.charAt(l);
			if (ch == '\n') {
				j++;
				i = -1;
			}
			for (int ys = 0; ys < BaseScreenLayer.CHARS.length; ys++) {
				int xs = BaseScreenLayer.CHARS[ys].indexOf(ch);
				if (xs >= 0) {
					if (mColor != null) {
						Pixmap pixmap = new Pixmap(32, 32, Format.RGBA8888);
						pixmap.setColor(mColor);
						pixmap.fillRectangle(0, 0, 32, 32);
						Texture pixmaptex = new Texture(pixmap);
						TextureRegion region = new TextureRegion(pixmaptex);
						spriteBatch.add(region, mPosX, mPosY, 3, 4);
						pixmap.dispose();
					} else {
						spriteBatch.add(Art.guys[xs][ys + 9], mPosX + mPadding + i * 6, mPosY + j * 8 + mPadding, 6,  6);
					}
//					draw(, mPosX + i * 6, mPosY, mColor);
				}
			}
			i++;
		}
	}

	public void setText (String text) {
		mText = text.toLowerCase();
	}

}
