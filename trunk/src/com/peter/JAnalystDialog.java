package com.peter;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.LayoutStyle;

import com.petersoft.CommonLib;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class JAnalystDialog extends javax.swing.JDialog implements Runnable {
	private JButton jCancelButton;
	private JLabel jLabel1;
	private JTree jTree;
	private File file;

	public JAnalystDialog(JFrame frame, JTree jTree, File file) {
		super(frame);
		this.jTree = jTree;
		this.file = file;

		// File files[] = new File("/lib").listFiles();
		// for (File file : files) {
		// System.out.println(file);
		// }

		initGUI();
		CommonLib.centerDialog(this);
	}

	private void initGUI() {
		try {
			{
				GroupLayout thisLayout = new GroupLayout((JComponent) getContentPane());
				getContentPane().setLayout(thisLayout);
				this.setTitle("Analyting");
				this.addWindowListener(new WindowAdapter() {
					public void windowActivated(WindowEvent evt) {
						thisWindowActivated(evt);
					}
				});
				{
					jCancelButton = new JButton();
					jCancelButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jCancelButtonActionPerformed(evt);
						}
					});
					jCancelButton.setText("Cancel");
				}
				{
					jLabel1 = new JLabel();
				}
				thisLayout.setVerticalGroup(thisLayout.createSequentialGroup().addContainerGap().addComponent(jLabel1, 0, 44, Short.MAX_VALUE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jCancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE).addContainerGap());
				thisLayout.setHorizontalGroup(thisLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								thisLayout
										.createParallelGroup()
										.addComponent(jLabel1, GroupLayout.Alignment.LEADING, 0, 378, Short.MAX_VALUE)
										.addGroup(
												GroupLayout.Alignment.LEADING,
												thisLayout.createSequentialGroup().addGap(0, 317, Short.MAX_VALUE)
														.addComponent(jCancelButton, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))).addContainerGap());
			}
			this.setSize(418, 126);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		ELFNode node = analystELF(file, null);
		((MyTreeModel) jTree.getModel()).setRoot(node);
		this.jCancelButton.setText("Finished");
	}

	private ELFNode analystELF(File file, ELFNode parent) {
		jLabel1.setText(file.getAbsolutePath());

		String result = CommonLib.runCommand("readelf -a " + file.getAbsolutePath());
		// System.out.println(result);
		String lines[] = result.split("\n");

		ELFNode node = new ELFNode(file, result, parent);
		if (parent != null) {
			parent.child.add(node);
		}

		for (String line : lines) {
			if (line.toLowerCase().contains("needed")) {
				String words[] = line.split("[\\[\\]]");
				if (words.length > 0) {
					if (new File("/lib/" + words[1]).exists()) {
						analystELF(new File("/lib/" + words[1]), node);
					} else if (new File("/usr/lib/" + words[1]).exists()) {
						analystELF(new File("/usr/lib/" + words[1]), node);
					} else if (new File("/usr/local/lib/" + words[1]).exists()) {
						analystELF(new File("/usr/local/lib/" + words[1]), node);
					} else if (new File("/lib/" + words[1]).exists()) {
						analystELF(new File("/lib/" + words[1]), node);
					} else if (new File("/usr/lib/" + words[1]).exists()) {
						analystELF(new File("/usr/lib/" + words[1]), node);
					} else if (new File(file.getParent() + "/" + words[1]).exists()) {
						analystELF(new File(file.getParent() + "/" + words[1]), node);
					} else {
						System.out.println("fuck " + file.getParent() + "/" + words[1]);
						node.child.add(new ELFNode(new File(words[1]), "not found", parent));
					}
				}
			}
		}
		return node;
	}

	private void thisWindowActivated(WindowEvent evt) {
		new Thread(this).start();
	}

	private void jCancelButtonActionPerformed(ActionEvent evt) {
		this.setVisible(false);
	}
}
