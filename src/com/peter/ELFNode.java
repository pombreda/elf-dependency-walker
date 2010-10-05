package com.peter;

import java.io.File;
import java.util.Enumeration;
import java.util.LinkedHashSet;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;

public class ELFNode implements TreeNode {
	Icon icon = new ImageIcon(getClass().getClassLoader().getResource("icons/famfam_icons/script.png"));
	Icon notFoundIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/famfam_icons/cross.png"));
	File file;
	String nmResult;

	public String getNmResult() {
		return nmResult;
	}

	public void setNmResult(String nmResult) {
		this.nmResult = nmResult;
	}

	ELFNode parent;
	LinkedHashSet<ELFNode> child = new LinkedHashSet<ELFNode>();

	public ELFNode(File file, String result, ELFNode parent) {
		this.file = file;
		this.parent = parent;
		this.nmResult = result;
	}

	public Icon getIcon() {
		if (nmResult.equals("not found")) {
			return notFoundIcon;
		} else {
			return icon;
		}
	}

	public String toString() {
		return file.getName();
	}

	@Override
	public Enumeration children() {
		return (Enumeration) child;
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
		return parent;
	}

	@Override
	public boolean isLeaf() {
		return getChildCount() == 0;
	}

}
