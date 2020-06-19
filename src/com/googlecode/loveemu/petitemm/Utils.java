package com.googlecode.loveemu.petitemm;

public final class Utils {
	
	private Utils() {
		super();
	}
	
	public static int getClosestValue(double[] vector, double value) {
		double prevDiff = Double.POSITIVE_INFINITY;
		for(int i = 0; i < vector.length; i++) {
			double diff = vector[i] - value;
			if(i != 0 && diff * prevDiff <= 0) {
				return Math.abs(diff) < Math.abs(prevDiff) ? i : i - 1;
			}
			prevDiff = diff;
		}
		return 0;
	}
	
	public static int getClosestValue(int[] vector, int value) {
		int prevDiff = Integer.MAX_VALUE;
		for(int i = 0; i < vector.length; i++) {
			int diff = vector[i] - value;
			if(i != 0 && (long) diff * prevDiff <= 0) {
				return Math.abs(diff) < Math.abs(prevDiff) ? i : i - 1;
			}
			prevDiff = diff;
		}
		return 0;
	}
	
}
