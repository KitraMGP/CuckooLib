package com.github.zi_jing.cuckoolib.util.registry;

import java.util.Map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.util.registry.RegistrySimple;

public class SizeLimitedRegistry<K, V> extends RegistrySimple<K, V> {
	protected Map<V, K> inversedRegistry;
	protected IntIdentityHashBiMap<V> idValueMap;
	private boolean frozen;
	private int size;

	public SizeLimitedRegistry(int size) {
		this.size = size;
		this.inversedRegistry = ((BiMap<K, V>) this.registryObjects).inverse();
		this.idValueMap = new IntIdentityHashBiMap<V>(size);
	}

	@Override
	protected Map<K, V> createUnderlyingMap() {
		return HashBiMap.create();
	}

	public boolean isFrozen() {
		return this.frozen;
	}

	public void freeze() {
		if (this.frozen) {
			throw new IllegalStateException("This registry is already forzen");
		}
		this.frozen = true;
	}

	@Override
	public void putObject(K key, V value) {
		throw new UnsupportedOperationException("Use #register(id, key, value)");
	}

	public void register(int id, K key, V value) {
		if (this.size <= id) {
			throw new IllegalArgumentException("The maximum ID is 64");
		}
		if (this.frozen) {
			throw new IllegalStateException("This registry is already forzen");
		}
		super.putObject(key, value);
		V objectWithId = this.getObjectById(id);
		if (objectWithId != null) {
			throw new IllegalArgumentException("The ID of [ " + key + " ] is registered");
		}
		this.idValueMap.put(value, id);
	}

	public K getKeyForObject(V value) {
		return this.inversedRegistry.get(value);
	}

	public int getIdForObject(V value) {
		return this.idValueMap.getId(value);
	}

	public V getObjectById(int id) {
		return this.idValueMap.get(id);
	}
}