package com.peter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedHashSet;
import java.util.Vector;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;

public class Setting {
	private static Setting setting = null;
	private int x;
	private int y;
	private int width;
	private int height;
	private int divX;
	private String lastOpenPath;
	private LinkedHashSet<String> historyList = new LinkedHashSet<String>();

	public LinkedHashSet<String> getHistoryList() {
		return historyList;
	}

	public void setHistoryList(LinkedHashSet<String> historyList) {
		this.historyList = historyList;
	}

	public String getLastOpenPath() {
		return lastOpenPath;
	}

	public void setLastOpenPath(String lastOpenPath) {
		this.lastOpenPath = lastOpenPath;
	}

	public Setting() {
		width = 800;
		height = 600;
		divX = 400;
	}

	public static Setting getInstance() {
		if (setting == null) {
			setting = load();
		}
		return setting;
	}

	public void save() {
		try {
			// Start by preparing the writer
			// We'll write to a string
			FileWriter outputWriter = new FileWriter(new File("setting.xml"));

			// Betwixt just writes out the bean as a fragment
			// So if we want well-formed xml, we need to add the prolog
			outputWriter.write("<?xml version='1.0' ?>");

			// Create a BeanWriter which writes to our prepared stream
			BeanWriter beanWriter = new BeanWriter(outputWriter);

			// Configure betwixt
			// For more details see java docs or later in the main documentation
			beanWriter.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
			beanWriter.getBindingConfiguration().setMapIDs(false);
			beanWriter.enablePrettyPrint();

			// If the base element is not passed in, Betwixt will guess
			// But let's write example bean as base element 'person'
			beanWriter.write("Setting", this);

			// Write to System.out
			// (We could have used the empty constructor for BeanWriter
			// but this way is more instructive)

			// Betwixt writes fragments not documents so does not automatically
			// close
			// writers or streams.
			// This example will do no more writing so close the writer now.
			outputWriter.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getDivX() {
		return divX;
	}

	public void setDivX(int divX) {
		this.divX = divX;
	}

	private static Setting load() {
		try {
			File file = new File("setting.xml");
			if (!file.exists()) {
				return new Setting();
			}

			FileReader reader = new FileReader(file);

			BeanReader beanReader = new BeanReader();
			beanReader.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
			beanReader.getBindingConfiguration().setMapIDs(false);
			beanReader.setValidating(false);
			beanReader.registerBeanClass("Setting", Setting.class);

			Setting setting = (Setting) beanReader.parse(reader);

			return setting;
		} catch (Exception ex) {
			ex.printStackTrace();
			return new Setting();
		}
	}

	public void addHistoryList(String str) {
		if (str != null) {
			historyList.add(str);
		}
	}

}
