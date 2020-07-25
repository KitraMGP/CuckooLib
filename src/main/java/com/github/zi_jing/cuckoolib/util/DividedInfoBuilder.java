package com.github.zi_jing.cuckoolib.util;

import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class DividedInfoBuilder {
    private List<InfoBuf> list;

    public DividedInfoBuilder() {
        this.list = NonNullList.create();
    }

    public void addInfo(InfoBuf info) {
        this.list.add(info);
    }

    public String build() {
        String str = TextFormatting.RESET + "";
        for (int i = 0; i < this.list.size(); i++) {
            if (i != 0) {
                str += TextFormatting.RESET + " | ";
            }
            str += list.get(i).build();
        }
        str += TextFormatting.RESET;
        return str;
    }

    public static class InfoBuf {
        protected String inf, unit;
        protected int accuracy;
        protected double value;
        protected TextFormatting formatValue;

        public InfoBuf(String inf, int value, String unit) {
            this(inf, value, TextFormatting.RESET, unit);
        }

        public InfoBuf(String inf, double value, TextFormatting formatValue, String unit) {
            this.inf = inf;
            this.value = value;
            this.unit = unit;
            this.formatValue = formatValue;
        }

        public InfoBuf setAccuracy(int accuracy) {
            this.accuracy = accuracy;
            return this;
        }

        public String build() {
            return TextFormatting.YELLOW + this.inf + ": " + this.formatValue
                    + String.format("%." + this.accuracy + "f", this.value) + TextFormatting.GREEN + this.unit;
        }
    }
}