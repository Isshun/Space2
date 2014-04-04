package com.mojang.metagun;

public class Utils {

	public static String getFormatedTime (int time) {
		if (time < 10) {
			return " 0:0" + time;
		} else if (time < 60) {
				return " 0:" + time;
		} else {
			int sec = time % 60;
			return " " + (int)(time / 60) + ":" + (sec < 10 ? "0" + sec : sec);
		}
	}

}
