package org.bluebox.space2.engine.screen;

import org.bluebox.space2.engine.ui.View;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MockScreenLayer extends BaseScreenLayer {

	@Override
	public void draw (int offsetX, int offsetY, float zoom) {
	}

	@Override
	void draw (TextureRegion region, int x, int y, int width, int height) {
	}

	@Override
	void notifyChange () {
	}

	@Override
	public void dispose () {
	}

	@Override
	void draw (View view) {
	}

	@Override
	void draw (Sprite sprite) {
	}

	@Override
	public void clear () {
	}

	@Override
	public void begin () {
	}

	@Override
	public void end () {
	}

	@Override
	public boolean isChange () {
		return false;
	}

}
