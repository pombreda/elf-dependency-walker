package com.elfdependencywalker;

import java.io.File;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;

public class ELFNode implements TreeNode, Comparable {
	static Icon icon = new ImageIcon(ELFNode.class.getClassLoader().getResource("icons/famfam_icons/script.png"));
	static Icon directoryIcon = new ImageIcon(ELFNode.class.getClassLoader().getResource("icons/famfam_icons/folder.png"));
	static Icon notFoundIcon = new ImageIcon(ELFNode.class.getClassLoader().getResource("icons/famfam_icons/cross.png"));
	File file;

	String nmResult;
	boolean notFound;
	//	ELFNode parent;
	public LinkedHashSet<ELFNode> parent = new LinkedHashSet<ELFNode>();
	public LinkedHashSet<ELFNode> child = new LinkedHashSet<ELFNode>();
	private int level = -1;

	public ELFNode(ELFNode parent, File file, String result, boolean notFound) {
		this.parent.add(parent);
		this.file = file;
		this.nmResult = result;
		this.notFound = notFound;
	}

	public File getFile() {
		return file;
	}

	public String getNmResult() {
		return nmResult;
	}

	public void setNmResult(String nmResult) {
		this.nmResult = nmResult;
	}

	public Icon getIcon() {
		if (file != null && file.isDirectory()) {
			return directoryIcon;
		} else if (notFound) {
			return notFoundIcon;
		} else {
			return icon;
		}
	}

	public String toString() {
		if (file != null && file.isDirectory()) {
			return file.getPath();
		} else {
			return file.getName();
		}
	}

	@Override
	public Enumeration children() {
		return null;
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return (TreeNode) child.toArray()[childIndex];
	}

	@Override
	public int getChildCount() {
		return child.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		for (int x = 0; x < getChildCount() - 1; x++) {
			if (child.toArray()[x] == node) {
				return x;
			}
		}
		return -1;
	}

	@Override
	public TreeNode getParent() {
		return (TreeNode) parent.toArray()[0];
	}

	@Override
	public boolean isLeaf() {
		return getChildCount() == 0;
	}

	public int getLevel() {
		if (level >= 0) {
			return level;
		} else {
			level = getLevel(this, -1, new HashSet<String>());
			return level;
		}
	}

	public int getLevel(ELFNode node, int level, HashSet<String> parsedNode) {
		if (node == null || node.parent.size() == 0 || parsedNode.contains(node.getFile().getName())) {
			return level;
		}
		parsedNode.add(node.getFile().getName());
		int maxLevel = 0;
		Iterator<ELFNode> ir = node.parent.iterator();
		while (ir.hasNext()) {
			int temp = getLevel(ir.next(), level + 1, parsedNode);
			if (temp > maxLevel) {
				maxLevel = temp;
			}
		}
		return maxLevel;
	}

	@Override
	public int compareTo(Object o) {
		return file.compareTo(((ELFNode) o).file);
	}
}
