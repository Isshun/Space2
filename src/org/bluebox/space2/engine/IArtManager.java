package org.bluebox.space2.engine;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface IArtManager {
	TextureRegion load (String name, int width, int height);
	TextureRegion[] split2 (String name, int width, int height);
	TextureRegion[][] split (String name, int width, int height);
}
