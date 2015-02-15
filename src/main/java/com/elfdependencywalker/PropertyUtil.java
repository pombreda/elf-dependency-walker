package com.elfdependencywalker;

import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
	static Properties prop = null;

	public static String getProperty(String name) {
		if (prop == null) {
			prop = new Properties();
			try {
				prop.load(PropertyUtil.class.getClassLoader().getResourceAsStream("main.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return prop.getProperty(name);
	}
	
	public static Properties getLangProperty(String lang) {
		Properties langProp = new Properties();
		try {
			langProp.load(PropertyUtil.class.getClassLoader().getResourceAsStream("lang_" + lang + ".properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return langProp;
	}
}
