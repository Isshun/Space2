
package com.mojang.metagun;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class MetagunDesktop {
	public static void main (String[] argv) {
		new LwjglApplication(new Space2(), "Metagun", 320 * Space2.SCREEN_SCALE, 240 * Space2.SCREEN_SCALE);
	}
}
