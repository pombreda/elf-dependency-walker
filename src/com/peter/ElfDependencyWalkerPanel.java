package com.peter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxEdgeLabelLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.layout.mxStackLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.orthogonal.mxOrthogonalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphOutline;
import com.mxgraph.swing.util.mxMorphing;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.util.mxPoint;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import com.peterswing.CommonLib;
import com.peterswing.advancedswing.jdropdownbutton.JDropDownButton;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class ElfDependencyWalkerPanel extends javax.swing.JPanel implements Printable {
	private JTabbedPane jTabbedPane1;
	private JPanel jTreePanel;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JDropDownButton jLayoutButton;
	private JButton jAnaystDirectoryButton;
	private JSplitPane jGraphSplitPane;
	private JButton jPrintButton;
	private JDropDownButton jAnalystButton;
	private JToolBar jToolBar1;
	private JEditorPane jTextArea1;
	private JTree jTree1;
	private JSplitPane jSplitPane1;
	private JButton jSaveToPngButton;
	private JButton settingButton;
	private JPanel jPanel1;
	private JPanel jCallGraphPreviewPanel = new JPanel();
	private MyTreeModel myTreeModel = new MyTreeModel(null);
	final JEditorPane lines = new JEditorPane();
	mxGraph graph;
	CallGraphComponent graphComponent;
	mxGraphOutline graphOutline;
	JProgressBar jStatusProgressBar = new JProgressBar();
	JAnalystDialog dialog;
	Object parent;
	JFrame jframe;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ElfDependencyWalker inst = new ElfDependencyWalker();
				inst.setVisible(true);
			}
		});
	}

	public ElfDependencyWalkerPanel(JFrame jframe) {
		super();
		this.jframe = jframe;
		initGUI();
	}

	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			{
				jTabbedPane1 = new JTabbedPane();
				this.add(jTabbedPane1, BorderLayout.CENTER);
				{
					jTreePanel = new JPanel();
					BorderLayout jTreePanelLayout = new BorderLayout();
					jTreePanel.setLayout(jTreePanelLayout);
					jTabbedPane1.addTab("Tree", null, jTreePanel, null);
					{
						jSplitPane1 = new JSplitPane();
						jTreePanel.add(jSplitPane1, BorderLayout.CENTER);
						{
							jScrollPane1 = new JScrollPane();
							jSplitPane1.add(jScrollPane1, JSplitPane.LEFT);
							jScrollPane1.setPreferredSize(new java.awt.Dimension(79, 541));
							{
								jTree1 = new JTree(myTreeModel);
								jTree1.setShowsRootHandles(true);
								jTree1.setCellRenderer(new MyTreeRenderer());
								jScrollPane1.setViewportView(jTree1);
								jTree1.addTreeSelectionListener(new TreeSelectionListener() {
									public void valueChanged(TreeSelectionEvent evt) {
										jTree1ValueChanged(evt);
									}
								});
							}
						}
						{
							jScrollPane2 = new JScrollPane();
							jSplitPane1.add(jScrollPane2, JSplitPane.RIGHT);
							jScrollPane2.setPreferredSize(new java.awt.Dimension(68, 541));
							{
								jTextArea1 = new JEditorPane();
								jScrollPane2.setViewportView(jTextArea1);
								updateLine();
								lines.setBackground(new Color(230, 230, 230));
								lines.setEditable(false);
								jScrollPane2.setRowHeaderView(lines);

							}
						}
					}

				}
				{
					jGraphSplitPane = new JSplitPane();
					jTabbedPane1.addTab("Graph", null, jGraphSplitPane, null);
				}
			}
			{
				jToolBar1 = new JToolBar();
				this.add(jToolBar1, BorderLayout.NORTH);
				{
					jAnalystButton = new JDropDownButton();
					jToolBar1.add(jAnalystButton);
					jAnalystButton.setText("Analyst ");
					jAnalystButton.setMaximumSize(new java.awt.Dimension(100, 28));
					jAnalystButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/famfam_icons/folder_page.png")));
					jAnalystButton.setPreferredSize(new java.awt.Dimension(100, 28));
					jAnalystButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jAnalystButtonActionPerformed(evt);
						}
					});
					addHistoryMenuitems();
				}
				{
					jAnaystDirectoryButton = new JButton();
					jToolBar1.add(jAnaystDirectoryButton);
					jAnaystDirectoryButton.setText("Analyst Directory");
					jAnaystDirectoryButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/famfam_icons/folder.png")));
					jAnaystDirectoryButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jAnaystDirectoryButtonActionPerformed(evt);
						}
					});
				}
				{
					jPrintButton = new JButton();
					jToolBar1.add(jPrintButton);
					jPrintButton.setText("Print");
					jPrintButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/famfam_icons/printer.png")));
					jPrintButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jPrintButtonActionPerformed(evt);
						}
					});
				}
				{
					jLayoutButton = new JDropDownButton();
					jToolBar1.add(jLayoutButton);
					jLayoutButton.setMaximumSize(new java.awt.Dimension(180, 28));
					jLayoutButton.setText("Hierarchical Layout");
					jLayoutButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/famfam_icons/cake.png")));
					jLayoutButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jLayoutButtonActionPerformed(evt);
						}
					});
				}
				{
					settingButton = new JButton();
					jToolBar1.add(settingButton);
					settingButton.setText("Setting");
					settingButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							settingButtonActionPerformed(evt);
						}
					});
				}
			}
			int x = Setting.getInstance().getX();
			int y = Setting.getInstance().getY();
			setLocation(x, y);
			this.setPreferredSize(new java.awt.Dimension(679, 607));

			setSize(Setting.getInstance().getWidth(), Setting.getInstance().getHeight());

			addLayoutMenuitems();
			jSplitPane1.setDividerLocation(Setting.getInstance().getDivX());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateJGraphx(MyTreeModel model) {
		graph = new mxGraph() {
			public void drawState(mxICanvas canvas, mxCellState state, String label) {
				if (getModel().isVertex(state.getCell()) && canvas instanceof PeterSwingCanvas) {
					PeterSwingCanvas c = (PeterSwingCanvas) canvas;
					c.drawVertex(state, label);
				} else {
					// draw edge, at least
					super.drawState(canvas, state, label);
				}
			}

			// Ports are not used as terminals for edges, they are
			// only used to compute the graphical connection point

			public boolean isPort(Object cell) {
				mxGeometry geo = getCellGeometry(cell);

				return (geo != null) ? geo.isRelative() : false;
			}

			// Implements a tooltip that shows the actual
			// source and target of an edge
			public String getToolTipForCell(Object cell) {
				if (model.isEdge(cell)) {
					return convertValueToString(model.getTerminal(cell, true)) + " -> " + convertValueToString(model.getTerminal(cell, false));
				}

				return super.getToolTipForCell(cell);
			}

			public boolean isCellFoldable(Object cell, boolean collapse) {
				return false;
			}
		};

		parent = graph.getDefaultParent();
		allNodes.clear();
		addCells(parent, (ELFNode) myTreeModel.getRoot(), null);

		graph.setCellsDisconnectable(false);
		graphComponent = new CallGraphComponent(graph);
		// setMarkerMaxAndMinSize();
		// graphComponent.setGridVisible(true);
		// graphComponent.setGridColor(Color.lightGray);
		// graphComponent.setBackground(Color.white);
		// graphComponent.getViewport().setOpaque(false);
		// graphComponent.setBackground(Color.WHITE);
		// graphComponent.setConnectable(false);
		// graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
		// {
		// public void mouseReleased(MouseEvent e) {
		// Object cell = graphComponent.getCellAt(e.getX(), e.getY());
		//
		// if (cell != null) {
		// String label = graph.getLabel(cell);
		// if (label.contains("->")) {
		// cellClientEvent(label);
		// }
		// }
		// }
		// });

		// graph.setCellsResizable(false);
		// graph.setCellsMovable(false);
		// graph.setCellsEditable(false);
		// graph.foldCells(false);
		// graph.setGridSize(10);

		// mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
		// layout.execute(parent);

		// jGraphSplitPane.removeAll();
		jGraphSplitPane.add(graphComponent, JSplitPane.RIGHT);
		{
			jPanel1 = new JPanel();
			BoxLayout jPanel1Layout = new BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS);
			jPanel1.setLayout(jPanel1Layout);
			jGraphSplitPane.add(jPanel1, JSplitPane.LEFT);
			jPanel1.setPreferredSize(new java.awt.Dimension(100, 535));
			jPanel1.add(jCallGraphPreviewPanel);
		}

		graphOutline = new mxGraphOutline(graphComponent);
		graphOutline.setBorder(new LineBorder(Color.LIGHT_GRAY));

		BorderLayout jCallGraphPreviewPanelLayout = new BorderLayout();
		jCallGraphPreviewPanel.setLayout(jCallGraphPreviewPanelLayout);
		jCallGraphPreviewPanel.removeAll();
		jCallGraphPreviewPanel.add(graphOutline, BorderLayout.CENTER);
		jCallGraphPreviewPanel.setPreferredSize(new Dimension(100, 100));
		{
			jSaveToPngButton = new JButton();
			jPanel1.add(jSaveToPngButton);
			jSaveToPngButton.setText("Save to PNG");
			jSaveToPngButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/famfam_icons/disk.png")));
			jSaveToPngButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jSaveToPngButtonActionPerformed(evt);
				}
			});
		}

		// graph.getModel().beginUpdate();
		// mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
		// layout.execute(parent);
		// graph.getModel().endUpdate();
	}

	int x = 0;
	Hashtable<String, mxCell> allNodes = new Hashtable<String, mxCell>();
	Random numGen = new Random();

	private void addCells(Object parent, ELFNode node, Object lastVertex) {
		try {
			Color aColor = new Color(numGen.nextInt(256), numGen.nextInt(256), numGen.nextInt(256));
			String hexStr = Integer.toHexString(aColor.getRGB());
			mxCell newNode = (mxCell) graph.insertVertex(parent, null, node.getFile().getName(), 100, x * 40 + 100, 200, 30);

			allNodes.put(node.getFile().getName(), newNode);
			LinkedHashSet<ELFNode> childNode = node.child;
			Iterator<ELFNode> ir = childNode.iterator();
			x++;

			if (parent != null && lastVertex != null) {
				graph.insertEdge(parent, null, "", lastVertex, newNode, mxConstants.STYLE_STROKECOLOR + "=#" + hexStr + ";edgeStyle=elbowEdgeStyle;");
			}
			while (ir.hasNext()) {
				ELFNode n = ir.next();
				if (allNodes.get(n.getFile().getName()) == null) {
					addCells(parent, n, newNode);
				} else {
					try {
						graph.insertEdge(parent, null, "", newNode, allNodes.get(n.getFile().getName()), mxConstants.STYLE_STROKECOLOR + "=#" + hexStr
								+ ";edgeStyle=elbowEdgeStyle;");
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private mxCell[] addPort(mxCell node) {
		final int PORT_DIAMETER = 0;
		final int PORT_RADIUS = PORT_DIAMETER / 2;

		mxGeometry geo1 = new mxGeometry(0.5, 0, PORT_DIAMETER, PORT_DIAMETER);
		geo1.setOffset(new mxPoint(-PORT_RADIUS, -PORT_RADIUS));
		geo1.setRelative(true);

		mxCell port1 = new mxCell(null, geo1, "shape=ellipse;perimter=ellipsePerimeter");
		port1.setVertex(true);
		graph.addCell(port1, node);

		mxGeometry geo2 = new mxGeometry(0.5, 1, PORT_DIAMETER, PORT_DIAMETER);
		geo2.setOffset(new mxPoint(-PORT_RADIUS, -PORT_RADIUS));
		geo2.setRelative(true);

		mxCell port2 = new mxCell(null, geo2, "shape=ellipse;perimter=ellipsePerimeter");
		port2.setVertex(true);
		graph.addCell(port2, node);
		return new mxCell[] { port1, port2 };
	}

	private void setMarkerMaxAndMinSize() {
		graphComponent.markerOffset = 10;
		graphComponent.markerEnd = 10;
	}

	private void cellClientEvent(String label) {
		String str[] = label.split("->");
	}

	private void jAnalystButtonActionPerformed(ActionEvent evt) {
		File file = null;
		if (jAnalystButton.getEventSource() == null) {
			final JFileChooser fc = new JFileChooser(Setting.getInstance().getLastOpenPath());
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file = fc.getSelectedFile();
			}
		} else {
			file = new File(((JMenuItem) jAnalystButton.getEventSource()).getText());
		}
		if (file != null && file.exists()) {
			Setting.getInstance().addHistoryList(file.getAbsolutePath());
			addHistoryMenuitems();

			Setting.getInstance().getHistoryList().add(file.getAbsolutePath());
			Setting.getInstance().setLastOpenPath(file.getParentFile().getAbsolutePath());

			dialog = new JAnalystDialog(jframe, jTree1, file);
			dialog.setVisible(true);
			if (dialog.allNodes != null) {
				updateJGraphx(myTreeModel);
			}
		}
	}

	private void addHistoryMenuitems() {
		jAnalystButton.removeAll();
		LinkedHashSet<String> historyVector = Setting.getInstance().getHistoryList();
		for (String str : historyVector) {
			if (new File(str).exists()) {
				jAnalystButton.insert(new JMenuItem(str), 0);
			}
		}
	}

	private void addLayoutMenuitems() {
		jLayoutButton.removeAll();
		jLayoutButton.add(new JMenuItem("Hierarchical Layout"));
		jLayoutButton.add(new JMenuItem("Circle Layout"));
		jLayoutButton.add(new JMenuItem("Organic Layout"));
		jLayoutButton.add(new JMenuItem("Compact Tree Layout"));
		jLayoutButton.add(new JMenuItem("Edge Label Layout"));
		jLayoutButton.add(new JMenuItem("Fast Organic Layout"));
		jLayoutButton.add(new JMenuItem("Orthogonal Layout"));
		jLayoutButton.add(new JMenuItem("Parallel Edge Layout"));
		jLayoutButton.add(new JMenuItem("Stack Layout"));
	}

	private void thisWindowClosing(WindowEvent evt) {
		Setting.getInstance().setWidth(this.getWidth());
		Setting.getInstance().setHeight(this.getHeight());
		Setting.getInstance().setX(this.getLocation().x);
		Setting.getInstance().setY(this.getLocation().y);
		Setting.getInstance().setDivX(jSplitPane1.getDividerLocation());
		Setting.getInstance().save();
	}

	private void jTree1ValueChanged(TreeSelectionEvent evt) {
		try {
			ELFNode node = (ELFNode) jTree1.getLastSelectedPathComponent();
			jTextArea1.setContentType("text/html");
			if (node.getNmResult() == null) {
				File file = node.getFile();
				String results[] = clearHTML(CommonLib.runCommand("readelf -a " + file.getAbsolutePath())).split("\n\n");

				String colors[] = { "#000000", "#0000ff", "#ff0000", "#007700", "#ff00ff" };
				String result = "<html><body><strong>" + file.getAbsolutePath() + "</strong><br><pre>";

				for (int x = 1, count = 0; x < results.length; x++) {
					result += "\n\n<font color=\"" + colors[count] + "\">" + results[x] + "</font>";
					if (count < colors.length - 1) {
						count++;
					} else {
						count = 0;
					}

				}
				result += "</pre></body></html>";
				node.setNmResult(result);
			}

			jTextArea1.setText(node.getNmResult());
			jTextArea1.setCaretPosition(0);

			// updateLine();
		} catch (Exception ex) {

		}
	}

	private String clearHTML(String html) {
		return html.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	private void updateLine() {
		String text = "<html><body><pre>";
		for (int i = 1; i < 1000; i++) {
			if (i % 10 == 0) {
				text += "<font color=\"blue\"><strong>" + i + "</strong></font>\n";
			} else {
				text += i + "\n";
			}
		}
		text += "</pre></body></html>";
		lines.setContentType("text/html");
		lines.setText(text);
	}

	private void jPrintButtonActionPerformed(ActionEvent evt) {
		PrintUtilities.printComponent(jTextArea1);

		/*
		 * PrinterJob printJob = PrinterJob.getPrinterJob();
		 * printJob.setPrintable(this); if (printJob.printDialog()) try {
		 * printJob.print(); } catch (PrinterException pe) {
		 * System.out.println("Error printing: " + pe); }
		 */
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if (pageIndex > 0) {
			return (NO_SUCH_PAGE);
		} else {
			Graphics2D g2d = (Graphics2D) graphics;
			g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
			disableDoubleBuffering(jTextArea1);
			jTextArea1.paint(g2d);
			enableDoubleBuffering(jTextArea1);
			return (PAGE_EXISTS);
		}
	}

	public static void disableDoubleBuffering(Component c) {
		RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(false);
	}

	public static void enableDoubleBuffering(Component c) {
		RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(true);
	}

	private void jAnaystDirectoryButtonActionPerformed(ActionEvent evt) {
		final JFileChooser fc = new JFileChooser(Setting.getInstance().getLastOpenPath());
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			Setting.getInstance().setLastOpenPath(fc.getSelectedFile().getAbsolutePath());
			JAnalystDialog d = new JAnalystDialog(jframe, jTree1, fc.getSelectedFile());
			d.setVisible(true);
			updateJGraphx(myTreeModel);
		}
	}

	private void jLayoutButtonActionPerformed(ActionEvent evt) {
		if (parent != null) {
			final mxGraph graph = graphComponent.getGraph();
			Object cell = graph.getSelectionCell();

			if (cell == null || graph.getModel().getChildCount(cell) == 0) {
				cell = graph.getDefaultParent();
			}
			graph.getModel().beginUpdate();

			if (jLayoutButton.getEventSource() == null) {
				return;
			}
			String str = ((JMenuItem) jLayoutButton.getEventSource()).getText();
			jLayoutButton.setText(str);
			if (str.equals("Hierarchical Layout")) {
				mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
				layout.execute(cell);
			} else if (str.equals("Circle Layout")) {
				mxCircleLayout layout = new mxCircleLayout(graph);
				layout.setDisableEdgeStyle(false);
				layout.execute(cell);
			} else if (str.equals("Organic Layout")) {
				mxOrganicLayout layout = new mxOrganicLayout(graph);
				layout.execute(cell);
			} else if (str.equals("Compact Tree Layout")) {
				mxCompactTreeLayout layout = new mxCompactTreeLayout(graph, false);
				layout.execute(cell);
			} else if (str.equals("Edge Label Layout")) {
				mxEdgeLabelLayout layout = new mxEdgeLabelLayout(graph);
				layout.execute(cell);
			} else if (str.equals("Fast Organic Layout")) {
				mxFastOrganicLayout layout = new mxFastOrganicLayout(graph);
				layout.execute(cell);
			} else if (str.equals("Orthogonal Layout")) {
				mxOrthogonalLayout layout = new mxOrthogonalLayout(graph);
				layout.execute(cell);
			} else if (str.equals("Parallel Edge Layout")) {
				mxParallelEdgeLayout layout = new mxParallelEdgeLayout(graph);
				layout.execute(cell);
			} else if (str.equals("Stack Layout")) {
				mxStackLayout layout = new mxStackLayout(graph);
				layout.execute(cell);
			} else {
				System.out.println("no this layout");
			}

			mxMorphing morph = new mxMorphing(graphComponent, 20, 1.2, 20);

			morph.addListener(mxEvent.DONE, new mxIEventListener() {

				public void invoke(Object sender, mxEventObject evt) {
					graph.getModel().endUpdate();
				}

			});

			morph.startAnimation();
		}
	}

	public static boolean saveImage(Container container, File file) {
		final BufferedImage image = new BufferedImage(container.getWidth(), container.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics gr = image.getGraphics();
		container.printAll(gr);
		gr.dispose();
		try {
			ImageIO.write(image, "PNG", file);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void jSaveToPngButtonActionPerformed(ActionEvent evt) {
		final JFileChooser fc = new JFileChooser(Setting.getInstance().getLastOpenPath());
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			saveImage(this.graphComponent.getGraphControl(), fc.getSelectedFile());
		}

	}

	private void settingButtonActionPerformed(ActionEvent evt) {
		SettingDialog settingDialog = new SettingDialog(jframe, true);
		settingDialog.setLocationRelativeTo(null);
		settingDialog.setVisible(true);
	}
}
