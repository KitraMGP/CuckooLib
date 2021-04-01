package com.github.zi_jing.cuckoolib.util.math;

import java.util.Arrays;
import java.util.Random;

public class MathUtil {
	public static boolean between(int value, int min, int max) {
		return value >= min && value <= max;
	}

	public static boolean between(long value, long min, long max) {
		return value >= min && value <= max;
	}

	public static boolean between(float value, float min, float max) {
		return value >= min && value <= max;
	}

	public static boolean between(double value, double min, double max) {
		return value >= min && value <= max;
	}

	public static int getTrueBits(int num) {
		int cnt = 0;
		while (num != 0) {
			num &= (num - 1);
			cnt++;
		}
		return cnt;
	}

	public static int getTrueBits(long num) {
		int cnt = 0;
		while (num != 0) {
			num &= (num - 1);
			cnt++;
		}
		return cnt;
	}

	public static int gcd(int a, int b) {
		int t;
		while (b > 0) {
			t = b;
			b = a % b;
			a = t;
		}
		return a;
	}

	public static int lcm(int a, int b) {
		return a * b / gcd(a, b);
	}

	public static long gcd(long a, long b) {
		long t;
		while (b > 0) {
			t = b;
			b = a % b;
			a = t;
		}
		return a;
	}

	public static long lcm(long a, long b) {
		return a * b / gcd(a, b);
	}

	public static int[] getRandomSortedArray(int length) {
		int[] copy = new int[length];
		for (int i = 0; i < length; i++) {
			copy[i] = i;
		}
		Random rand = new Random();
		for (int i = length - 1; i >= 0; i--) {
			int idx = rand.nextInt(i + 1);
			int val = copy[idx];
			copy[idx] = copy[i];
			copy[i] = val;
		}
		return copy;
	}

	public static int[] getRandomSortedArray(int[] array) {
		int[] copy = Arrays.copyOf(array, array.length);
		Random rand = new Random();
		for (int i = copy.length - 1; i >= 0; i--) {
			int idx = rand.nextInt(i + 1);
			int val = copy[idx];
			copy[idx] = copy[i];
			copy[i] = val;
		}
		return copy;
	}
}