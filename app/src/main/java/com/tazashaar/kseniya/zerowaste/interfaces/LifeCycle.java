package com.tazashaar.kseniya.zerowaste.interfaces;

public interface LifeCycle<V> {
	void bind(V view);

	void unbind();
}
