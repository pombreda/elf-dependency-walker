package com.elfdependencywalker;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

public class MyTreeRenderer extends JLabel implements TreeCellRenderer {
	Color selectedBackgroundColor = new Color(200, 200, 240);

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		ELFNode node = (ELFNode) value;
		this.setIcon(node.getIcon());
		this.setText(node.toString());
		if (hasFocus) {
			this.setBackground(selectedBackgroundColor);
		} else {
			this.setBackground(Color.white);
		}
		this.setOpaque(true);
		return this;
	}

}
