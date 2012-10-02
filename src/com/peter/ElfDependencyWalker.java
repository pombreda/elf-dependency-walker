package com.peter;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.mxgraph.view.mxGraph;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI Builder, which is free for non-commercial use. If Jigloo is being used commercially (ie, by a
 * corporation, company or business for any purpose whatever) then you should purchase a license for each developer using Jigloo. Please visit www.cloudgarden.com for details. Use
 * of Jigloo implies acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR ANY
 * CORPORATE OR COMMERCIAL PURPOSE.
 */
public class ElfDependencyWalker extends javax.swing.JFrame {
	mxGraph graph;
	private ElfDependencyWalkerPanel elfDependencyWalkerPanel;
	Object parent;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ElfDependencyWalker inst = new ElfDependencyWalker();
				inst.setVisible(true);
			}
		});
	}

	public ElfDependencyWalker() {
		super();
		try {
			UIManager.setLookAndFeel("com.peterswing.white.PeterSwingWhiteLookAndFeel");
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
			int x = Setting.getInstance().getX();
			int y = Setting.getInstance().getY();
			setLocation(x, y);
			{
				try {
					// this try-catch prevent Jigloo crash
					elfDependencyWalkerPanel = new ElfDependencyWalkerPanel(this);
				} catch (Exception ex) {

				}
				getContentPane().add(elfDependencyWalkerPanel, BorderLayout.CENTER);
			}
			this.setSize(814, 533);

			setSize(Setting.getInstance().getWidth(), Setting.getInstance().getHeight());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void thisWindowClosing(WindowEvent evt) {
		Setting.getInstance().setWidth(this.getWidth());
		Setting.getInstance().setHeight(this.getHeight());
		Setting.getInstance().setX(this.getLocation().x);
		Setting.getInstance().setY(this.getLocation().y);
		Setting.getInstance().save();
	}

}
