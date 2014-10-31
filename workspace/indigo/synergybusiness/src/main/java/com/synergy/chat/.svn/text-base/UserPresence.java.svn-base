package com.synergy.chat;

import java.util.HashMap;

public class UserPresence {

	public static final Byte ONLINE = 0;
	public static final Byte OFFLINE = 1;
	public static final Byte BUSY = 2;

	private static UserPresence instance;

	public static UserPresence getInstance() {
		if (instance == null) {
			instance = new UserPresence();
		}
		return instance;
	}

	private HashMap<Number, Byte> states = new HashMap<Number, Byte>();

	private UserPresence() {
	}

	public void setState(Number userId, Byte state) {
		states.put(userId, state);
	}

	public Byte getState(Number userId) {
		Byte state = states.get(userId);
		return state != null ? state : UserPresence.OFFLINE;
	}

	public boolean isOnLine(Number userId) {
		return getState(userId) == UserPresence.ONLINE;
	}

}
