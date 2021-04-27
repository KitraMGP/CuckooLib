package com.github.zi_jing.cuckoolib.util.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;

public class ContinuousMap<K> {
	private static final Comparator<Pair<Float, ?>> COMPARATOR = (pair1, pair2) -> {
		return pair1.getLeft().compareTo(pair2.getLeft());
	};

	protected List<Pair<Float, K>> map;

	public ContinuousMap(List<Pair<Float, K>> map) {
		this.map = ImmutableList.copyOf(map);
	}

	public List<Pair<Float, K>> getPointsMap() {
		return this.map;
	}

	public K getValue(float num) {
		for (Pair<Float, K> pair : this.map) {
			if (num >= pair.getLeft()) {
				return pair.getRight();
			}
		}
		return null;
	}

	public static <K> Builder<K> builder() {
		return new Builder<K>();
	}

	public static class Builder<K> {
		protected Set<Float> criticalPoints;
		protected List<Pair<Float, K>> map;

		public Builder() {
			this.criticalPoints = new HashSet<Float>();
			this.map = new ArrayList<Pair<Float, K>>();
		}

		public Builder addCriticalPoint(float num, K value) {
			if (this.criticalPoints.contains(num)) {
				throw new IllegalArgumentException("Critical Points of ContinuousMap can't be repetitive");
			}
			this.criticalPoints.add(num);
			this.map.add(Pair.of(num, value));
			return this;
		}

		public ContinuousMap<K> build() {
			this.map.sort(COMPARATOR);
			return new ContinuousMap<K>(this.map);
		}
	}
}