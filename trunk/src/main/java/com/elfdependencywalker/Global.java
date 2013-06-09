package com.elfdependencywalker;

public class Global {
	public static String version = "20130125";
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
