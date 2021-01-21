package com.github.zi_jing.cuckoolib.util.math;

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
}