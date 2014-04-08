
package org.bluebox.space2;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class Space2Desktop {
	public static void main (String[] argv) {
		new LwjglApplication(new Game(), "Space2", Constants.GAME_WIDTH * Constants.SCREEN_SCALE, Constants.GAME_HEIGHT * Constants.SCREEN_SCALE);
	}
}
