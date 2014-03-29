package com.elfdependencywalker;

import java.io.File;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;

import org.apache.commons.collections.IteratorUtils;

public class ELFNode implements TreeNode, Comparable {
	static Icon icon = new ImageIcon(ELFNode.class.getClassLoader().getResource("icons/famfam_icons/script.png"));
	static Icon directoryIcon = new ImageIcon(ELFNode.class.getClassLoader().getResource("icons/famfam_icons/folder.png"));
	static Icon notFoundIcon = new ImageIcon(ELFNode.class.getClassLoader().getResource("icons/famfam_icons/cross.png"));
	File file;

	String nmResult;
	boolean notFound;
	public LinkedHashSet<ELFNode> parent = new LinkedHashSet<ELFNode>();
	public LinkedHashSet<ELFNode> child = new LinkedHashSet<ELFNode>();
	private int level = -1;

	boolean processed;

	public ELFNode(ELFNode parent, File file, String result, boolean notFound) {
		if (parent != null) {
			this.parent.add(parent);
		}
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
			return file.getName() + " ," + getLevel();
		}
	}

	@Override
	public Enumeration children() {
		return IteratorUtils.asEnumeration(child.iterator());
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
		return level;
	}

	public void updateLevel(int level) {
		if (this.level != -1) {
			System.out.println(file.getName() + " ==== " + level);
			return;
		}
		this.level = level;
		System.out.print(", " + file.getName() + "=" + level);
		Iterator<ELFNode> ir = child.iterator();
		while (ir.hasNext()) {
			ELFNode tempNode = ir.next();
			tempNode.updateLevel(level + 1);
		}
	}

	//	public int getLevel() {
	//		if (processed) {
	//			return level;
	//		} else {
	//			level = getLevel(this, 0, new HashSet<String>());
	//			System.out.println("level = " + file.getName() + " = " + level);
	//			return level;
	//		}
	//	}
	//
	//	public int getLevel(ELFNode node, int level, HashSet<String> parsedNode) {
	//		if (node == null || processed == true) {
	//			return level;
	//		}
	//		processed = true;
	//		int maxLevel = 0;
	//		if (node.parent != null) {
	//			Iterator<ELFNode> ir = node.parent.iterator();
	//			while (ir.hasNext()) {
	//				ELFNode tempNode = ir.next();
	//				int temp = getLevel(tempNode, level + 1, parsedNode);
	//				if (temp > maxLevel) {
	//					maxLevel = temp;
	//				}
	//			}
	//		}
	//		return maxLevel;
	//	}

	@Override
	public int compareTo(Object o) {
		return file.getName().compareTo(((ELFNode) o).file.getName());
	}

	public void setProcessed(boolean b) {
		if (processed == b) {
			return;
		}
		processed = b;
		Iterator<ELFNode> ir = child.iterator();
		while (ir.hasNext()) {
			ELFNode tempNode = ir.next();
			tempNode.setProcessed(b);
		}
	}
}
