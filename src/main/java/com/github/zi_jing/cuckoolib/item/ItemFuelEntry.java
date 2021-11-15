package com.github.zi_jing.cuckoolib.item;

import com.github.zi_jing.cuckoolib.material.MatterState;
import com.github.zi_jing.cuckoolib.util.data.FuelInfo;

public class ItemFuelEntry {
	protected FuelInfo fuelInfo;
	protected double fuelMass;
	// 由于燃料形态等因素, 可能燃料达到了燃点也难以反应, 姑且引入此系数, 代表表观燃点与实际燃点的比值, 应该≥1
	protected double flameRetardancy;

	public ItemFuelEntry(FuelInfo fuelInfo, double fuelMass) {
		this(fuelInfo, fuelMass, 1);
	}

	public ItemFuelEntry(FuelInfo fuelInfo, double fuelMass, double flameRetardancy) {
		this.fuelInfo = fuelInfo;
		this.fuelMass = fuelMass;
		this.flameRetardancy = flameRetardancy;
	}

	public FuelInfo getFuleInfo() {
		return this.fuelInfo;
	}

	public MatterState getState() {
		return this.fuelInfo.getState();
	}

	public double getCalorificValue() {
		return this.fuelInfo.getCalorificValue();
	}

	public double getRealIgnitionPoint() {
		return this.flameRetardancy * this.fuelInfo.getIgnitionPoint();
	}

	public double getFuelMass() {
		return this.fuelMass;
	}

	public double getFlameRetardancy() {
		return this.flameRetardancy;
	}
}