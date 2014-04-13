
package org.bluebox.space2.engine;

import org.bluebox.space2.game.model.PlanetClassModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Art {
	public static final int PLANET_RES_12 = 4;
	public static final int PLANET_RES_16 = 0;
	public static final int PLANET_RES_24 = 5;
	public static final int PLANET_RES_32 = 1;
	public static final int PLANET_RES_42 = 2;
	public static final int PLANET_RES_56 = 6;
	public static final int PLANET_RES_64 = 7;
	public static final int PLANET_RES_128 = 3;
	
	public static TextureRegion[][]	guys;
	public static TextureRegion[][] 	bigText;
	public static TextureRegion[][] 	planets;
	public static TextureRegion[] 	system;
	public static TextureRegion[] 	flags;
	public static TextureRegion[] 	sun;
	public static TextureRegion[] 	system_selected;
	public static TextureRegion 		bg;
	public static TextureRegion 		ic_money_12;
	public static TextureRegion 		res_culture;
	public static TextureRegion 		ic_construction_12;
	public static TextureRegion 		ic_science;
	public static TextureRegion 		res_food;
	public static TextureRegion 		bt_planets;
	public static TextureRegion 		bt_relations;
	public static TextureRegion 		map;
	public static TextureRegion 		ship;
	public static TextureRegion 		ic_people;
	public static TextureRegion 		ic_satisfaction;
	public static TextureRegion 		ic_construction_16;
	public static TextureRegion 		ic_move;
	public static TextureRegion 		bt_debug;
	public static TextureRegion 		ship_big;
	public static TextureRegion 		ship_32;
	public static TextureRegion 		ship_64;
	public static TextureRegion 		ship_128;
	public static TextureRegion 		bg_1;
	public static TextureRegion 		shield;
	public static TextureRegion 		ic_attack_32;
	public static TextureRegion 		ic_info_32;
	public static TextureRegion 		dock;
	public static TextureRegion 		bt_space_map;
	public static TextureRegion 		bt_move;
	public static TextureRegion 		system_selected_origin;
	public static TextureRegion 		spacedock;
	public static TextureRegion[] 	systems;
	private static IArtManager 		mArt;
	public static TextureRegion 		bt_cancel;
	public static TextureRegion 		bt_bg;

	public static void load () {
		ship_big = mArt.load("res/ship7.png", 128, 87);
		ship = mArt.load("res/ship.png", 16, 16);
		ship_32 = mArt.load("res/ship_1_32.png", 32, 32);
		ship_64 = mArt.load("res/ship_1_64.png", 64, 64);
		ship_128 = mArt.load("res/ship_1_128.png", 128, 128);
		bt_cancel = mArt.load("res/bt_cancel.png", 60, 13);
		bt_move = mArt.load("res/bt_move.png", 60, 13);
		bt_bg = mArt.load("res/bt_bg.png", 60, 13);
		ic_move = mArt.load("res/ic_move.png", 11, 11);
		
		spacedock = mArt.load("res/spacedock.png", 108, 80);
		shield = mArt.load("res/shield.png", 32, 32);
		dock = mArt.load("res/dock_16.png", 16, 16);
		ic_attack_32 = mArt.load("res/ic_attack_32.png", 32, 32);
		ic_info_32 = mArt.load("res/ic_info_32.png", 32, 32);

		bg = mArt.load("res/simple_space.png", 960, 540);
		bg_1 = mArt.load("res/bg_1.png", 377, 264);
		map = mArt.load("res/map.png", 64, 56);
		system = new TextureRegion[4];
		system[0] = mArt.load("res/system_0.png", 22, 22);
		system[1] = mArt.load("res/system_1.png", 22, 22);
		system[2] = mArt.load("res/system_2.png", 22, 22);
		system[3] = mArt.load("res/system_3.png", 22, 22);
		
		systems = new TextureRegion[4];
		systems[0] = mArt.load("res/system_0_64.png", 64, 64);
		systems[1] = mArt.load("res/system_1_64.png", 64, 64);
		systems[2] = mArt.load("res/system_2_64.png", 64, 64);
		systems[3] = mArt.load("res/system_3_64.png", 64, 64);
		
		sun = new TextureRegion[4];
		sun[0] = mArt.load("res/sun_0_256.png", 256, 256);
		sun[1] = mArt.load("res/sun_1_256.png", 256, 256);
		sun[2] = mArt.load("res/sun_2_256.png", 256, 256);
		sun[3] = mArt.load("res/sun_3_256.png", 256, 256);
		system_selected = new TextureRegion[4];
		system_selected[0] = mArt.load("res/system_3_selected.png", 22, 22);
		system_selected[1] = mArt.load("res/system_1_selected.png", 22, 22);
		system_selected[2] = mArt.load("res/system_3_selected.png", 22, 22);
		system_selected[3] = mArt.load("res/system_3_selected.png", 22, 22);
		system_selected_origin = mArt.load("res/system_selected_origin.png", 22, 22);
		bt_planets = mArt.load("res/flag_planets.png", 32, 42);
		bt_relations = mArt.load("res/flag_relations.png", 32, 42);
		bt_debug = mArt.load("res/bt_debug.png", 32, 42);
		bt_space_map = mArt.load("res/map.png", 24, 16);
		ic_money_12 = mArt.load("res/res_money.png", 12, 12);
		res_food = mArt.load("res/res_food.png", 12, 12);
		res_culture = mArt.load("res/res_culture.png", 12, 12);
		ic_construction_12 = mArt.load("res/res_construction_12.png", 16, 16);
		ic_construction_16 = mArt.load("res/res_construction_16.png", 12, 12);
		ic_science = mArt.load("res/res_science.png", 12, 12);
		ic_people = mArt.load("res/ic_people.png", 12, 12);
		ic_satisfaction = mArt.load("res/ic_satisfaction.png", 12, 12);
		guys = mArt.split("res/guys.png", 6, 6);
		bigText = mArt.split("res/guys.png", 12, 12);
		flags = new TextureRegion[10];
		flags[0] = mArt.load("res/flag_0_16.png", 24, 16);
		flags[1] = mArt.load("res/flag_1_16.png", 24, 16);
		flags[2] = mArt.load("res/flag_2_16.png", 24, 16);
		flags[3] = mArt.load("res/flag_3_16.png", 24, 16);
		flags[4] = mArt.load("res/flag_4_16.png", 24, 16);
		flags[5] = mArt.load("res/flag_5_16.png", 24, 16);
		flags[6] = mArt.load("res/flag_0_16.png", 24, 16);
		flags[7] = mArt.load("res/flag_0_16.png", 24, 16);
		flags[8] = mArt.load("res/flag_0_16.png", 24, 16);
		flags[9] = mArt.load("res/flag_0_16.png", 24, 16);
		
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
		textures[PLANET_RES_12] = mArt.load("res/" + name + "_12.png", 12, 12);
		textures[PLANET_RES_16] = mArt.load("res/" + name + "_16.png", 16, 16);
		textures[PLANET_RES_24] = mArt.load("res/" + name + "_24.png", 24, 24);
		textures[PLANET_RES_32] = mArt.load("res/" + name + "_32.png", 32, 32);
		textures[PLANET_RES_42] = mArt.load("res/" + name + "_42.png", 42, 42);
		textures[PLANET_RES_56] = mArt.load("res/" + name + "_56.png", 56, 56);
		textures[PLANET_RES_64] = mArt.load("res/" + name + "_64.png", 64, 64);
		textures[PLANET_RES_128] = mArt.load("res/" + name + "_128.png", 128, 128);
	}

	public static void init (IArtManager art) {
		mArt = art;
		load();
	}
}
