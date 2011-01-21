package com.peter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Hashtable;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.LayoutStyle;

import com.petersoft.CommonLib;

public class JAnalystDialog extends javax.swing.JDialog implements Runnable {
	private JButton jCancelButton;
	private JLabel jLabel1;
	private JTree jTree;
	private File file;
	public Hashtable<String, ELFNode> allNodes = new Hashtable<String, ELFNode>();
	final int MAX_NUMBER_OF_VERTEX = 500;
	int noOfVertex;

	public JAnalystDialog(JFrame frame, JTree jTree, File file) {
		super(frame, true);
		this.jTree = jTree;
		this.file = file;

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
		allNodes.clear();
		ELFNode node = analystELF(file, null);
		((MyTreeModel) jTree.getModel()).setRoot(node);
		this.jCancelButton.setText("Finished");
		this.setVisible(false);
	}

	private ELFNode analystELF(File file, ELFNode parent) {
		if (file.isDirectory()) {
			ELFNode mother = null;
			for (File f : file.listFiles()) {
				if (mother == null) {
					mother = analystELF(f, null);
				} else {
					analystELF(f, mother);
				}
			}
			return mother;
		}

		if (noOfVertex >= MAX_NUMBER_OF_VERTEX) {
			return parent;
		}
		if (allNodes.get(file.getName()) == null) {
			noOfVertex++;
			jLabel1.setText(noOfVertex + " " + file);
		}

		String results[];

		results = CommonLib.runCommand("readelf -a " + file.getAbsolutePath()).split("\n");

		ELFNode node = new ELFNode(file, null, parent, false);

		if (parent != null) {
			if (allNodes.get(file.getName()) != null) {
				parent.child.add(allNodes.get(file.getName()));
				return null;
			} else {
				parent.child.add(node);
				allNodes.put(file.getName(), node);
			}
		}
		for (String line : results) {
			if (line.toLowerCase().contains("needed")) {
				String words[] = line.split("[\\[\\]]");
				if (words.length > 1) {
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
						node.child.add(new ELFNode(new File(words[1]), "", parent, true));
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
