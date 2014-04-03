package com.elfdependencywalker;

public class TestThread implements Runnable {
	public static void main(String args[]) {
		for (int x = 0; x < 10; x++) {
			new Thread(new TestThread()).start();
		}
	}

	@Override
	public void run() {
		double r = 1.2323;
		while (true) {
			r = r * r + Math.pow(r, r);
		}
	}
}
