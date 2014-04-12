
package org.bluebox.space2;

import org.bluebox.space2.engine.ArtManager;
import org.bluebox.space2.game.Constants;
import org.bluebox.space2.game.Game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class Space2Desktop {
	public static void main (String[] argv) {
		new LwjglApplication(new Game(new ArtManager()), "Space2", Constants.GAME_WIDTH * Constants.SCREEN_SCALE, Constants.GAME_HEIGHT * Constants.SCREEN_SCALE);
	}
}
