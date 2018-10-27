package com.iotarch.farm.entity;

public class Role {
//	public static final String BARISTA = "barista";
//	public static final String BAKER = "baker";
	public static final String STAFF = "staff";
	// This role implicitly allows access to all views.
	public static final String ADMIN = "admin";

	private Role() {
		// Static methods and fields only
	}

	public static String[] getAllRoles() {
		return new String[] { STAFF, ADMIN };
	}

}
