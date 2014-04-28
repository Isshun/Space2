package org.bluebox.space2.engine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class MockArtManager implements IArtManager {

	@Override
	public TextureRegion load(String name, int width, int height) {
		return new TextureRegion();
	}

	@Override
	public TextureRegion[][] split (String name, int width, int height) {
		return null;
	}

	@Override
	public TextureRegion[] split2 (String name, int width, int height) {
		// TODO Auto-generated method stub
		return null;
	}

}
