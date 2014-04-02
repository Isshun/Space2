package com.mojang.metagun.model;

public class PlanetSizeModel {
	public static String sSize1 = "Tiny (--)";
	public static String sSize2 = "Small(-)";
	public static String sSize3 = "Medium (-)";
	public static String sSize4 = "Large (++)";
	public static String sSize5 = "Giant (+++)";

	public static String sShortSize1 = "--";
	public static String sShortSize2 = "-";
	public static String sShortSize3 = "-";
	public static String sShortSize4 = "++";
	public static String sShortSize5 = "+++";

	public static String getText(int sizeId) {
		switch (sizeId) {
		case 0: return sSize1;
		case 1: return sSize2;
		case 2: return sSize3;
		case 3: return sSize4;
		case 4: return sSize5;
		}
		return null;
	}

	public static String getShortText(int sizeId) {
		switch (sizeId) {
		case 0: return sShortSize1;
		case 1: return sShortSize2;
		case 2: return sShortSize3;
		case 3: return sShortSize4;
		case 4: return sShortSize5;
		}
		return null;
	}
}
