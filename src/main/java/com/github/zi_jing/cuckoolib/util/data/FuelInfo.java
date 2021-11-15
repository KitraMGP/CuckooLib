package com.github.zi_jing.cuckoolib.util.data;

import com.github.zi_jing.cuckoolib.item.ItemFuelEntry;
import com.github.zi_jing.cuckoolib.material.MatterState;

public class FuelInfo {
	protected MatterState state;
	// 单位: K
	protected double ignitionPoint;
	// 单位: J/kg (固体); J/L (流体)
	protected double calorificValue;

	public static FuelInfo of(MatterState state, float ignitionPoint, float calorificValue) {
		FuelInfo info = new FuelInfo();
		info.state = state;
		info.ignitionPoint = ignitionPoint;
		info.calorificValue = calorificValue;
		return info;
	}

	public MatterState getState() {
		return this.state;
	}

	public double getIgnitionPoint() {
		return this.ignitionPoint;
	}

	public double getCalorificValue() {
		return this.calorificValue;
	}

	public ItemFuelEntry toEntry(double fuelMass) {
		return new ItemFuelEntry(this, fuelMass);
	}

	public ItemFuelEntry toEntry(double fuelMass, double flameRetardancy) {
		return new ItemFuelEntry(this, fuelMass, flameRetardancy);
	}
}