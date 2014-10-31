package com.synergychat;

import java.util.HashMap;

public class SingletonContext {

	private static SingletonContext instance;

	private SingletonContext() {
	}

	public static SingletonContext getInstance() {
		if (instance == null) {
			instance = new SingletonContext();
		}
		return instance;
	}

	private HashMap<String, Object> ctx = new HashMap<String, Object>();

	public void set(String key, Object value) {
		ctx.put(key, value);
	}

	public Object get(String key) {
		return ctx.get(key);
	}

	public boolean contains(String key) {
		return ctx.containsKey(key);
	}

	public void remove(String key) {
		ctx.remove(key);
	}

}
