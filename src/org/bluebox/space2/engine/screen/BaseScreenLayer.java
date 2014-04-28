package org.bluebox.space2.engine.screen;

import org.bluebox.space2.engine.Art;
import org.bluebox.space2.engine.ui.View;
import org.bluebox.space2.game.Constants;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;

public abstract class BaseScreenLayer {
	public static final String[]	CHARS = {"abcdefghijklmnopqrstuvwxyz0123456789", ".,!?:;\"'+-=/\\<%   () $^&*@~"};

	public static final int 		CACHE = 0;
	public static final int 		DYNAMIC = 1;
	
	protected int 						mRealPosX;
	protected int 						mRealPosY;
	private Color						mColorBad;
	private Color						mColorGood;
	private StringConfig				mStringConfig;
	protected int						mProjectionWidth;

	protected int mProjectionHeight;

	public class StringConfig {
		public static final int SIZE_REGULAR = 0;
		public static final int SIZE_BIG = 1;

		public int 		maxWidth;
		public boolean isMultiline;
		public Color 	color;
		public int 		size;
		public boolean isNumbersColored;
	}
	
	public static boolean isDebug;

	public static BaseScreenLayer create (int mode) {
		if (isDebug) {
			return new MockScreenLayer();
		}
		if (mode == 0) {
			return new CacheScreenLayer();
		}
		if (mode == 1) {
			return new DynamicScreenLayer();
		}
		return null;
	}

	public BaseScreenLayer() {
		mColorBad = new Color(220f/255, 40f/255, 50f/255, 1);
		mColorGood = new Color(0.5f, 1, 0.5f, 1);
		mStringConfig = new StringConfig();
	}
	
	public abstract void draw (int offsetX, int offsetY, float zoom);
	
	public void drawRectangle(int x, int y, int width, int height, int color) {
		Pixmap pixmap = new Pixmap(32, 32, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, 32, 32);
		Texture pixmaptex = new Texture(pixmap);
		TextureRegion region = new TextureRegion(pixmaptex);
		draw(region, x, y, width, height);
		pixmap.dispose();
	}
	
	public void drawRectangle(int x, int y, int width, int height, Color color) {
		drawRectangle(null, x, y, width, height, color);
	}

	public void drawRectangle(SpriteBatch bacth, int x, int y, int width, int height, Color color) {
		Pixmap pixmap = new Pixmap(32, 32, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, 32, 32);
		Texture pixmaptex = new Texture(pixmap);
		TextureRegion region = new TextureRegion(pixmaptex);
		if (bacth != null) {
			bacth.draw(region, x, y, width, height);
		} else {
			draw(region, x, y, width, height);
		}
		pixmap.dispose();
	}

	abstract void draw (TextureRegion region, int x, int y, int width, int height);

