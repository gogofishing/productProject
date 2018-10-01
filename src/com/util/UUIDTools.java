package com.util;

import java.util.UUID;

public class UUIDTools {

	public static Object getUUID() {
		// TODO Auto-generated method stub
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "").substring(0, 6);
	}
}
