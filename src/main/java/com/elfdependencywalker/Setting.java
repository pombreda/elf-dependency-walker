package com.elfdependencywalker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedHashSet;

import org.apache.commons.io.IOUtils;

import com.thoughtworks.xstream.XStream;

public class Setting {
	private static Setting setting = null;
	public int x;
	public int y;
	public int width;
	public int height;
	public int divX;
	public String lastOpenPath;
	public LinkedHashSet<String> historyList = new LinkedHashSet<String>();
	public LinkedHashSet<String> lookupDirectory = new LinkedHashSet<String>();

	public static Setting getInstance() {
		if (setting == null) {
			setting = load();
		}
		return setting;
	}

	public void save() {
		XStream xstream = new XStream();
		xstream.alias("Setting", Setting.class);
		String xml = xstream.toXML(this);
		try {
			IOUtils.write(xml, new FileOutputStream(new File("gkd.xml")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Setting load() {
		try {
			XStream xstream = new XStream();
			xstream.alias("Setting", Setting.class);
			Setting setting = (Setting) xstream.fromXML(new FileInputStream(new File("gkd.xml")));
			return setting;
		} catch (Exception ex) {
			new File("gkd.xml").delete();
			Setting Setting = new Setting();
			Setting.save();
			return Setting;
		}
	}

	public static void main(String args[]) {
		Setting setting = Setting.getInstance();
		System.out.println(setting.width);
	}
}
