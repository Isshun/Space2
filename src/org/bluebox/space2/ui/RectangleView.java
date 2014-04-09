package org.bluebox.space2.ui;

import org.bluebox.space2.Art;
import org.bluebox.space2.screen.Screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	public void draw (SpriteBatch spriteBatch) {
		Pixmap pixmap = new Pixmap(mWidth, mHeight, Format.RGBA8888);
		pixmap.setColor(mColor);
		pixmap.fillRectangle(0, 0, mWidth, mHeight);
		Texture pixmaptex = new Texture(pixmap);
		Sprite line = new Sprite(pixmaptex);
		if (mAngle != 0) {
			line.setRotation(mAngle);
		}
		line.setPosition(mPosX, mPosY);
		line.draw(spriteBatch);
		pixmap.dispose();
		
		if (mText != null) {
			for (int i = 0; i < mText.length(); i++) {
				char ch = mText.charAt(i);
				for (int ys = 0; ys < Screen.CHARS.length; ys++) {
					int xs = Screen.CHARS[ys].indexOf(ch);
					if (xs >= 0) {
						spriteBatch.draw(Art.guys[xs][ys + 9], mPosX + i * 6, mPosY, 6,  6);
					}
				}
			}
		}
		
	}

	public void setText (String text) {
		mText = text;
	}

}
