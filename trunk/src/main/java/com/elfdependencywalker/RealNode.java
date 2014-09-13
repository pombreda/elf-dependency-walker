package com.elfdependencywalker;

import java.io.File;
import java.util.LinkedHashSet;

public class RealNode {
	File file;
	public LinkedHashSet<RealNode> parent = new LinkedHashSet<RealNode>();
	public LinkedHashSet<RealNode> child = new LinkedHashSet<RealNode>();

	public RealNode(RealNode parent, File file) {
		this.parent.add(parent);
		this.file = file;
	}
}
