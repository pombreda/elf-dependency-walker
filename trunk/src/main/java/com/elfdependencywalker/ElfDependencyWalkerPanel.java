package com.elfdependencywalker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.RepaintManager;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

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
import com.mxgraph.view.mxGraph;
import com.peterswing.CommonLib;
import com.peterswing.advancedswing.jdropdownbutton.JDropDownButton;
import com.peterswing.advancedswing.jprogressbardialog.JProgressBarDialog;

public class ElfDependencyWalkerPanel extends javax.swing.JPanel implements Printable {
	File files[] = null;
	private JTabbedPane tabbedPane1;
	private JPanel treePanel;
	private JScrollPane scrollPane1;
	private JScrollPane scrollPane2;
	private JDropDownButton layoutButton;
	private JButton anaystDirectoryButton;
	private JSplitPane graphSplitPane;
	private JButton printButton;
	private JDropDownButton analystButton;
	private JToolBar toolBar1;
	private JEditorPane textArea1;
	private JTree tree1;
	public JSplitPane splitPane1;
	private JButton saveToPngButton;
	private JCheckBox filterNoChildNodejCheckBox;
	private JButton dotButton;
	private JButton zoom100Button;
	private JButton zoomOutButton;
	private JButton zoomInButton;
	private JButton settingButton;
	private JPanel panel1;
	private JPanel callGraphPreviewPanel = new JPanel();
	private MyTreeModel myTreeModel = new MyTreeModel(null);
	final JEditorPane lines = new JEditorPane();
	mxGraph graph;
	CallGraphComponent graphComponent;
	mxGraphOutline graphOutline;
	JProgressBar jStatusProgressBar = new JProgressBar();
	AnalystDialog dialog;
	Object parent;
	JFrame jframe;
	int x = 0;
	Hashtable<String, mxCell> allNodes = new Hashtable<String, mxCell>();
	Hashtable<String, Color> allNodesEdgeColor = new Hashtable<String, Color>();
	Hashtable<Integer, Color> allNodesColor = new Hashtable<Integer, Color>();
	Random numGen = new Random();
	private JPanel dotPanel;
	private JScrollPane scrollPane;
	private JLabel dotLabel;
	private JPanel panel_1;
	private JButton btnSavePng;
	Vector<String> allEdges = new Vector<String>();
	private JButton buttonZoomIn;
	private JButton buttonZoomOut;
	private JButton btnExportCsvFor;
	Vector<String> finishedDotNodes = new Vector<String>();
	private int preferWidth;
	private int preferHeight;
	private JButton button;
	protected boolean mousePressed;
	protected int imageX;
	protected int imageY;
	protected int lastX;
	protected int lastY;
	private JCheckBox chckbxEdge;
	private JCheckBox chckbxOrtho;
	private JSpinner maxLevelSpinner;
	private JLabel lblMaxLevel;
	private JLabel lblMaxNodePer;
	private JSpinner maxNodePerLevelSpinner;
	private JCheckBox colorCheckBox;
	private JPanel graphPanel;
	private JPanel panel_3;
	private JCheckBox oneLevelDownwardOnlyCheckBox;

	public ElfDependencyWalkerPanel(JFrame jframe) {
		super();
		this.jframe = jframe;
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);

			tabbedPane1 = new JTabbedPane();
			this.add(tabbedPane1, BorderLayout.CENTER);

			treePanel = new JPanel();
			BorderLayout jTreePanelLayout = new BorderLayout();
			treePanel.setLayout(jTreePanelLayout);
			tabbedPane1.addTab("Tree", null, treePanel, null);

			splitPane1 = new JSplitPane();
			treePanel.add(splitPane1, BorderLayout.CENTER);

			scrollPane1 = new JScrollPane();
			splitPane1.add(scrollPane1, JSplitPane.LEFT);
			scrollPane1.setPreferredSize(new java.awt.Dimension(79, 541));

			tree1 = new JTree(myTreeModel);
			tree1.setShowsRootHandles(true);
			tree1.setCellRenderer(new MyTreeRenderer());
			scrollPane1.setViewportView(tree1);
			tree1.addTreeSelectionListener(new TreeSelectionListener() {
				public void valueChanged(TreeSelectionEvent evt) {
					tree1ValueChanged(evt);
				}
			});

			scrollPane2 = new JScrollPane();
			splitPane1.add(scrollPane2, JSplitPane.RIGHT);
			scrollPane2.setPreferredSize(new java.awt.Dimension(68, 541));

