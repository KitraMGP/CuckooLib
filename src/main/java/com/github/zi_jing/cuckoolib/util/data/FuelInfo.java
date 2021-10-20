package com.github.zi_jing.cuckoolib.util.data;

import com.github.zi_jing.cuckoolib.item.ItemFuelEntry;
import com.github.zi_jing.cuckoolib.material.MatterState;

public class FuelInfo {
	protected MatterState state;
	// 单位: K
	protected float ignitionPoint;
	// 单位: J/kg (固体); J/L (流体)
	protected float calorificValue;

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

	public float getIgnitionPoint() {
		return this.ignitionPoint;
	}

	public float getCalorificValue() {
		return this.calorificValue;
	}

	public ItemFuelEntry toEntry(float fuelMass) {
		return new ItemFuelEntry(this, fuelMass);
	}

	public ItemFuelEntry toEntry(float fuelMass, float flameRetardancy) {
		return new ItemFuelEntry(this, fuelMass, flameRetardancy);
	}
}