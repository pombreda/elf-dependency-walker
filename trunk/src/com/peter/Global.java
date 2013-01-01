package com.peter;

public class Global {
	public static String version = "20121211";
	public static boolean debug = false;
	public static boolean isMac = false;

	public static void debug(String str) {
		if (debug) {
			System.out.println(str);
		}
	}

	public static void debug() {
		if (debug) {
			System.out.println();
		}
	}
}
