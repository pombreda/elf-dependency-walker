package com.peter;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

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
public class Application extends javax.swing.JFrame {
	private JTabbedPane jTabbedPane1;
	private JPanel jTreePanel;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JButton jAnalystButton;
	private JToolBar jToolBar1;
	private JTextArea jTextArea1;
	private JTree jTree1;
	private JSplitPane jSplitPane1;

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
								jTree1 = new JTree();
								jScrollPane1.setViewportView(jTree1);
							}
						}
						{
							jScrollPane2 = new JScrollPane();
							jSplitPane1.add(jScrollPane2, JSplitPane.RIGHT);
							jScrollPane2.setPreferredSize(new java.awt.Dimension(68, 541));
							{
								jTextArea1 = new JTextArea();
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
					jAnalystButton = new JButton();
					jToolBar1.add(jAnalystButton);
					jAnalystButton.setText("Analyst");
					jAnalystButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jAnalystButtonActionPerformed(evt);
						}
					});
				}
			}
			int x = Setting.getInstance().getX();
			int y = Setting.getInstance().getY();
			setLocation(x, y);

			setSize(Setting.getInstance().getWidth(), Setting.getInstance().getHeight());
			jSplitPane1.setDividerLocation(Setting.getInstance().getDivX());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jAnalystButtonActionPerformed(ActionEvent evt) {
		JAnalystDialog dialog = new JAnalystDialog(this);
		dialog.setVisible(true);
	}

	private void thisWindowClosing(WindowEvent evt) {
		Setting.getInstance().setWidth(this.getWidth());
		Setting.getInstance().setHeight(this.getHeight());
		Setting.getInstance().setX(this.getLocation().x);
		Setting.getInstance().setY(this.getLocation().y);
		Setting.getInstance().setDivX(jSplitPane1.getDividerLocation());
		Setting.getInstance().save();
	}

}
