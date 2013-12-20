package com.app.util;

import java.util.Random;

public class RandomUtil {
	public static int getRandomInteger(int aStart, int aEnd) {
		Random random = new Random();
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		long range = (long) aEnd - (long) aStart + 1;
		long fraction = (long) (range * random.nextDouble());
		int randomNumber = (int) (fraction + aStart);
		return randomNumber;
	}
}
