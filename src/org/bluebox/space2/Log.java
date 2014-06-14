package org.bluebox.space2;

public class Log {
	public static int LEVEL_FATAL = 0;
	public static int LEVEL_ERROR = 1;
	public static int LEVEL_WARNING = 2;
	public static int LEVEL_INFO = 3;
	public static int LEVEL_DEBUG = 4;
	public static int LEVEL = LEVEL_DEBUG;
	
	public static void debug(String str) {
		if (LEVEL < LEVEL_DEBUG) return;
		
		if (str != null) {
			System.out.println(str);
		}
	}

	public static void error(String str) {
		if (LEVEL < LEVEL_ERROR) return;

		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		System.out.println("Error occured \"" + str + "\"");
		for (int i = 2; i < elements.length; i++) {
			System.out.println("          " + elements[i].toString());
		}
	}

	public static void info(String str) {
		if (LEVEL < LEVEL_INFO) return;

		if (str != null) {
			System.out.println(str);
		}
	}

	public static void warning(String str) {
		if (LEVEL < LEVEL_WARNING) return;

		if (str != null) {
			System.out.println(str);
		}
	}
}
