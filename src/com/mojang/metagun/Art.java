
package com.mojang.metagun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Art {
	public static TextureRegion[][] guys;
	public static TextureRegion[][] player1;
	public static TextureRegion[][] player2;
	public static TextureRegion[][] walls;
	public static TextureRegion[][] gremlins;
	public static TextureRegion bg;
	public static Pixmap level;
	public static TextureRegion titleScreen;
	public static TextureRegion shot;
	public static TextureRegion[][] buttons;

	public static TextureRegion winScreen1;
	public static TextureRegion winScreen2;
	public static TextureRegion system;
	public static TextureRegion sun;
	public static TextureRegion planet;
	public static TextureRegion planet_32;
	public static TextureRegion planet_128;
	public static TextureRegion res_money;
	public static TextureRegion res_culture;
	public static TextureRegion res_construction;
	public static TextureRegion res_science;
	public static TextureRegion res_food;
	public static TextureRegion[][] bigText;

	public static void load () {
		bg = load("res/background.png", 320, 240);
		system = load("res/system.png", 32, 32);
		res_money = load("res/res_money.png", 8, 8);
		res_food = load("res/res_food.png", 8, 8);
		res_culture = load("res/res_culture.png", 8, 8);
		res_construction = load("res/res_construction.png", 8, 8);
		res_science = load("res/res_science.png", 8, 8);
		planet = load("res/planet_bad_42.png", 42, 42);
		planet_32 = load("res/planet_bad_32.png", 32, 32);
		planet_128 = load("res/planet_bad_128.png", 128, 128);
		sun = load("res/sun.png", 128, 128);
		level = new Pixmap(Gdx.files.internal("res/levels.png"));
		titleScreen = load("res/titlescreen.png", 320, 740);
		guys = split("res/guys.png", 6, 6);
		bigText = split("res/guys.png", 12, 12);
		player1 = split("res/player.png", 16, 32);
		player2 = split("res/player.png", 16, 32, true);
		walls = split("res/walls.png", 10, 10);
		gremlins = split("res/gremlins.png", 30, 30);
		buttons = split("res/buttons.png", 32, 32);
		shot = new TextureRegion(guys[0][0].getTexture(), 3, 27, 2, 2);
		winScreen1 = load("res/winscreen1.png", 320, 240);
		winScreen2 = load("res/winscreen2.png", 320, 240);
	}

	private static TextureRegion[][] split (String name, int width, int height) {
		return split(name, width, height, false);
	}

	private static TextureRegion[][] split (String name, int width, int height, boolean flipX) {
		Texture texture = new Texture(Gdx.files.internal(name));
		int xSlices = texture.getWidth() / width;
		int ySlices = texture.getHeight() / height;
		TextureRegion[][] res = new TextureRegion[xSlices][ySlices];
		for (int x = 0; x < xSlices; x++) {
			for (int y = 0; y < ySlices; y++) {
				res[x][y] = new TextureRegion(texture, x * width, y * height, width, height);
				res[x][y].flip(flipX, true);
			}
		}
		return res;
	}

	public static TextureRegion load (String name, int width, int height) {
		Texture texture = new Texture(Gdx.files.internal(name));
		TextureRegion region = new TextureRegion(texture, 0, 0, width, height);
		region.flip(false, true);
		return region;
	}
}
