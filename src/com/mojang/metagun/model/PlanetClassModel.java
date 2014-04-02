package com.mojang.metagun.model;

public class PlanetClassModel {
	
	public static final int CLASS_D = 0;
	public static final int CLASS_F = 1;
	public static final int CLASS_H = 2;
	public static final int CLASS_J = 3;
	public static final int CLASS_K = 4;
	public static final int CLASS_K_OCEAN = 5;
	public static final int CLASS_K_LOW_OXYGEN = 6;
	public static final int CLASS_L = 7;
	public static final int CLASS_L_JUNGLE = 8;
	public static final int CLASS_L_DESERT = 9;
	public static final int CLASS_M = 10;
	public static final int CLASS_N = 11;
	public static final int CLASS_P = 12;
	public static final int CLASS_T = 13;
	public static final int CLASS_Y = 14;
	
	public int id;
	public int rand;
	public String name;
	public String shortName;
	
	public static PlanetClassModel[] sClass = {
		new PlanetClassModel(CLASS_D,					10,	"Planetoid (D)",		"D"),
		new PlanetClassModel(CLASS_F,					5,		"Inert (F)",			"F"),
		new PlanetClassModel(CLASS_H,					2,		"Uninhabitable (H)",	"H"),
		new PlanetClassModel(CLASS_J,					2,		"Gas giant (J)",		"J"),
		new PlanetClassModel(CLASS_K,					10,	"Adaptable (K)",		"K"),
		new PlanetClassModel(CLASS_K_OCEAN,			10,	"Adaptable (K)",		"K"),
		new PlanetClassModel(CLASS_K_LOW_OXYGEN,	10,	"Adaptable (K)",		"K"),
		new PlanetClassModel(CLASS_L,					8,		"Hostile (L)",			"L"),
		new PlanetClassModel(CLASS_L_DESERT,		8,		"Hostile (L)",			"L"),
		new PlanetClassModel(CLASS_L_JUNGLE,		8,		"Hostile (L)",			"L"),
		new PlanetClassModel(CLASS_M,					10,	"Suitable (M)",		"M"),
		new PlanetClassModel(CLASS_N,					2,		"Sulfurique (N)",		"N"),
		new PlanetClassModel(CLASS_P,					2,		"Glaciated (P)",		"P"),
		new PlanetClassModel(CLASS_T,					2,		"Gas Giant (T)",		"T"),
		new PlanetClassModel(CLASS_Y,					1,		"Deamon (Y)",			"Y")
	};

	public PlanetClassModel (int classification, int rand, String name, String shortName) {
		this.id = classification;
		this.rand = rand;
		this.name = name;
		this.shortName = shortName;
	}

	public static String getText(int classId) {
		return sClass[classId].name;
	}

	public static String getShortText(int classId) {
		return sClass[classId].shortName;
	}
	
}
