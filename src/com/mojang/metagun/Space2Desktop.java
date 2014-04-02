
package com.mojang.metagun;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class Space2Desktop {
	public static void main (String[] argv) {
		new LwjglApplication(new Space2(), "Space2", 320 * Constants.SCREEN_SCALE, 240 * Constants.SCREEN_SCALE);
	}
}
