package com.peter;

import java.io.File;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;

public class RealNode {
	File file;
	public LinkedHashSet<RealNode> parent = new LinkedHashSet<RealNode>();
	public LinkedHashSet<RealNode> child = new LinkedHashSet<RealNode>();

	public RealNode(RealNode parent, File file) {
		this.parent.add(parent);
		this.file = file;
	}
}