			textArea1 = new JEditorPane();
			scrollPane2.setViewportView(textArea1);
			updateLine();
			lines.setBackground(new Color(230, 230, 230));
			lines.setEditable(false);
			scrollPane2.setRowHeaderView(lines);

			toolBar1 = new JToolBar();
			this.add(toolBar1, BorderLayout.NORTH);

			analystButton = new JDropDownButton();
			toolBar1.add(analystButton);
			analystButton.setText("Analyst ");
			analystButton.setMaximumSize(new java.awt.Dimension(100, 28));
			analystButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/famfam_icons/folder_page.png")));
			analystButton.setPreferredSize(new java.awt.Dimension(100, 28));
			analystButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					analystButtonActionPerformed(evt);
				}
			});
			addHistoryMenuitems();

			anaystDirectoryButton = new JButton();
			toolBar1.add(anaystDirectoryButton);
			anaystDirectoryButton.setText("Analyst Directory");
			anaystDirectoryButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/famfam_icons/folder.png")));
			anaystDirectoryButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					anaystDirectoryButtonActionPerformed(evt);
				}
			});

			printButton = new JButton();
			toolBar1.add(printButton);
			printButton.setText("Print");
			printButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/famfam_icons/printer.png")));
			printButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					printButtonActionPerformed(evt);
				}
			});

			layoutButton = new JDropDownButton();
			toolBar1.add(layoutButton);
			layoutButton.setMaximumSize(new java.awt.Dimension(180, 28));
			layoutButton.setText("Hierarchical Layout");
			layoutButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/famfam_icons/cake.png")));
			layoutButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					layoutButtonActionPerformed(evt);
				}
			});

			settingButton = new JButton();
			toolBar1.add(settingButton);
			settingButton.setText("Setting");
			settingButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					settingButtonActionPerformed(evt);
				}
			});

			btnExportCsvFor = new JButton("Export CSV for Gephi");
			btnExportCsvFor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					final JFileChooser fc = new JFileChooser(Setting.getInstance().lastOpenPath);
					int returnVal = fc.showSaveDialog(ElfDependencyWalkerPanel.this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file;
						if (!fc.getSelectedFile().getName().endsWith(".csv")) {
							file = new File(fc.getSelectedFile().getAbsolutePath() + ".csv");
						} else {
							file = fc.getSelectedFile();
						}
						try {
							BufferedWriter of = new BufferedWriter(new FileWriter(file));
							of.write("Source,Target,Type,Id,Label,Weight\n");
							genGephiCSV(of, (ELFNode) myTreeModel.getRoot(), 1, new HashSet<String>());
							of.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			});
			toolBar1.add(btnExportCsvFor);

			int x = Setting.getInstance().x;
			int y = Setting.getInstance().y;
			setLocation(x, y);
			this.setPreferredSize(new Dimension(1202, 665));

			setSize(Setting.getInstance().width, Setting.getInstance().height);

			addLayoutMenuitems();
			splitPane1.setDividerLocation(Setting.getInstance().divX);

			dotPanel = new JPanel();
			tabbedPane1.addTab("Dot", null, dotPanel, null);
			dotPanel.setLayout(new BorderLayout(0, 0));

			scrollPane = new JScrollPane();
			dotPanel.add(scrollPane, BorderLayout.CENTER);

			dotLabel = new JLabel("");
			dotLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					lastX = e.getX();
					lastY = e.getY();
					//								mousePressed = true;
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					//								mousePressed = false;
					//								imageX = e.getX();
					//								imageY = e.getY();
					//								int max = scrollPane.getHorizontalScrollBar().getMaximum();
					////								if (imageX - lastX < 0) {
					//									scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getValue() + (lastX - imageX));
					////								} else if (imageX - lastX < 0) {
					////									scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getValue() - (lastX - imageX));
					////								}
				}
			});
			dotLabel.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					imageX = e.getX();
					imageY = e.getY();
					//					int max = scrollPane.getHorizontalScrollBar().getMaximum();
					//								if (imageX - lastX < 0) {
					scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getValue() + (lastX - imageX));
					//								} else if (imageX - lastX < 0) {
					//									scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getValue() - (lastX - imageX));
					//								}
					scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getValue() + (lastY - imageY));
				}
			});
			scrollPane.setViewportView(dotLabel);

			panel_1 = new JPanel();
			dotPanel.add(panel_1, BorderLayout.NORTH);

			btnSavePng = new JButton("Save png");
			btnSavePng.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					saveDotToPng();
				}
			});

			colorCheckBox = new JCheckBox("Color");
			colorCheckBox.setSelected(true);
			panel_1.add(colorCheckBox);

			filterNoChildNodejCheckBox = new JCheckBox();
			panel_1.add(filterNoChildNodejCheckBox);
			filterNoChildNodejCheckBox.setSelected(true);
			filterNoChildNodejCheckBox.setText("filter no child node");

			chckbxEdge = new JCheckBox("Edge");
			panel_1.add(chckbxEdge);
			chckbxEdge.setSelected(true);

			chckbxOrtho = new JCheckBox("Ortho", true);
			panel_1.add(chckbxOrtho);

			oneLevelDownwardOnlyCheckBox = new JCheckBox("Connect to one level downward only");
			panel_1.add(oneLevelDownwardOnlyCheckBox);

			lblMaxLevel = new JLabel(", Max level:");
			panel_1.add(lblMaxLevel);

			maxLevelSpinner = new JSpinner();
			panel_1.add(maxLevelSpinner);
			maxLevelSpinner.setModel(new SpinnerNumberModel(100, 0, 100, 1));
			maxLevelSpinner.setMaximumSize(new Dimension(50, 18));

			lblMaxNodePer = new JLabel("Max node per level:");
			panel_1.add(lblMaxNodePer);

			maxNodePerLevelSpinner = new JSpinner();
			panel_1.add(maxNodePerLevelSpinner);
			maxNodePerLevelSpinner.setModel(new SpinnerNumberModel(10, 1, 100, 1));
			maxNodePerLevelSpinner.setMaximumSize(new Dimension(50, 18));

			dotButton = new JButton();
			panel_1.add(dotButton);
			dotButton.setText("dot");

			dotButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					dotButtonActionPerformed(evt);
				}
			});
			panel_1.add(btnSavePng);

			buttonZoomIn = new JButton("+");
			buttonZoomIn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ImageIcon icon = new ImageIcon(Global.filename);
					icon.getImage().flush();
					preferWidth = (int) (((float) preferWidth) * 1.1);
					preferHeight = (int) (((float) preferHeight) * 1.1);
					dotLabel.setIcon(resizeImage(icon, preferWidth, preferHeight));
				}
			});
			panel_1.add(buttonZoomIn);

			buttonZoomOut = new JButton("-");
			buttonZoomOut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ImageIcon icon = new ImageIcon(Global.filename);
					icon.getImage().flush();
					preferWidth = (int) (((float) preferWidth) * 0.9);
					preferHeight = (int) (((float) preferHeight) * 0.9);
					dotLabel.setIcon(resizeImage(icon, preferWidth, preferHeight));
				}
			});
			panel_1.add(buttonZoomOut);

			button = new JButton("100%");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ImageIcon icon = new ImageIcon(Global.filename);
					icon.getImage().flush();
					preferWidth = icon.getIconWidth();
					preferHeight = icon.getIconHeight();
					dotLabel.setIcon(resizeImage(icon, icon.getIconWidth(), icon.getIconHeight()));
				}
			});
			panel_1.add(button);

			graphPanel = new JPanel();
			tabbedPane1.addTab("Graph", null, graphPanel, null);
			graphPanel.setLayout(new BorderLayout(0, 0));

			graphSplitPane = new JSplitPane();
			graphPanel.add(graphSplitPane, BorderLayout.CENTER);

			panel_3 = new JPanel();
			graphPanel.add(panel_3, BorderLayout.NORTH);

			zoomInButton = new JButton();
			panel_3.add(zoomInButton);
			zoomInButton.setText("Zoom in");

			zoomOutButton = new JButton();
			panel_3.add(zoomOutButton);
			zoomOutButton.setText("Zoom out");

			zoom100Button = new JButton();
			panel_3.add(zoom100Button);
			zoom100Button.setText("100%");
			zoom100Button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					zoom100ButtonActionPerformed(evt);
				}
			});
			zoomOutButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					zoomOutButtonActionPerformed(evt);
				}
			});
			zoomInButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					zoomInButtonActionPerformed(evt);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void saveDotToPng() {
		final JFileChooser fc = new JFileChooser(Setting.getInstance().lastOpenPath);
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			saveImage(new ImageIcon(Global.filename).getImage(), fc.getSelectedFile());
		}
	}

	public boolean updateJGraphx(MyTreeModel model) {
		final JProgressBarDialog d = new JProgressBarDialog(jframe, "Updating tree...", true);
		d.jProgressBar.setIndeterminate(true);
		d.jProgressBar.setStringPainted(true);
		Thread longRunningThread = new Thread() {
			public void run() {

				graph = new mxGraph() {
					/*public void drawState(mxICanvas canvas, mxCellState state, String label) {
						if (getModel().isVertex(state.getCell()) && canvas instanceof PeterSwingCanvas) {
							PeterSwingCanvas c = (PeterSwingCanvas) canvas;
							c.drawVertex(state, label);
						} else {
							// draw edge, at least
							//					super.drawState(canvas, state, label);
						}
					}*/

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
				Iterator<ELFNode> ir = ((ELFNode) myTreeModel.getRoot()).child.iterator();
				while (ir.hasNext()) {
					ELFNode n = ir.next();
					addCells(parent, n, null);
					d.jProgressBar.setString(n.getFile().getName());
				}

				graph.setCellsDisconnectable(false);
				graphComponent = new CallGraphComponent(graph);
				graphSplitPane.add(graphComponent, JSplitPane.RIGHT);
				{
					panel1 = new JPanel();
					BoxLayout jPanel1Layout = new BoxLayout(panel1, javax.swing.BoxLayout.Y_AXIS);
					panel1.setLayout(jPanel1Layout);
					graphSplitPane.add(panel1, JSplitPane.LEFT);
					graphSplitPane.setDividerLocation(250);
					panel1.setPreferredSize(new java.awt.Dimension(100, 535));
					panel1.add(callGraphPreviewPanel);
				}

				graphOutline = new mxGraphOutline(graphComponent);
				graphOutline.setBorder(new LineBorder(Color.LIGHT_GRAY));

				BorderLayout jCallGraphPreviewPanelLayout = new BorderLayout();
				callGraphPreviewPanel.setLayout(jCallGraphPreviewPanelLayout);
				callGraphPreviewPanel.removeAll();
				callGraphPreviewPanel.add(graphOutline, BorderLayout.CENTER);
				callGraphPreviewPanel.setPreferredSize(new Dimension(100, 100));
				{
					saveToPngButton = new JButton();
					panel1.add(saveToPngButton);
					saveToPngButton.setText("Save png");
					saveToPngButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/famfam_icons/disk.png")));
					saveToPngButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							saveToPngButtonActionPerformed(evt);
						}
					});
				}
			}
		};
		d.thread = longRunningThread;
		d.setVisible(true);

		try {
			d.thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}

	Color getRandomColor() {
		Color color = new Color(numGen.nextInt(100) + 156, numGen.nextInt(100) + 156, numGen.nextInt(100) + 156);
		return color;
	}

	Color getEdgeColor(String edgeIDStr) {
		Color hexStr = allNodesEdgeColor.get(edgeIDStr);
		if (hexStr == null) {
			hexStr = getRandomColor();
			allNodesEdgeColor.put(edgeIDStr, hexStr);
		}
		return hexStr;
	}

	private void addCells(Object parent, ELFNode node, mxCell lastVertex) {
		try {
			if (allNodes.get(node.getFile().getName()) == null) {
				mxCell newNode;
				String name = node.getFile().getName();
				if (name.toLowerCase().contains(".a.") || name.toLowerCase().contains(".so.") || name.toLowerCase().contains(".a")) {
					newNode = (mxCell) graph.insertVertex(parent, null, name, 100, x * 40 + 100, 150, 30, mxConstants.STYLE_FILLCOLOR + "=#fff29b");
				} else {
					newNode = (mxCell) graph.insertVertex(parent, null, name, 100, x * 40 + 100, 150, 30);
				}
				allNodes.put(node.getFile().getName(), newNode);
				LinkedHashSet<ELFNode> childNode = node.child;
				Iterator<ELFNode> ir = childNode.iterator();
				x++;

				if (lastVertex != null && lastVertex != newNode) {
					String hexStr = Integer.toHexString(getEdgeColor(lastVertex.getValue().toString()).getRGB()).substring(2);
					graph.insertEdge(parent, null, "", lastVertex, newNode, mxConstants.STYLE_STROKECOLOR + "=#" + hexStr + ";edgeStyle=elbowEdgeStyle;");
				}
				while (ir.hasNext()) {
					ELFNode n = ir.next();
					addCells(parent, n, newNode);
				}

			} else {
				if (lastVertex != null && lastVertex != allNodes.get(node.getFile().getName())) {
					String hexStr = Integer.toHexString(getEdgeColor(lastVertex.getValue().toString()).getRGB()).substring(2);
					graph.insertEdge(parent, null, "", lastVertex, allNodes.get(node.getFile().getName()), mxConstants.STYLE_STROKECOLOR + "=#" + hexStr
							+ ";edgeStyle=elbowEdgeStyle;");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
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
	*/

	private void analystButtonActionPerformed(ActionEvent evt) {
		if (analystButton.getEventSource() == null) {
			final JFileChooser fc = new JFileChooser(Setting.getInstance().lastOpenPath);
			fc.setMultiSelectionEnabled(true);
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				files = fc.getSelectedFiles();
				Setting.getInstance().lastOpenPath = files[0].getAbsolutePath();
				Setting.getInstance().save();
			}
		} else {
			files = new File[1];
			files[0] = new File(((JMenuItem) analystButton.getEventSource()).getText());
		}
		if (files != null) {
			if (files.length == 1) {
				Setting.getInstance().historyList.add(files[0].getAbsolutePath());
				addHistoryMenuitems();
				Setting.getInstance().historyList.add(files[0].getAbsolutePath());
				Setting.getInstance().lastOpenPath = files[0].getParentFile().getAbsolutePath();
			}
			dialog = new AnalystDialog(jframe, tree1, files);
			dialog.setVisible(true);
			boolean success = updateJGraphx(myTreeModel);
			if (success) {
				dotButtonActionPerformed(null);
			}
		}
	}

	private void addHistoryMenuitems() {
		analystButton.removeAll();
		LinkedHashSet<String> historyVector = Setting.getInstance().historyList;
		for (String str : historyVector) {
			if (new File(str).exists()) {
				analystButton.insert(new JMenuItem(str), 0);
			}
		}
	}

	private void addLayoutMenuitems() {
		layoutButton.removeAll();
		layoutButton.add(new JMenuItem("Hierarchical Layout"));
		layoutButton.add(new JMenuItem("Circle Layout"));
		layoutButton.add(new JMenuItem("Organic Layout"));
		layoutButton.add(new JMenuItem("Compact Tree Layout"));
		layoutButton.add(new JMenuItem("Edge Label Layout"));
		layoutButton.add(new JMenuItem("Fast Organic Layout"));
		layoutButton.add(new JMenuItem("Orthogonal Layout"));
		layoutButton.add(new JMenuItem("Parallel Edge Layout"));
		layoutButton.add(new JMenuItem("Stack Layout"));
	}

	private void tree1ValueChanged(TreeSelectionEvent evt) {
		try {
			ELFNode node = (ELFNode) tree1.getLastSelectedPathComponent();
			textArea1.setContentType("text/html");
			if (node.getNmResult() == null && !Global.isMac) {
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

			textArea1.setText(node.getNmResult());
			textArea1.setCaretPosition(0);

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

	private void printButtonActionPerformed(ActionEvent evt) {
		PrintUtilities.printComponent(textArea1);
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if (pageIndex > 0) {
			return (NO_SUCH_PAGE);
		} else {
			Graphics2D g2d = (Graphics2D) graphics;
			g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
			disableDoubleBuffering(textArea1);
			textArea1.paint(g2d);
			enableDoubleBuffering(textArea1);
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

	private void anaystDirectoryButtonActionPerformed(ActionEvent evt) {
		final JFileChooser fc = new JFileChooser(Setting.getInstance().lastOpenPath);
		fc.setMultiSelectionEnabled(true);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			Setting.getInstance().lastOpenPath = fc.getSelectedFile().getAbsolutePath();
			AnalystDialog d = new AnalystDialog(jframe, tree1, fc.getSelectedFiles());
			d.setVisible(true);
			updateJGraphx(myTreeModel);
			dotButtonActionPerformed(null);
		}
	}

	private void layoutButtonActionPerformed(ActionEvent evt) {
		if (parent != null) {
			final mxGraph graph = graphComponent.getGraph();
			Object cell = graph.getSelectionCell();

			if (cell == null || graph.getModel().getChildCount(cell) == 0) {
				cell = graph.getDefaultParent();
			}
			graph.getModel().beginUpdate();

			String str;
			if (layoutButton.getEventSource() == null) {
				str = "Hierarchical Layout";
			} else {
				str = ((JMenuItem) layoutButton.getEventSource()).getText();
			}
			layoutButton.setText(str);
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
			tabbedPane1.setSelectedIndex(2);
		}
	}

	public static BufferedImage imageToBufferedImage(Image im) {
		return imageToBufferedImage(im, 0, 0);
	}

	public static BufferedImage imageToBufferedImage(Image im, int x, int y) {
		BufferedImage bi = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics bg = bi.getGraphics();
		bg.drawImage(im, x, y, null);
		bg.dispose();
		return bi;
	}

	public static boolean saveImage(Image image, File file) {
		try {
			if (file.getName().endsWith(".png")) {
				ImageIO.write(imageToBufferedImage(image), "PNG", file);
			} else {
				ImageIO.write(imageToBufferedImage(image), "PNG", new File(file.getAbsolutePath() + ".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
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

	private void saveToPngButtonActionPerformed(ActionEvent evt) {
		final JFileChooser fc = new JFileChooser(Setting.getInstance().lastOpenPath);
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file;
			System.out.println(fc.getSelectedFile().getName());
			if (fc.getSelectedFile().getName().endsWith(".png")) {
				file = fc.getSelectedFile();
			} else {
				file = new File(fc.getSelectedFile().getAbsolutePath() + ".png");
			}
			saveImage(this.graphComponent.getGraphControl(), file);
		}
	}

	private void settingButtonActionPerformed(ActionEvent evt) {
		SettingDialog settingDialog = new SettingDialog(jframe, true);
		settingDialog.setLocationRelativeTo(null);
		settingDialog.setVisible(true);
	}

	private void zoomInButtonActionPerformed(ActionEvent evt) {
		if (graphComponent != null) {
			graphComponent.zoomIn();
		}
	}

	private void zoomOutButtonActionPerformed(ActionEvent evt) {
		if (graphComponent != null) {
			graphComponent.zoomOut();
		}
	}

	private void zoom100ButtonActionPerformed(ActionEvent evt) {
		if (graphComponent != null) {
			graphComponent.zoomActual();
		}
	}

	private void dotButtonActionPerformed(ActionEvent evt) {
		final JProgressBarDialog d = new JProgressBarDialog(jframe, "Generating dot...", true);
		d.jProgressBar.setIndeterminate(true);
		d.jProgressBar.setStringPainted(true);

		Thread longRunningThread = new Thread() {
			public void run() {
				try {
					LinkedHashSet<String> allLevelNames = new LinkedHashSet<String>();
					allEdges.clear();
					finishedDotNodes.clear();
					allNodesEdgeColor.clear();
					allNodesColor.clear();
					File file = new File("elf.dot");
					BufferedWriter of = new BufferedWriter(new FileWriter(file));
					of.write("digraph \"" + Arrays.toString(files) + "\"{\n");
					of.write("\tmargin = 0.7;\n");
					of.write("\tfontname = Verdana;\n");
					of.write("\tfontsize = 12;\n");
					of.write("\tfontsize = 12;\n");
					if (chckbxOrtho.isSelected()) {
						of.write("\tsplines=ortho;\n");
					}
					of.write("\tnodesep=0.15;\n");
					of.write("\tranksep=0.15;\n");
					of.write("\tnode [shape=box, margin=0.04, shape=box, fontname=\"Ubuntu-M\", fontsize = 12, width=0.2, height=0.1];\n");

					((ELFNode) myTreeModel.getRoot()).setProcessed(false);
					int maxDepthOfTree = getMaxDepth((ELFNode) myTreeModel.getRoot());

					//rank
					for (int x = maxDepthOfTree; x > 0; x--) {
						int level = maxDepthOfTree - x;
						if (level > (Integer) maxLevelSpinner.getValue()) {
							continue;
						}
						d.jProgressBar.setString("Level " + level);
						Vector<ELFNode> nodesInLevel = new Vector<ELFNode>();
						((ELFNode) myTreeModel.getRoot()).setProcessed(false);
						getNodesInLevel(nodesInLevel, (ELFNode) myTreeModel.getRoot(), x, maxDepthOfTree, filterNoChildNodejCheckBox.isSelected());

						int maxNode = (int) maxNodePerLevelSpinner.getValue();
						for (int l = 0; l < Math.ceil((float) nodesInLevel.size() / maxNode); l++) {
							String levelName = "\"Level " + level + "." + l + "\"";
							if (level <= (Integer) maxLevelSpinner.getValue()) {
								allLevelNames.add(levelName);
							}
							String tempStr = "\t{\n\t\trank=same;\"Level " + level + "." + l + "\";";
							boolean newline = false;
							boolean hasAtLeastOneNodeInLevel = false;
							//							System.out.println("==" + l * maxNode);
							int noOfMatch = 0;
							for (int y = l * maxNode; y < (l + 1) * maxNode && y < nodesInLevel.size(); y++) {
								//								System.out.print(y + ",");
								ELFNode node = nodesInLevel.get(y);
								//								if (filterNoChildNodejCheckBox.isSelected() && node.getChildCount() == 0 && node.getParent().getParent() == null) {
								//									continue;
								//								}
								if ((maxDepthOfTree - node.getLevel()) <= (Integer) maxLevelSpinner.getValue() && !node.file.getName().equals("Peter")) {
									if (y > 0 && !newline && hasAtLeastOneNodeInLevel) {
										tempStr += " ; ";
									}
									hasAtLeastOneNodeInLevel = true;
									tempStr += "\"" + node + "\"";
									noOfMatch++;
								}
							}
							if (noOfMatch == 0) {
								allLevelNames.remove(levelName);
							}
							//							System.out.println();

							tempStr += "\n\t}\n";
							if (hasAtLeastOneNodeInLevel) {
								of.write(tempStr);
							}
						}
					}
					//end rank

					addDotCells(of, (ELFNode) myTreeModel.getRoot(), chckbxEdge.isSelected(), maxDepthOfTree, d, new Vector<String>());

					of.write("\t{\n\t");
					//of.write("\t\tnode[shape=box fontsize=8 width=0.2 height=0.1];\n");
					for (int x = allLevelNames.size() - 1; x >= 0; x--) {
						if (x < allLevelNames.size() - 1) {
							of.write(" -> ");
						}
						of.write(allLevelNames.toArray()[x].toString());
					}
					of.write("\n\t}\n");
					of.write("}\n"); //end graph
					of.close();

					new File("elf.png").delete();
					d.jProgressBar.setString("running dot command : " + "dot -Tpng " + file.getName() + " -o elf.png");
					if (Global.isMac) {
						CommonLib.runCommand("/opt/local/bin/dot -Tpng " + file.getName() + " -o elf.png");
					} else {
						CommonLib.runCommand("dot -Tpng " + file.getName() + " -o elf.png");
					}

					addText(new File(Global.filename));

					ImageIcon icon = new ImageIcon(Global.filename);
					icon.getImage().flush();

					preferWidth = dotLabel.getWidth() > icon.getIconWidth() ? icon.getIconWidth() : dotLabel.getWidth();
					float ratio = ((float) preferWidth) / icon.getIconWidth();
					if (ratio == 0) {
						ratio = 1;
					}
					preferHeight = (int) (icon.getIconHeight() * ratio);
					if (preferHeight == 0) {
						return;
					}
					ImageIcon resizedIcon = resizeImage(icon, preferWidth, preferHeight);
					dotLabel.setIcon(resizedIcon);
					tabbedPane1.setSelectedIndex(1);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			private void addText(File file) {
				try {
					BufferedImage image = ImageIO.read(file);
					Graphics g = image.getGraphics();
					g.setFont(new Font("Verdana", Font.PLAIN, 12));
					g.setColor(Color.white);
					g.fillRect(0, 0, 200, 60);
					g.setColor(Color.black);
					g.drawString(System.getProperty("os.name") + " " + System.getProperty("os.version") + " " + System.getProperty("os.arch"), 10, 30);
					g.drawString(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()), 10, 50);
					g.dispose();
					ImageIO.write(image, "png", file);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
		d.thread = longRunningThread;
		d.setVisible(true);

	}

	ImageIcon resizeImage(ImageIcon icon, int width, int height) {
		return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	/*
	private void getAllNodes(Vector<ELFNode> allNodes, ELFNode node) {
		if (allNodes.contains(node)) {
			return;
		}
		allNodes.add(node);
		Iterator<ELFNode> ir = node.child.iterator();
		while (ir.hasNext()) {
			ELFNode childNode = ir.next();
			getAllNodes(allNodes, childNode);
		}
	}
	*/

	//	private void getNodesInLevel(Vector<ELFNode> nodesInLevel, ELFNode node, int level) {
	//		Vector<ELFNode> allNodes = new Vector<ELFNode>();
	//		getAllNodes(allNodes, (ELFNode) myTreeModel.getRoot());
	//		Collections.sort(allNodes);
	//		ELFNode lastNode = null;
	//		int maxDepth = -9999;
	//		for (ELFNode n : allNodes) {
	//			if (lastNode != null && !n.file.getName().equals(lastNode.file.getName())) {
	//				if (maxDepth == level) {
	//					//					Global.debug(lastNode.file.getName() + " == " + level);
	//					nodesInLevel.addElement(lastNode);
	//				}
	//				maxDepth = n.getLevel();
	//			} else {
	//				if (n.getLevel() > maxDepth) {
	//					maxDepth = n.getLevel();
	//				}
	//
	//				//				Global.debug("   " + n.file.getName() + " = " + n.getLevel());
	//			}
	//			lastNode = n;
	//		}
	//	}

	private void getNodesInLevel(Vector<ELFNode> nodesInLevel, ELFNode node, int level, int maxDepthOfTree, boolean filterNoChildNode) {
		if (node.processed) {
			return;
		}
		node.processed = true;
		if ((node.getLevel() == maxDepthOfTree && maxDepthOfTree == level) || (node.getLevel() == level && (!filterNoChildNode || node.getChildCount() > 0))) {
			nodesInLevel.add(node);
		}
		Iterator<ELFNode> ir = node.child.iterator();
		while (ir.hasNext()) {
			ELFNode tempNode = ir.next();
			getNodesInLevel(nodesInLevel, tempNode, level, maxDepthOfTree, filterNoChildNode);
		}
	}

	private int getMaxDepth(ELFNode node) {
		if (node == null || node.processed) {
			return 0;
		}
		node.processed = true;
		int maxChildDepth = node.getLevel();
		Iterator<ELFNode> ir = node.child.iterator();
		while (ir.hasNext()) {
			ELFNode childNode = ir.next();
			if (childNode.getLevel() > node.getLevel()) {
				int childLevel = getMaxDepth(childNode);
				if (childLevel > maxChildDepth) {
					maxChildDepth = childLevel;
				}
			}
		}
		return maxChildDepth;
	}

	private void addDotCells(BufferedWriter writer, ELFNode node, boolean hasEdge, int maxDepthOfTree, JProgressBarDialog d, Vector<String> parsed) throws IOException {
		if (parsed.contains(node.getFile().getName())) {
			return;
		}
		if (filterNoChildNodejCheckBox.isSelected() && node.getChildCount() == 0 && node.getParent().getParent() == null) {
			return;
		}
		Color color;
		if (colorCheckBox.isSelected()) {
			color = allNodesColor.get(node.getLevel());
			if (color == null) {
				color = getRandomColor();
				allNodesColor.put(node.getLevel(), color);
			}
		} else {
			color = Color.black;
		}
		if (!node.file.getName().equals("Peter") && !finishedDotNodes.contains(node.file.getName())) {
			if ((maxDepthOfTree - node.getLevel()) <= (Integer) maxLevelSpinner.getValue()) {
				writer.write("\t\"" + node + "\" [style=filled, fillcolor=\"#" + Integer.toHexString(color.getRGB()).substring(2) + "\"];\n");
				parsed.add(node.file.getName());
				finishedDotNodes.add(node.file.getName());
			}
		}

		Iterator<ELFNode> ir = node.child.iterator();
		while (ir.hasNext()) {
			ELFNode childNode = ir.next();
			if (hasEdge && !node.file.getName().equals("Peter") && !allEdges.contains(node.file.getName() + "\" -> \"" + childNode.file.getName())) {
				if ((maxDepthOfTree - node.getLevel()) <= (Integer) maxLevelSpinner.getValue() && (maxDepthOfTree - childNode.getLevel()) <= (Integer) maxLevelSpinner.getValue()) {
					if (node.getLevel() < childNode.getLevel()) {
						if (oneLevelDownwardOnlyCheckBox.isSelected() && childNode.getLevel() - node.getLevel() != 1) {
							continue;
						}
						d.jProgressBar.setString("\t\t\"" + node.file.getName() + "\" -> \"" + childNode.file.getName() + "\"");
						writer.write("\t\t\"" + node + "\" -> \"" + childNode + "\" [width=1, color=\"#" + Integer.toHexString(color.darker().getRGB()).substring(2)
								+ "\", arrowhead=none];\n");
						allEdges.add(node.file.getName() + "\" -> \"" + childNode.file.getName());
					}
				}
			}
			addDotCells(writer, childNode, hasEdge, maxDepthOfTree, d, parsed);
		}
	}

	protected void genGephiCSV(BufferedWriter of, ELFNode node, int id, HashSet<String> parsed) throws IOException {
		if (parsed.contains(node.file.getName())) {
			return;
		}
		parsed.add(node.file.getName());

		Iterator<ELFNode> ir = node.child.iterator();
		while (ir.hasNext()) {
			ELFNode childNode = ir.next();
			if (node.file.getName().equals("Peter")) {
				continue;
			}
			if (!node.file.getName().equals(childNode.file.getName())) {
				if ((filterNoChildNodejCheckBox.isSelected() && childNode.getChildCount() > 0) || !filterNoChildNodejCheckBox.isSelected()) {
					of.write(node.file.getName() + "," + childNode.file.getName() + ",Directed," + id + "," + node.file.getName() + "," + node.getLevel() + "\n");
				}
				id++;
			}
		}
		ir = node.child.iterator();
		while (ir.hasNext()) {
			ELFNode childNode = ir.next();
			genGephiCSV(of, childNode, id, parsed);
		}
	}
}
