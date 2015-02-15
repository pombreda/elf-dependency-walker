package com.elfdependencywalker;

public class Global {
	public static boolean debug = true;
	public static boolean isMac = false;
	public static String filename = "elf.png";

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
