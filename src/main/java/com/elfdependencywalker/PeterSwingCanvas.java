package com.elfdependencywalker;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.CellRendererPane;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.view.mxInteractiveCanvas;
import com.mxgraph.view.mxCellState;

public class PeterSwingCanvas extends mxInteractiveCanvas {
	protected CellRendererPane rendererPane = new CellRendererPane();

	protected JLabel jLabel = new JLabel();

	protected mxGraphComponent graphComponent;

	Color borderColor = new Color(0, 102, 255);
	Color backgroundcolor = new Color(195, 219, 255);

	public PeterSwingCanvas(mxGraphComponent graphComponent) {
		this.graphComponent = graphComponent;

		jLabel.setBorder(BorderFactory.createLineBorder(borderColor));
		jLabel.setHorizontalAlignment(JLabel.CENTER);
		jLabel.setBackground(backgroundcolor);
		jLabel.setOpaque(true);
		jLabel.setFont(new Font("arial", Font.PLAIN, 10));
	}

	public void drawVertex(mxCellState state, String label) {
		jLabel.setText(label);
		rendererPane.paintComponent(g, jLabel, graphComponent, (int) state.getX() + translate.x, (int) state.getY() + translate.y, (int) state.getWidth(), (int) state.getHeight(),
				true);
	}

}