	public void drawRectangle (int x, int y, int width, int height, Color color, int angle) {
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, width, height);
		Texture pixmaptex = new Texture(pixmap);
		Sprite line = new Sprite(pixmaptex);
		line.setRotation(angle);
		line.setPosition(x, y);
		draw(line);
//		if (bacth != null) {
//			line.draw(bacth);
//		} else {
//		}
		pixmap.dispose();
	}
	
	public void draw (SpriteBatch batch, TextureRegion region, int x, int y) {
		draw(batch, region, x, y, null);
	}
	
	public void draw (SpriteBatch batch, TextureRegion region, int x, int y, Color color) {
		Sprite sprite = new Sprite(region);
		sprite.setPosition(x, y);
		if (color != null) {
			sprite.setColor(color);
		}
		draw(sprite);
	}

	protected void draw (TextureRegion region, int x, int y, int angle) {
		Sprite sprite = new Sprite(region);
		sprite.setRotation(angle);
		sprite.setPosition(x, y);
		//sprite.draw(mSpriteBatch);
		draw(sprite);
	}

	public void draw (Pixmap pixmap, int x, int y) {
		Texture pixmaptex = new Texture(pixmap);
		TextureRegion region = new TextureRegion(pixmaptex);
		draw(region, x, y);
	}

	public void draw (TextureRegion region, int x, int y) {
		int width = region.getRegionWidth();
		if (width < 0) width = -width;
		draw(region, x, y, width, region.getRegionHeight());
	}

	public void setStringMaxWidth (int width) {
		mStringConfig.maxWidth = width;
	}

	public void setStringMultiline (boolean isMultiline) {
		mStringConfig.isMultiline = isMultiline;
	}

	public void setStringColorNumbers (boolean isColored) {
		mStringConfig.isNumbersColored = isColored;
	}

	public void setStringColor (Color color) {
		mStringConfig.color = color;
	}

	public void setStringSize(int size) {
		mStringConfig.size = size;
	}

	public int drawString (SpriteBatch batch, String string, int x, int y) {
		
		boolean isBig = mStringConfig.size == StringConfig.SIZE_BIG;
		int lineHeight = isBig ? 18 : 10;
		int charWidth = isBig ? 12 : 6;
		int line = 0;
		
		// Multilines
		if (mStringConfig.isMultiline) {
			String rest = string;
			int lineLength = mStringConfig.maxWidth / charWidth;
			for (; rest.length() > lineLength; line++) {
				int index = rest.lastIndexOf(' ', lineLength);
				if (index == -1) {
					index = lineLength;
				}
				String sub = rest.substring(0, index);
				rest = rest.substring(index + 1);
				System.out.println("sub: " + index + ", " + sub);
				if (isBig) {
					drawBigString(batch, sub, x, y + line * lineHeight);
				} else {
					drawString(null, sub, x, y + line * lineHeight, Integer.MAX_VALUE);
				}
			}
			string = rest;
		}

		if (isBig) {
			drawBigString(batch, string, x, y + line * lineHeight);
		} else {
			drawString(batch, string, x, y + line * lineHeight, Integer.MAX_VALUE);
		}
		
		resetStringConfig();
		return line + 1;
	}
	
	private void resetStringConfig () {
		mStringConfig.color = null;
		mStringConfig.isMultiline = false;
		mStringConfig.isNumbersColored = false;
		mStringConfig.maxWidth = Integer.MAX_VALUE;
		mStringConfig.size = StringConfig.SIZE_REGULAR;
	}

	public int drawString (String string, int x, int y) {
		return drawString(null, string, x, y);
	}
	
	private void drawString (SpriteBatch batch, String string, int x, int y, int truncate) {
		string = string.toLowerCase();
		for (int i = 0; i < Math.min(string.length(), truncate); i++) {
			char ch = string.charAt(i);
			if (ch == 'é' || ch == 'è') {
				ch = 'e';
			}
			if (ch == 'à') {
				ch = 'a';
			}
			if (mStringConfig.isNumbersColored) {
				if (ch == ' ') {
					mStringConfig.color = null;
				}
				if (ch == '+') {
					mStringConfig.color = mColorGood;
				}
				if (ch == '-') {
					mStringConfig.color = mColorBad;
				}
			}
			for (int ys = 0; ys < CHARS.length; ys++) {
				int xs = CHARS[ys].indexOf(ch);
				if (xs >= 0) {
					draw(batch, Art.guys[xs][ys + 9], x + i * 6, y, mStringConfig.color);
				}
			}
		}

		if (string.length() >= truncate) {
			draw(batch, Art.guys[20][10], x + truncate * 6, y, mStringConfig.color);
		}
	}

	private void drawBigString (SpriteBatch batch, String string, int x, int y) {
		string = string.toLowerCase();
		for (int i = 0; i < string.length(); i++) {
			char ch = string.charAt(i);
			if (ch == 'é' || ch == 'è') {
				ch = 'e';
			}
			if (ch == 'à') {
				ch = 'a';
			}
			for (int ys = 0; ys < CHARS.length; ys++) {
				int xs = CHARS[ys].indexOf(ch);
				if (xs >= 0) {
					draw(batch, Art.bigText[xs][ys + 7], x + i * 12, y, mStringConfig.color);
				}
			}
		}
	}

	public void setCamera (int posX, int posY) {
		mRealPosX = posX;
		mRealPosY = posY;
	}

	abstract void notifyChange ();
	abstract public void dispose ();
	abstract void draw (View view);
	abstract void draw(Sprite sprite);
	abstract public void clear ();
	abstract public void begin();
	abstract public void end();
	abstract public boolean isChange ();

	public void setProjectionSize (int projectionWidth, int projectionHeight) {
		mProjectionWidth = projectionWidth;
		mProjectionHeight = projectionHeight;
	}

}
