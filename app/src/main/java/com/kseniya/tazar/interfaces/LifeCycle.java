package com.kseniya.tazar.interfaces;

public interface LifeCycle<V> {
	void bind(V view);

	void unbind();
}
