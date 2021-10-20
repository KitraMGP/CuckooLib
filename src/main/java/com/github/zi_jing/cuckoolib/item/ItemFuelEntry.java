package com.github.zi_jing.cuckoolib.item;

import com.github.zi_jing.cuckoolib.util.data.FuelInfo;

public class ItemFuelEntry {
	protected FuelInfo fuelInfo;
	protected float fuelMass;
	// 由于燃料形态等因素, 可能燃料达到了燃点也难以反应, 姑且引入此系数, 代表表观燃点与实际燃点的比值, 应该≥1
	protected float flameRetardancy;

	public ItemFuelEntry(FuelInfo fuelInfo, float fuelMass) {
		this(fuelInfo, fuelMass, 1);
	}

	public ItemFuelEntry(FuelInfo fuelInfo, float fuelMass, float flameRetardancy) {
		this.fuelInfo = fuelInfo;
		this.fuelMass = fuelMass;
		this.flameRetardancy = flameRetardancy;
	}

	public FuelInfo getFuleInfo() {
		return this.fuelInfo;
	}

	public float getFuelMass() {
		return this.fuelMass;
	}

	public float getFlameRetardancy() {
		return this.flameRetardancy;
	}
}