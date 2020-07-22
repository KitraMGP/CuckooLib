package com.github.zi_jing.cuckoolib.uti.registry;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import net.minecraft.util.NonNullList;

public class UnorderedRegistry<V> {
	private List<V> list;
	private boolean frozen;

	public UnorderedRegistry() {
		this.list = NonNullList.create();
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

	public List<V> list() {
		return this.list;
	}

	public Stream<V> stream() {
		return this.list.stream();
	}

	public Iterator<V> iterator() {
		return this.list.iterator();
	}

	public void register(V value) {
		if (this.frozen) {
			throw new IllegalStateException("This registry is already forzen");
		}
		if (this.list.contains(value)) {
			throw new IllegalStateException("This value has been registered");
		}
		this.list.add(value);
	}

	public void delete(V value) {
		if (this.list.contains(value)) {
			this.list.remove(value);
		}
	}
}