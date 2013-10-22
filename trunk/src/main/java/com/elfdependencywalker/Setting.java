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
			IOUtils.write(xml, new FileOutputStream(new File("elf-dependency-walker.xml")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Setting load() {
		try {
			XStream xstream = new XStream();
			xstream.alias("Setting", Setting.class);
			Setting setting = (Setting) xstream.fromXML(new FileInputStream(new File("elf-dependency-walker.xml")));
			return setting;
		} catch (Exception ex) {
			new File("elf-dependency-walker.xml").delete();
			Setting setting = new Setting();
			setting.lookupDirectory.add("/usr");
			setting.lookupDirectory.add("/usr/lib");
			setting.lookupDirectory.add("/lib");
			setting.lookupDirectory.add("/usr/local/lib");
			setting.lookupDirectory.add("/lib64");
			setting.lookupDirectory.add("/usr/lib64");
			setting.lookupDirectory.add("/usr/local/lib64");
			setting.lookupDirectory.add("/lib/x86_64-linux-gnu");
			setting.width = 800;
			setting.height = 600;
			setting.save();
			return setting;
		}
	}

	public static void main(String args[]) {
		Setting setting = Setting.getInstance();
		System.out.println(setting.width);
	}
}
