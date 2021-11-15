package com.github.zi_jing.cuckoolib.material;

import net.minecraft.client.resources.I18n;

public enum MatterState {
	SOLID("solid"), LIQUID("liquid"), GAS("gas"), PLASMA("plasma");

	private String name;

	MatterState(String name) {
		this.name = name;
	}

	public String getStateName() {
		return this.name;
	}

	public String getStateLocalizedName() {
		return I18n.get("cuckoolib.material.state." + this.name);
	}
}