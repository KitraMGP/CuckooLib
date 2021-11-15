package com.github.zi_jing.cuckoolib.material.type;

import java.util.HashMap;
import java.util.Map;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.github.zi_jing.cuckoolib.material.MatterState;
import com.github.zi_jing.cuckoolib.util.data.UnorderedRegistry;

import net.minecraft.client.resources.I18n;
import net.minecraft.fluid.Fluid;

public class MaterialBase {
	public static final UnorderedRegistry<String, MaterialBase> REGISTRY = new UnorderedRegistry<String, MaterialBase>();

	public static final Map<Class<? extends MaterialBase>, Long> DEFAULT_FLAGS = new HashMap<Class<? extends MaterialBase>, Long>();

	public static final IMaterialFlag GENERATE_DUST = createFlag(0);
	public static final IMaterialFlag GENERATE_PLATE = createFlag(1);
	public static final IMaterialFlag GENERATE_ROD = createFlag(2);
	public static final IMaterialFlag GENERATE_GEAR = createFlag(3);
	public static final IMaterialFlag GENERATE_CRYSTAL = createFlag(4);
	public static final IMaterialFlag GENERATE_INGOT = createFlag(5);
	public static final IMaterialFlag GENERATE_FOIL = createFlag(6);
	public static final IMaterialFlag GENERATE_SCREW = createFlag(7);
	public static final IMaterialFlag GENERATE_SPRING = createFlag(8);
	public static final IMaterialFlag GENERATE_RING = createFlag(9);
	public static final IMaterialFlag GENERATE_WIRE = createFlag(10);
	public static final IMaterialFlag GENERATE_ROTOR = createFlag(11);
	public static final IMaterialFlag GENERATE_NUGGET = createFlag(12);
	public static final IMaterialFlag GENERATE_TOOL = createFlag(16);

	protected String name;
	protected int color, durability, harvestLevel;
	protected long flagValue;
	protected float efficiency;
	protected double meltingPoint, boilingPoint, heatCapacity;

	public MaterialBase(String name, int color) {
		this.name = name;
		this.color = color;
		this.register();
	}

	public static void registerDefaultFlags(Class<? extends MaterialBase> type, IMaterialFlag... flags) {
		DEFAULT_FLAGS.put(type, combineFlags(flags));
	}

	public static IMaterialFlag createFlag(int id) {
		if (id < 64 && id >= 0) {
			return () -> id;
		}
		throw new IllegalArgumentException("The maximum Material Flag ID is 63");
	}

	public static long combineFlags(IMaterialFlag... flags) {
		long ret = 0;
		for (IMaterialFlag flag : flags) {
			ret |= flag.getValue();
		}
		return ret;
	}

	public void setPointTemp(double meltingPoint, double boilingPoint) {
		if (!this.validatePointTemp(meltingPoint, boilingPoint)) {
			throw new IllegalArgumentException("The matter state points [ " + meltingPoint + ", " + boilingPoint + ", " + " ] is invalied");
		}
		this.meltingPoint = meltingPoint;
		this.boilingPoint = boilingPoint;
	}

	public void setHeatCapacity(double heatCapacity) {
		this.heatCapacity = heatCapacity > 0 ? heatCapacity : 1600;
	}

	public double getHeatCapacity() {
		return this.heatCapacity;
	}

	public boolean validatePointTemp(double meltingPoint, double boilingPoint) {
		return boilingPoint >= meltingPoint && meltingPoint >= 0;
	}

	public MatterState getState(double temp) {
		if (temp < this.meltingPoint) {
			return MatterState.SOLID;
		}
		if (temp < this.boilingPoint) {
			return MatterState.LIQUID;
		}
		return MatterState.GAS;
	}

	public boolean existState(MatterState state) {
		switch (state) {
		case SOLID:
			return this.meltingPoint != 0;
		case LIQUID:
			return this.meltingPoint <= this.boilingPoint;
		case GAS:
			return true;
		default:
			return false;
		}
	}

	public static MaterialBase getMaterialByName(String name) {
		return REGISTRY.get(name);
	}

	public void addFlag(IMaterialFlag flag) {
		this.flagValue |= flag.getValue();
	}

	public String getName() {
		return this.name;
	}

	public int getColor() {
		return this.color;
	}

	public boolean hasFlag(IMaterialFlag flag) {
		if (DEFAULT_FLAGS.containsKey(this.getClass())) {
			return ((this.flagValue | DEFAULT_FLAGS.get(this.getClass())) & (flag.getValue())) != 0;
		}
		return (this.flagValue & (flag.getValue())) != 0;
	}

	public boolean generateLiquid() {
		return false;
	}

	public boolean generateGas() {
		return false;
	}

	public boolean generatePlasma() {
		return false;
	}

	public Fluid getLiquid() {
		return null;
	}

	public Fluid getGas() {
		return null;
	}

	public void setToolProperty(int durability, int harvestLevel, float efficiency) {
		this.durability = Math.max(durability, 0);
		this.harvestLevel = Math.max(harvestLevel, 0);
		this.efficiency = Math.max(efficiency, 0);
	}

	public int getDurability() {
		return this.durability;
	}

	public int getHarvestLevel() {
		return this.harvestLevel;
	}

	public float getEfficiency() {
		return this.efficiency;
	}

	public String getUnlocalizedName() {
		return CuckooLib.MODID + ".material." + this.name + ".name";
	}

	public String getLocalizedName() {
		return I18n.get(this.getUnlocalizedName());
	}

	@Override
	public String toString() {
		return this.name;
	}

	public void register() {
		REGISTRY.register(this.name, this);
	}
}