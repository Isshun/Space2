package org.bluebox.space2.engine.ui;

import org.bluebox.space2.engine.Art;
import org.bluebox.space2.engine.screen.BaseScreen;
import org.bluebox.space2.engine.screen.BaseScreenLayer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RectangleView extends View {

	private Color 	mColor;
	private int 	mAngle;
	private String mText;

	public RectangleView (int x, int y, int width, int height, Color color) {
		super(x, y);
		
		mColor = color;
		mWidth = width;
		mHeight = height;
	}

	@Override
	public void draw (SpriteCache spriteBatch) {
		Pixmap pixmap = new Pixmap(mWidth, mHeight, Format.RGBA8888);
		pixmap.setColor(mColor);
		pixmap.fillRectangle(0, 0, mWidth, mHeight);
		Texture pixmaptex = new Texture(pixmap);
		Sprite line = new Sprite(pixmaptex);
		if (mAngle != 0) {
			line.setRotation(mAngle);
		}
		line.setPosition(mPosX, mPosY);
		spriteBatch.add(line);
		pixmap.dispose();
		
		if (mText != null) {
			for (int i = 0; i < mText.length(); i++) {
				char ch = mText.charAt(i);
				for (int ys = 0; ys < BaseScreenLayer.CHARS.length; ys++) {
					int xs = BaseScreenLayer.CHARS[ys].indexOf(ch);
					if (xs >= 0) {
						spriteBatch.add(Art.guys[xs][ys + 9], mPadding - 1 + mPosX + i * 6, mPadding + mPosY, 6,  6);
					}
				}
			}
		}

	}

	public void setText (String text) {
		mText = text.toLowerCase();
	}

	public void setHeight (int height) {
		mHeight = height;
	}

	public void setPosition (int x, int y) {
		mPosX = x;
		mPosY = y;
	}

	public void setWidth (int width) {
		mWidth = width;
	}

	public void setPadding (int padding) {
		mPadding = padding;
	}

}
