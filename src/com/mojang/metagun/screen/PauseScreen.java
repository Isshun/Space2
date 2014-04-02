
package com.mojang.metagun.screen;

import com.mojang.metagun.Art;
import com.mojang.metagun.Input;

public class PauseScreen extends Screen {
	private final Screen parent;
	private int selected = 0;

	private final String[] options = {"BACK TO GAME", "AUTOMORTIS", "QUIT TO TITLE"};

	public PauseScreen (Screen parent) {
		this.parent = parent;
	}

	@Override
	public void render () {
		parent.render();

		mSpriteBatch.begin();
		int xs = 0;
		int ys = options.length;
		for (int y = 0; y < options.length; y++) {
			int s = options[y].length();
			if (s > xs) xs = s;
		}
		xs += 1;
		int xp = 40;
		int yp = 40;
		for (int x = 0 - 1; x < xs + 1; x++) {
			for (int y = 0 - 1; y < ys + 1; y++) {
				int xf = 1;
				int yf = 12;
				if (x < 0) xf--;
				if (y < 0) yf--;
				if (x >= xs) xf++;
				if (y >= ys) yf++;
				draw(Art.guys[xf][yf], xp + x * 6, yp + y * 6);
			}
		}
		for (int y = 0; y < options.length; y++) {
			if (y == selected) {
				drawString("+", xp, yp + y * 6);
			}
			drawString(options[y], xp + 6, yp + y * 6);
		}
		mSpriteBatch.end();
	}

	@Override
	public void onTouch (int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMove (int offsetX, int offsetY) {
		// TODO Auto-generated method stub
		
	}
}
