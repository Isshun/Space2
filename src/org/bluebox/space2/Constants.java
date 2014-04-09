package org.bluebox.space2;

//Galaxy Mini				320 x 240	1.3
//Galaxy Ace				480 x 320	1.5
//Galaxy S II				800 x 480	1.6
//Galaxy S4					1920 x 1080	1.7
//Galaxy Tab				1024 x 600	1.7 	
//Galaxy Tab 7.7 / 10.1	1280 x 800  1.6
//Nexus 4 					1280 x 768 	1.6	320ppi

public class Constants {
//	public static int GAME_WIDTH = 480;
//	public static int GAME_HEIGHT = 320;
	public static int GAME_WIDTH = 1280;
	public static int GAME_HEIGHT = 780;
	public static final long TOUCH_MOVE_DELAY = 50;
	public static final int SYSTEM_SIZE = 22;
	public static final int TOUCH_RECOVERY = 500;
	public static final int UPDATE_INTERVAL = 1;
	public static final int TOUCH_MARGIN = 20;
	public static final long LONG_TOUCH_INTERVAL = 200;
	public static final int TOUCH_MOVE_MARGIN = 10;
	
	// Fix missing stop touch
	public static final int TOUCH_MOVE_INTERVAL = 100;
	public static final long TOUCH_MOVE_OFFSET = 20;
	public static final long TICK_MS_INTERVAL = 10;
	
	// onTouch after this delay are not throw
	public static final long TOUCH_DELAY = 150;
	public static final long BACK_MIN_DELAY = 250;
	public static final int MAP_WIDTH = 1000;
	public static final int MAP_HEIGHT = 600;

	
	public static final int SCREEN_SCALE = 1;
	public static final int SCREEN_TRANSITION_OFFSET = 32;

}
