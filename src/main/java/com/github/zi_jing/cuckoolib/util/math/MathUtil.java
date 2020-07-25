package com.github.zi_jing.cuckoolib.util.math;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class MathUtil {
    public static final String M[] = {"", "M", "MM", "MMM"};
    public static final String C[] = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
    public static final String X[] = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    public static final String I[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

    public static final String[] HIGH = {"k", "M", "G", "T", "P", "E"};
    public static final String[] LOW = {"m", "μ", "n", "p", "f", "a"};

    public static final String WORD = "fZodR9XQDSUm21yCkr6zBqiveYah8bt4xsWpHnJE7jL5VG3guMTKNPAwcF";
    public static final BiMap<Integer, String> WORD_MAP = HashBiMap.create(58);
    public static final BiMap<String, Integer> INVERSED_WORD_MAP = WORD_MAP.inverse();
    public static final int[] SET = {9, 8, 1, 6, 2, 4};
    public static final long XOR = 177451812;
    public static final long ADD = 8728348608l;

    static {
        for (int i = 0; i < WORD.length(); i++) {
            WORD_MAP.put(i, WORD.substring(i, i + 1));
        }
    }

    public static String encodeBv(long av) {
        av = (av ^ XOR) + ADD;
        String bv = "1  4 1 7  ";
        for (int i = 0; i < 6; i++) {
            String update = WORD_MAP.get((int) ((av / Math.pow(58, i)) % 58));
            bv = bv.substring(0, SET[i]) + update + bv.substring(SET[i] + 1);
        }
        return bv;
    }

    public static long decodeBv(String bv) {
        long av = 0;
        for (int i = 0; i < 6; i++) {
            av += ((long) INVERSED_WORD_MAP.get(bv.substring(SET[i], SET[i] + 1))) * Math.pow(58, i);
        }
        av = (av - ADD) ^ XOR;
        return av;
    }

    public static String romanNumber(int n) {
        if (n == 0) {
            return null;
        }
        return M[n / 1000] + C[(n % 1000) / 100] + X[(n % 100) / 10] + I[n % 10];
    }

    public static String formatOrder(double num) {
        if (num == 0) {
            return "0";
        }
        String str = "";
        if (num < 0) {
            num = -num;
        }
        int i;
        if (num >= 1000) {
            for (i = -1; num >= 1000; i++) {
                num /= 1000;
            }
            str += HIGH[i];
        } else if (num < 1) {
            for (i = -1; num < 1; i++) {
                num *= 10;
                num *= 10;
                num *= 10;
            }
            str += LOW[i];
        }
        return str;
    }

    public static String formatNumber(double num) {
        if (num == 0) {
            return "0";
        }
        String str = "";
        if (num < 0) {
            num = -num;
            str += "-";
        }
        int i;
        if (1 <= num && num < 1000) {
            if ((int) (num * 10 % 10) == 0) {
                str += String.format("%.0f", num);
            } else {
                str += String.format("%.1f", num);
            }
        } else if (num >= 1000) {
            for (i = -1; num >= 1000 && i < 6; i++) {
                num /= 1000;
            }
            if ((int) (num * 10 % 10) == 0) {
                str += String.format("%.0f", num);
            } else {
                str += String.format("%.1f", num);
            }
        } else {
            for (i = -1; num < 1 && i < 6; i++) {
                num *= 1000;
            }
            if ((int) (num * 10 % 10) == 0) {
                str += String.format("%.0f", num);
            } else {
                str += String.format("%.1f", num);
            }
        }
        return str;
    }

    public static String format(double num) {
        return formatNumber(num) + formatOrder(num);
    }
}