
package com.mojang.metagun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mojang.metagun.model.PlanetClassModel;

public class Art {
	public static final int PLANET_RES_12 = 4;
	public static final int PLANET_RES_16 = 0;
	public static final int PLANET_RES_24 = 5;
	public static final int PLANET_RES_32 = 1;
	public static final int PLANET_RES_42 = 2;
	public static final int PLANET_RES_56 = 6;
	public static final int PLANET_RES_64 = 7;
	public static final int PLANET_RES_128 = 3;
	
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
	public static TextureRegion[] system;
	public static TextureRegion sun[];
	public static TextureRegion ic_money_12;
	public static TextureRegion res_culture;
	public static TextureRegion ic_construction_12;
	public static TextureRegion res_science;
	public static TextureRegion res_food;
	public static TextureRegion[][] bigText;
	public static TextureRegion[][] planets;
	public static TextureRegion[] flags;
	public static TextureRegion bt_planets;
	public static TextureRegion bt_relations;
	public static TextureRegion map;
	public static TextureRegion ship;
	public static TextureRegion ic_people;
	public static TextureRegion ic_satisfaction;
	public static TextureRegion ic_construction_16;
	public static TextureRegion bt_debug;
	public static TextureRegion ship_big;
	public static TextureRegion ship_32;
	public static TextureRegion ship_64;
	public static TextureRegion ship_128;
	public static TextureRegion bg_1;
	public static TextureRegion shield;
	public static TextureRegion ic_attack_32;
	public static TextureRegion ic_info_32;

	public static void load () {
		ship_big = load("res/ship7.png", 128, 87);
		ship = load("res/ship.png", 16, 16);
		ship_32 = load("res/ship_1_32.png", 32, 32);
		ship_64 = load("res/ship_1_64.png", 64, 64);
		ship_128 = load("res/ship_1_128.png", 128, 128);
		
		shield = load("res/shield.png", 32, 32);
		ic_attack_32 = load("res/ic_attack_32.png", 32, 32);
		ic_info_32 = load("res/ic_info_32.png", 32, 32);

		bg = load("res/simple_space.png", 960, 540);
		bg_1 = load("res/bg_1.png", 377, 264);
		map = load("res/map.png", 64, 56);
		system = new TextureRegion[4];
		system[0] = load("res/system_0.png", 22, 22);
		system[1] = load("res/system_1.png", 22, 22);
		system[2] = load("res/system_2.png", 22, 22);
		system[3] = load("res/system_3.png", 22, 22);
		sun = new TextureRegion[4];
		sun[0] = load("res/sun_0_256.png", 256, 256);
		sun[1] = load("res/sun_1_256.png", 256, 256);
		sun[2] = load("res/sun_2_256.png", 256, 256);
		sun[3] = load("res/sun_3_256.png", 256, 256);
		bt_planets = load("res/flag_planets.png", 32, 42);
		bt_relations = load("res/flag_relations.png", 32, 42);
		bt_debug = load("res/bt_debug.png", 32, 42);
		ic_money_12 = load("res/res_money.png", 12, 12);
		res_food = load("res/res_food.png", 12, 12);
		res_culture = load("res/res_culture.png", 12, 12);
		ic_construction_12 = load("res/res_construction_12.png", 16, 16);
		ic_construction_16 = load("res/res_construction_16.png", 12, 12);
		res_science = load("res/res_science.png", 12, 12);
		ic_people = load("res/ic_people.png", 12, 12);
		ic_satisfaction = load("res/ic_satisfaction.png", 12, 12);
		level = new Pixmap(Gdx.files.internal("res/levels.png"));
		titleScreen = load("res/titlescreen.png", 320, 740);
		guys = split("res/guys.png", 6, 6);
		bigText = split("res/guys.png", 12, 12);
		player1 = split("res/player.png", 16, 32);
		player2 = split("res/player.png", 16, 32, true);
		walls = split("res/walls.png", 10, 10);
		flags = new TextureRegion[10];
		flags[0] = load("res/flag_0_16.png", 24, 16);
		flags[1] = load("res/flag_1_16.png", 24, 16);
		flags[2] = load("res/flag_2_16.png", 24, 16);
		flags[3] = load("res/flag_3_16.png", 24, 16);
		flags[4] = load("res/flag_4_16.png", 24, 16);
		flags[5] = load("res/flag_5_16.png", 24, 16);
		flags[6] = load("res/flag_0_16.png", 24, 16);
		flags[7] = load("res/flag_0_16.png", 24, 16);
		flags[8] = load("res/flag_0_16.png", 24, 16);
		flags[9] = load("res/flag_0_16.png", 24, 16);
		
		gremlins = split("res/gremlins.png", 30, 30);
		buttons = split("res/buttons.png", 32, 32);
		shot = new TextureRegion(guys[0][0].getTexture(), 3, 27, 2, 2);
		winScreen1 = load("res/winscreen1.png", 320, 240);
		winScreen2 = load("res/winscreen2.png", 320, 240);
		
		planets = new TextureRegion[15][8];
		for (int i = 0; i < 15; i++) {
			switch (i) {
			case PlanetClassModel.CLASS_K_OCEAN: loadPlanet(planets[i], "planet_k1"); break;
			case PlanetClassModel.CLASS_M: loadPlanet(planets[i], "planet_m"); break;
			case PlanetClassModel.CLASS_L: loadPlanet(planets[i], "planet_l"); break;
			case PlanetClassModel.CLASS_T: loadPlanet(planets[i], "planet_t"); break;
			case PlanetClassModel.CLASS_J: loadPlanet(planets[i], "planet_t"); break;
			case PlanetClassModel.CLASS_L_DESERT: loadPlanet(planets[i], "planet_l"); break;
			case PlanetClassModel.CLASS_L_JUNGLE: loadPlanet(planets[i], "planet_l"); break;
			default: loadPlanet(planets[i], "planet_y"); break;
			}
		}
	}

	private static void loadPlanet (TextureRegion[] textures, String name) {
		textures[PLANET_RES_12] = load("res/" + name + "_12.png", 12, 12);
		textures[PLANET_RES_16] = load("res/" + name + "_16.png", 16, 16);
		textures[PLANET_RES_24] = load("res/" + name + "_24.png", 24, 24);
		textures[PLANET_RES_32] = load("res/" + name + "_32.png", 32, 32);
		textures[PLANET_RES_42] = load("res/" + name + "_42.png", 42, 42);
		textures[PLANET_RES_56] = load("res/" + name + "_56.png", 56, 56);
		textures[PLANET_RES_64] = load("res/" + name + "_64.png", 64, 64);
		textures[PLANET_RES_128] = load("res/" + name + "_128.png", 128, 128);
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
