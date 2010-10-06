package com.peter;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.LinkedHashSet;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.petersoft.advancedswing.jdropdownbutton.JDropDownButton;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI Builder, which is free for non-commercial use. If Jigloo is being used commercially (ie, by a
 * corporation, company or business for any purpose whatever) then you should purchase a license for each developer using Jigloo. Please visit www.cloudgarden.com for details. Use
 * of Jigloo implies acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR ANY
 * CORPORATE OR COMMERCIAL PURPOSE.
 */
public class Application extends javax.swing.JFrame {
	private JTabbedPane jTabbedPane1;
	private JPanel jTreePanel;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JDropDownButton jAnalystButton;
	private JToolBar jToolBar1;
	private JEditorPane jTextArea1;
	private JTree jTree1;
	private JSplitPane jSplitPane1;
	private MyTreeModel myTreeModel = new MyTreeModel(null);

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Application inst = new Application();
				inst.setVisible(true);
			}
		});
	}

	public Application() {
		super();
		try {
			UIManager.setLookAndFeel("com.petersoft.white.PetersoftWhiteLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		initGUI();
	}

	private void initGUI() {
		try {
			setTitle("Elf Dependency Walker " + Global.version);
			this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent evt) {
					thisWindowClosing(evt);
				}
			});
			this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("icons/peter.png")).getImage());
			{
				jTabbedPane1 = new JTabbedPane();
				getContentPane().add(jTabbedPane1, BorderLayout.CENTER);
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
								jTextArea1.setText("");
							}
						}
					}
				}
			}
			{
				jToolBar1 = new JToolBar();
				getContentPane().add(jToolBar1, BorderLayout.NORTH);
				{
					jAnalystButton = new JDropDownButton();
					jToolBar1.add(jAnalystButton);
					jAnalystButton.setText("Analyst");
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
			}
			int x = Setting.getInstance().getX();
			int y = Setting.getInstance().getY();
			setLocation(x, y);
			this.setSize(399, 520);

			setSize(Setting.getInstance().getWidth(), Setting.getInstance().getHeight());
			jSplitPane1.setDividerLocation(Setting.getInstance().getDivX());
		} catch (Exception e) {
			e.printStackTrace();
		}
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

			JAnalystDialog dialog = new JAnalystDialog(this, jTree1, file);
			dialog.setVisible(true);
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
			jTextArea1.setText(node.getNmResult());
			jTextArea1.setCaretPosition(0);
		} catch (Exception ex) {

		}
	}

}
