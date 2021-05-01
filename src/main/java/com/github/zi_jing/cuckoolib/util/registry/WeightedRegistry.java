package com.github.zi_jing.cuckoolib.util.registry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.util.registry.RegistrySimple;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeightedRegistry<K, V> extends RegistrySimple<K, V> {
    protected int[] weightList;
    protected Map<V, K> inversedRegistry;
    protected IntIdentityHashBiMap<V> idValueMap;
    private boolean frozen;
    private int size, weightSum;

    public WeightedRegistry(int size) {
        this.size = size;
        this.weightList = new int[size];
        this.inversedRegistry = ((BiMap<K, V>) this.registryObjects).inverse();
        this.idValueMap = new IntIdentityHashBiMap<V>(size);
    }

    @Override
    protected Map<K, V> createUnderlyingMap() {
        return HashBiMap.create();
    }

    public int[] getWeightList() {
        return this.weightList;
    }

    public int getWeightSum() {
        return this.weightSum;
    }

    public List<Pair<V, Integer>> getValueWeightList() {
        List<Pair<V, Integer>> list = new ArrayList<Pair<V, Integer>>();
        for (V value : this.inversedRegistry.keySet()) {
            list.add(Pair.of(value, this.weightList[this.getIdForObject(value)]));
        }
        return list;
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
        throw new UnsupportedOperationException("Use #register(id, key, value, weight)");
    }

    public void register(int id, K key, V value, int weight) {
        if (this.size <= id) {
            throw new IllegalArgumentException("The maximum ID is 64");
        }
        if (this.frozen) {
            throw new IllegalStateException("This registry is already forzen");
        }
        if (weight <= 0) {
            weight = 1;
        }
        super.putObject(key, value);
        V objectWithId = this.getObjectById(id);
        if (objectWithId != null) {
            throw new IllegalArgumentException(
                    String.format(
                            "Tried to reassign id %d to %s (%s), but it is already assigned to %s (%s)!",
                            id, value, key, objectWithId, this.getKeyForObject(objectWithId)));
        }
        this.idValueMap.put(value, id);
        this.weightList[id] = weight;
        this.weightSum += weight;
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

    public V choose() {
        int offset = (int) (Math.random() * this.weightSum);
        int sum = 0;
        for (V value : this.inversedRegistry.keySet()) {
            int weight = this.weightList[this.getIdForObject(value)];
            if (sum <= offset && offset < sum + weight) {
                return value;
            }
            sum += weight;
        }
        return null;
    }
}
