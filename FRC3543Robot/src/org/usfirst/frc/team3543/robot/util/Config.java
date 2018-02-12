package org.usfirst.frc.team3543.robot.util;

import java.util.HashMap;

public class Config<T> extends HashMap<String, T>{

	private static final long serialVersionUID = 1L;

	public T lookup(String key, T defaultValue) {
		if (this.containsKey(key)) {
			return this.get(key);
		}
		else {
			return defaultValue;
		}
	}
}
