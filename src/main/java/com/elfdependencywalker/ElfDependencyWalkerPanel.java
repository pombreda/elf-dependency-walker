package com.elfdependencywalker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
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
import java.util.Collections;
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
import com.peterswing.advancedswing.jprogressbardialog.JProgressBarDialog;

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
	private JCheckBox filterNoChildNodejCheckBox;
	private JButton dotButton;
	private JButton zoom100Button;
	private JButton zoomOutButton;
	private JButton zoomInButton;
	private JButton settingButton;
	private JPanel jPanel1;
	private JPanel jCallGraphPreviewPanel = new JPanel();
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
	Hashtable<String, String> allNodesEdgeColor = new Hashtable<String, String>();
	Random numGen = new Random();
	private JPanel panel;
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
				{
					zoomInButton = new JButton();
					jToolBar1.add(zoomInButton);
					zoomInButton.setText("Zoom in");
					zoomInButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							zoomInButtonActionPerformed(evt);
						}
					});
				}
				{
					zoomOutButton = new JButton();
					jToolBar1.add(zoomOutButton);
					zoomOutButton.setText("Zoom out");
					zoomOutButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							zoomOutButtonActionPerformed(evt);
						}
					});
				}
				{
					zoom100Button = new JButton();
					jToolBar1.add(zoom100Button);
					zoom100Button.setText("100%");
					zoom100Button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							zoom100ButtonActionPerformed(evt);
						}
					});
				}
				{
					filterNoChildNodejCheckBox = new JCheckBox();
					jToolBar1.add(filterNoChildNodejCheckBox);
					filterNoChildNodejCheckBox.setText("filter no child node");
				}
				{
					chckbxEdge = new JCheckBox("Edge");
					chckbxEdge.setSelected(true);
					jToolBar1.add(chckbxEdge);
				}
				{
					chckbxOrtho = new JCheckBox("Ortho");
					jToolBar1.add(chckbxOrtho);
				}
				{
					maxLevelSpinner = new JSpinner();
					maxLevelSpinner.setModel(new SpinnerNumberModel(100, 0, 100, 1));
					maxLevelSpinner.setMaximumSize(new Dimension(50, 18));
					jToolBar1.add(maxLevelSpinner);
				}
				{
					dotButton = new JButton();
					jToolBar1.add(dotButton);
					dotButton.setText("dot");
					{
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
										of.write("source,target\n");
										genGephiCSV(of, (ELFNode) myTreeModel.getRoot());
										of.close();
									} catch (Exception ex) {
										ex.printStackTrace();
									}
								}
							}
						});
						jToolBar1.add(btnExportCsvFor);
					}
					dotButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							dotButtonActionPerformed(evt);
						}
					});
				}
			}
			int x = Setting.getInstance().x;
			int y = Setting.getInstance().y;
			setLocation(x, y);
			this.setPreferredSize(new Dimension(916, 607));

			setSize(Setting.getInstance().width, Setting.getInstance().height);

			addLayoutMenuitems();
			jSplitPane1.setDividerLocation(Setting.getInstance().divX);
			{
				panel = new JPanel();
				jTabbedPane1.addTab("Dot", null, panel, null);
				panel.setLayout(new BorderLayout(0, 0));
				{
					scrollPane = new JScrollPane();
					panel.add(scrollPane, BorderLayout.CENTER);
					{
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
								int max = scrollPane.getHorizontalScrollBar().getMaximum();
								//								if (imageX - lastX < 0) {
								scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getValue() + (lastX - imageX));
								//								} else if (imageX - lastX < 0) {
								//									scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getValue() - (lastX - imageX));
								//								}
								scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getValue() + (lastY - imageY));
							}
						});
						scrollPane.setViewportView(dotLabel);
					}
				}
				{
					panel_1 = new JPanel();
					panel.add(panel_1, BorderLayout.NORTH);
					{
						btnSavePng = new JButton("Save png");
						btnSavePng.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								saveDotToPng();
							}
						});
						panel_1.add(btnSavePng);
					}
					{
						buttonZoomIn = new JButton("+");
						buttonZoomIn.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								ImageIcon icon = new ImageIcon("elf.png");
								icon.getImage().flush();
								preferWidth = (int) (((float) preferWidth) * 1.1);
								preferHeight = (int) (((float) preferHeight) * 1.1);
								dotLabel.setIcon(resizeImage(icon, preferWidth, preferHeight));
							}
						});
						panel_1.add(buttonZoomIn);
					}
					{
						buttonZoomOut = new JButton("-");
						buttonZoomOut.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								ImageIcon icon = new ImageIcon("elf.png");
								icon.getImage().flush();
								preferWidth = (int) (((float) preferWidth) * 0.9);
								preferHeight = (int) (((float) preferHeight) * 0.9);
								dotLabel.setIcon(resizeImage(icon, preferWidth, preferHeight));
							}
						});
						panel_1.add(buttonZoomOut);
					}
					{
						button = new JButton("100%");
						button.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								ImageIcon icon = new ImageIcon("elf.png");
								icon.getImage().flush();
								dotLabel.setIcon(resizeImage(icon, icon.getIconWidth(), icon.getIconHeight()));
							}
						});
						panel_1.add(button);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void saveDotToPng() {
		final JFileChooser fc = new JFileChooser(Setting.getInstance().lastOpenPath);
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			saveImage(new ImageIcon("elf.png").getImage(), fc.getSelectedFile());
		}
	}

	public void updateJGraphx(MyTreeModel model) {
		//		if (1 < 2)
		//			return;
		graph = new mxGraph() {
			public void drawState(mxICanvas canvas, mxCellState state, String label) {
				if (getModel().isVertex(state.getCell()) && canvas instanceof PeterSwingCanvas) {
					PeterSwingCanvas c = (PeterSwingCanvas) canvas;
					c.drawVertex(state, label);
				} else {
					// draw edge, at least
					//					super.drawState(canvas, state, label);
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
		Iterator<ELFNode> ir = ((ELFNode) myTreeModel.getRoot()).child.iterator();
		while (ir.hasNext()) {
			ELFNode n = ir.next();
			addCells(parent, n, null);
		}

		graph.setCellsDisconnectable(false);
		graphComponent = new CallGraphComponent(graph);
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
			jSaveToPngButton.setText("Save png");
			jSaveToPngButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/famfam_icons/disk.png")));
			jSaveToPngButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jSaveToPngButtonActionPerformed(evt);
				}
			});
		}
	}

	String getRandomColor() {
		Color aColor = new Color(numGen.nextInt(256), numGen.nextInt(256), numGen.nextInt(256));
		String hexStr = Integer.toHexString(aColor.getRGB());
		return hexStr;
	}

	String getEdgeColor(String edgeIDStr) {
		String hexStr = allNodesEdgeColor.get(edgeIDStr);
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
					String hexStr = getEdgeColor(lastVertex.getValue().toString());
					graph.insertEdge(parent, null, "", lastVertex, newNode, mxConstants.STYLE_STROKECOLOR + "=#" + hexStr + ";edgeStyle=elbowEdgeStyle;");
				}
				while (ir.hasNext()) {
					ELFNode n = ir.next();
					addCells(parent, n, newNode);
				}

			} else {
				if (lastVertex != null && lastVertex != allNodes.get(node.getFile().getName())) {
					String hexStr = getEdgeColor(lastVertex.getValue().toString());
					graph.insertEdge(parent, null, "", lastVertex, allNodes.get(node.getFile().getName()), mxConstants.STYLE_STROKECOLOR + "=#" + hexStr
							+ ";edgeStyle=elbowEdgeStyle;");
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
		File files[] = null;
		if (jAnalystButton.getEventSource() == null) {
			final JFileChooser fc = new JFileChooser(Setting.getInstance().lastOpenPath);
			fc.setMultiSelectionEnabled(true);
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				files = fc.getSelectedFiles();
			}
		} else {
			files = new File[1];
			files[0] = new File(((JMenuItem) jAnalystButton.getEventSource()).getText());
		}
		if (files != null) {
			if (files.length == 1) {
				Setting.getInstance().historyList.add(files[0].getAbsolutePath());
				addHistoryMenuitems();
				Setting.getInstance().historyList.add(files[0].getAbsolutePath());
				Setting.getInstance().lastOpenPath = files[0].getParentFile().getAbsolutePath();
			}
			dialog = new AnalystDialog(jframe, jTree1, files);
			dialog.setVisible(true);
			updateJGraphx(myTreeModel);
			dotButtonActionPerformed(null);
		}
	}

	private void addHistoryMenuitems() {
		jAnalystButton.removeAll();
		LinkedHashSet<String> historyVector = Setting.getInstance().historyList;
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
		final JFileChooser fc = new JFileChooser(Setting.getInstance().lastOpenPath);
		fc.setMultiSelectionEnabled(true);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			Setting.getInstance().lastOpenPath = fc.getSelectedFile().getAbsolutePath();
			AnalystDialog d = new AnalystDialog(jframe, jTree1, fc.getSelectedFiles());
			d.setVisible(true);
			System.out.println("updateJGraphx(myTreeModel);");
			updateJGraphx(myTreeModel);
			System.out.println("dotButtonActionPerformed(null);");
			dotButtonActionPerformed(null);
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

			String str;
			if (jLayoutButton.getEventSource() == null) {
				str = "Hierarchical Layout";
			} else {
				str = ((JMenuItem) jLayoutButton.getEventSource()).getText();
			}
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
			jTabbedPane1.setSelectedIndex(1);
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
			ImageIO.write(imageToBufferedImage(image), "PNG", file);
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

	private void jSaveToPngButtonActionPerformed(ActionEvent evt) {
		final JFileChooser fc = new JFileChooser(Setting.getInstance().lastOpenPath);
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
					File file = new File("elf.dot");
					BufferedWriter of = new BufferedWriter(new FileWriter(file));
					of.write("digraph G{\n");
					of.write("\tfontname = Verdana;\n");
					of.write("\tfontsize = 8;\n");
					if (chckbxOrtho.isSelected()) {
						of.write("\tsplines=ortho;\n");
					}
					of.write("\tnodesep=0.15;\n");
					of.write("\tranksep=0.15;\n");
					of.write("\tnode [shape=box, margin=0.04, shape=box, fontname=\"Ubuntu-M\", fontsize = 10, width=0.2, height=0.1];\n");

					int maxDepthOfTree = getMaxDepth((ELFNode) myTreeModel.getRoot());

					//rank
					for (int x = 1; x <= maxDepthOfTree; x++) {
						int level = maxDepthOfTree - x;
						if (level > (Integer) maxLevelSpinner.getValue()) {
							continue;
						}
						d.jProgressBar.setString("Level " + level);
						Vector<ELFNode> nodesInLevel = new Vector<ELFNode>();
						getNodesInLevel(nodesInLevel, (ELFNode) myTreeModel.getRoot(), x);

						String tempStr = "";

						if (level <= (Integer) maxLevelSpinner.getValue()) {
							allLevelNames.add("\"Level " + level + "\"");
						}
						tempStr = "\t{\n\t\trank=same;\"Level " + level + "\";";
						boolean newline = false;
						boolean hasAtLeastOneNodeInLevel = false;
						for (int y = 0; y < nodesInLevel.size(); y++) {
							ELFNode node = nodesInLevel.get(y);
							if (filterNoChildNodejCheckBox.isSelected() && node.getChildCount() == 0 && node.getParent().getParent() == null) {
								continue;
							}
							if ((maxDepthOfTree - node.getLevel()) <= (Integer) maxLevelSpinner.getValue()) {
								if (y > 0 && !newline && hasAtLeastOneNodeInLevel) {
									tempStr += " ; ";
								}
								hasAtLeastOneNodeInLevel = true;
								tempStr += "\"" + node.getFile().getName() + "\"";
								//					if (y % 4 == 0 && y > 0 && y != nodesInLevel.size() - 1) {
								//						//level = level + 0.1f;
								//						level = level.add(BigDecimal.valueOf(0.1));
								//						of.write("\n\t}\n");
								//						of.write("\t{\n\t\trank=same;\"Level " + level + "\";");
								//						allLevelNames.add("\"Level " + level + "\"");
								//						newline = true;
								//					} else {
								//						newline = false;
								//					}
							}
						}

						tempStr += "\n\t}\n";

						if (hasAtLeastOneNodeInLevel) {
							of.write(tempStr);
						}
					}
					//end rank

					addDotCells(of, (ELFNode) myTreeModel.getRoot(), chckbxEdge.isSelected(), maxDepthOfTree, d);

					of.write("\t{\n\t");
					//of.write("\t\tnode[shape=box fontsize=8 width=0.2 height=0.1];\n");
					for (int x = 0; x < allLevelNames.size(); x++) {
						if (x > 0) {
							of.write(" -> ");
						}
						of.write(allLevelNames.toArray()[x].toString());
					}
					of.write("\n\t}\n");
					of.write("}\n"); //end graph
					of.close();

					System.out.println("running dot command : " + "dot -Tpng " + file.getName() + " -o elf.png");
					CommonLib.runCommand("dot -Tpng " + file.getName() + " -o elf.png");
					//file.delete();
					ImageIcon icon = new ImageIcon("elf.png");
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
					dotLabel.setIcon(resizeImage(icon, preferWidth, preferHeight));
					jTabbedPane1.setSelectedIndex(2);
					System.out.println("Process ended");
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

	private void getAllNodes(Vector<ELFNode> allNodes, ELFNode node) {
		allNodes.add(node);
		Iterator<ELFNode> ir = node.child.iterator();
		while (ir.hasNext()) {
			ELFNode childNode = ir.next();
			getAllNodes(allNodes, childNode);
		}
	}

	private void getNodesInLevel(Vector<ELFNode> nodesInLevel, ELFNode node, int level) {
		Vector<ELFNode> allNodes = new Vector<ELFNode>();
		getAllNodes(allNodes, (ELFNode) myTreeModel.getRoot());
		Collections.sort(allNodes);
		ELFNode lastNode = null;
		int maxDepth = -9999;
		for (ELFNode n : allNodes) {
			if (lastNode != null && !n.file.getName().equals(lastNode.file.getName())) {
				if (maxDepth == level) {
					Global.debug(lastNode.file.getName() + " == " + level);
					nodesInLevel.addElement(lastNode);
				}
				maxDepth = n.getLevel();
			} else {
				if (n.getLevel() > maxDepth) {
					maxDepth = n.getLevel();
				}

				Global.debug("   " + n.file.getName() + " = " + n.getLevel());
			}
			lastNode = n;
		}
	}

	private int getMaxDepth(ELFNode node) {
		if (node == null) {
			return 0;
		}
		int maxChildDepth = node.getLevel();
		Iterator<ELFNode> ir = node.child.iterator();
		while (ir.hasNext()) {
			ELFNode childNode = ir.next();
			int childLevel = getMaxDepth(childNode);
			if (childLevel > maxChildDepth) {
				maxChildDepth = childLevel;
			}
		}
		return maxChildDepth;
	}

	private void addDotCells(BufferedWriter writer, ELFNode node, boolean hasEdge, int maxDepthOfTree, JProgressBarDialog d) throws IOException {
		if (filterNoChildNodejCheckBox.isSelected() && node.getChildCount() == 0 && node.getParent().getParent() == null) {
			return;
		}
		Iterator<ELFNode> ir = node.child.iterator();
		if (!node.file.getName().equals("Peter") && !finishedDotNodes.contains(node.file.getName())) {
			if ((maxDepthOfTree - node.getLevel()) <= (Integer) maxLevelSpinner.getValue()) {
				writer.write("\t\"" + node.file.getName() + "\" [];\n");
				finishedDotNodes.add(node.file.getName());
			}
		}

		float r = numGen.nextFloat();
		float g = numGen.nextFloat();
		float b = numGen.nextFloat();

		while (ir.hasNext()) {
			ELFNode childNode = ir.next();
			if (hasEdge && !node.file.getName().equals("Peter") && !allEdges.contains(node.file.getName() + "\" -> \"" + childNode.file.getName())) {
				if ((maxDepthOfTree - node.getLevel()) <= (Integer) maxLevelSpinner.getValue() && (maxDepthOfTree - childNode.getLevel()) <= (Integer) maxLevelSpinner.getValue()) {
					d.jProgressBar.setString("\t\t\"" + node.file.getName() + "\" -> \"" + childNode.file.getName() + "\"");
					writer.write("\t\t\"" + node.file.getName() + "\" -> \"" + childNode.file.getName() + "\" [width=1, color=\"" + r + " ," + g + ", " + b + "\"];\n");
					allEdges.add(node.file.getName() + "\" -> \"" + childNode.file.getName());
				}
			}
			addDotCells(writer, childNode, hasEdge, maxDepthOfTree, d);
		}
	}

	protected void genGephiCSV(BufferedWriter of, ELFNode node) throws IOException {
		Iterator<ELFNode> ir = node.child.iterator();
		while (ir.hasNext()) {
			ELFNode childNode = ir.next();
			of.write(node.file.getName() + "," + childNode.file.getName() + "\n");
		}
		ir = node.child.iterator();
		while (ir.hasNext()) {
			ELFNode childNode = ir.next();
			genGephiCSV(of, childNode);
		}
	}
}
