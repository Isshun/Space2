package org.bluebox.space2.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RectangleView extends View {

	private Color 	mColor;
	private int 	mAngle;

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
	}

}
