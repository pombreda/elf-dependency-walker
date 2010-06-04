package com.peter;

import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

public class ELFNode extends DefaultMutableTreeNode {
	Icon icon = new ImageIcon(getClass().getClassLoader().getResource("icons/famfam_icons/script.png"));
	File file;
	String nmResult;

	public ELFNode(File file, String nmResult) {
		this.file = file;
		this.nmResult = nmResult;
	}

	public Icon getIcon() {
		return icon;
	}

	public String toString() {
		return file.getName();
	}
}
