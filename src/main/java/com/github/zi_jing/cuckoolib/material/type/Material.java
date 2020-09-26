package com.github.zi_jing.cuckoolib.material.type;

import java.util.HashMap;
import java.util.Map;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.github.zi_jing.cuckoolib.util.IRegistrable;
import com.github.zi_jing.cuckoolib.util.registry.SizeLimitedRegistry;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fluids.Fluid;

public abstract class Material implements IRegistrable {
	public static final SizeLimitedRegistry<String, Material> REGISTRY = new SizeLimitedRegistry<String, Material>(
			1024);

	public static final Map<Class<? extends Material>, Long> DEFAULT_FLAGS = new HashMap<Class<? extends Material>, Long>();

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
	public static final IMaterialFlag GENERATE_TOOL = createFlag(16);

	protected String name;
	protected int id, color, durability, harvestLevel;
	protected long flagValue;

	public Material(int id, String name, int color) {
		this.id = id;
		this.name = name;
		this.color = color;
	}

	public static Material getMaterialById(int id) {
		return REGISTRY.getObjectById(id);
	}

	public static Material getMaterialByName(String name) {
		return REGISTRY.getObject(name);
	}

	public static void registerDefaultFlags(Class<? extends Material> type, IMaterialFlag... flags) {
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

	public void addFlag(IMaterialFlag flag) {
		this.flagValue |= flag.getValue();
	}

	public String getName() {
		return this.name;
	}

	public int getId() {
		return this.id;
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

	public Fluid getPlasma() {
		return null;
	}

	public void setDurability(int durability) {
		if (durability < 0) {
			throw new IllegalArgumentException("Material durability must be a nonnegative number");
		}
		this.durability = durability;
	}

	public void setHarvestLevel(int harvestLevel) {
		if (harvestLevel < 0) {
			throw new IllegalArgumentException("Material harvest level must be a nonnegative number");
		}
		this.harvestLevel = harvestLevel;
	}

	public int getDurability() {
		return this.durability;
	}

	public int getHarvestLevel() {
		return this.harvestLevel;
	}

	public String getUnlocalizedName() {
		return CuckooLib.MODID + ".material." + this.name + ".name";
	}

	public String getLocalizedName() {
		return I18n.format(this.getUnlocalizedName());
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public void register() {
		REGISTRY.register(this.id, this.name, this);
	}
}