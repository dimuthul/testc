package com.hackerthon.common;

import java.util.Properties;


public class UtilC {

	public static final Properties p = new Properties();

	static {
		try {
			p.load(UtilQ.class.getResourceAsStream("../config/config.properties"));
		} catch (Exception e) {
			
		}
	}
}
