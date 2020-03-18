package io.swagger.util;

import java.util.SplittableRandom;

public class RandomDataGenerator {

	public static String generateRandomAlpha(int length) {
		String aToZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SplittableRandom rand = new SplittableRandom();
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int randIndex = rand.nextInt(aToZ.length());
			res.append(aToZ.charAt(randIndex));
		}
		return res.toString();
	}

	public static String generateRandomNumeric(int length) {
		String zeroTo9 = "0123456789";
		SplittableRandom rand = new SplittableRandom();
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int randIndex = rand.nextInt(zeroTo9.length());
			res.append(zeroTo9.charAt(randIndex));
		}
		return res.toString();
	}
	
	
}
