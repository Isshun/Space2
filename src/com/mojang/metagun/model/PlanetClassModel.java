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
	public static final int CLASS_Z = 15;
	
	public int id;
	public int rand;
	public String name;
	public String shortName;
	private String subName;
	
	public static PlanetClassModel[] sClass = {
		new PlanetClassModel(CLASS_D,					10,	"Planetoid",		null, 		"D",	"P"),
		new PlanetClassModel(CLASS_F,					5,		"Inert",				null, 		"F", 	"I"),
		new PlanetClassModel(CLASS_H,					2,		"Uninhabitable",	null, 		"H", 	"U"),
		new PlanetClassModel(CLASS_J,					2,		"Gas giant",		null, 		"J", 	"G"),
		new PlanetClassModel(CLASS_K,					10,	"Adaptable",		null, 		"K", 	"A"),
		new PlanetClassModel(CLASS_K_OCEAN,			10,	"Adaptable",		"Ocean", 	"K", 	"A"),
		new PlanetClassModel(CLASS_K_LOW_OXYGEN,	10,	"Adaptable",		"Low O2",	"K", 	"A"),
		new PlanetClassModel(CLASS_L,					8,		"Hostile",			null, 		"L", 	"H"),
		new PlanetClassModel(CLASS_L_DESERT,		8,		"Hostile",			"Desert",	"L", 	"H"),
		new PlanetClassModel(CLASS_L_JUNGLE,		8,		"Hostile",			"Jungle",	"L", 	"H"),
		new PlanetClassModel(CLASS_M,					20,	"Terrestrial",		null, 		"M", 	"T"),
		new PlanetClassModel(CLASS_N,					2,		"Sulfurique",		null, 		"N", 	"S"),
		new PlanetClassModel(CLASS_P,					2,		"Hostile",			"Glaciated","P", 	"H"),
		new PlanetClassModel(CLASS_T,					2,		"Gas Giant",		null, 		"T", 	"G"),
		new PlanetClassModel(CLASS_Y,					1,		"Deamon",			null, 		"Y", 	"Y"),
		new PlanetClassModel(CLASS_Z,					1,		"Artificial",		null, 		"Z", 	"Z")
	};

	public PlanetClassModel (int classification, int rand, String name, String subName, String STShortName, String humanShortName) {
		this.id = classification;
		this.rand = rand;
		this.name = name + " (" + humanShortName + ")";
		this.subName = subName;
		this.shortName = humanShortName;
	}

	public static String getText(int classId) {
		return sClass[classId].name + " (" + sClass[classId].shortName + ")";
	}

	public static String getShortText(int classId) {
		return sClass[classId].shortName;
	}
	
}
