package com.mojang.metagun.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mojang.metagun.Art;
import com.mojang.metagun.Constants;
import com.mojang.metagun.screen.Screen;

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
		mText = text.toUpperCase();
	}

	@Override
	public void draw (SpriteBatch spriteBatch) {
		for (int i = 0; i < mText.length(); i++) {
			char ch = mText.charAt(i);
			if (ch == '(') {
//				draw(Art.guys[18][10], mPosX + i * 6, mPosY, mColor);
			}
			if (ch == ')') {
//				draw(Art.guys[19][10], mPosX + i * 6, mPosY, mColor);
			}
			for (int ys = 0; ys < Screen.CHARS.length; ys++) {
				int xs = Screen.CHARS[ys].indexOf(ch);
				if (xs >= 0) {
					if (mColor != null) {
						Pixmap pixmap = new Pixmap(32, 32, Format.RGBA8888);
						pixmap.setColor(mColor);
						pixmap.fillRectangle(0, 0, 32, 32);
						Texture pixmaptex = new Texture(pixmap);
						TextureRegion region = new TextureRegion(pixmaptex);
						spriteBatch.draw(region, mPosX, mPosY, 3, 4);
						pixmap.dispose();
					} else {
						spriteBatch.draw(Art.guys[xs][ys + 9], mPosX + i * 6, mPosY, 6,  6);
					}
//					draw(, mPosX + i * 6, mPosY, mColor);
				}
			}
		}
	}

}
