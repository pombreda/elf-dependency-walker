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

import com.peterswing.CommonLib;

public class JAnalystDialog extends javax.swing.JDialog implements Runnable {
	private JButton jCancelButton;
	private JLabel jLabel1;
	private JTree jTree;
	private File file;
	public Hashtable<String, ELFNode> allNodes = new Hashtable<String, ELFNode>();
	final int MAX_NUMBER_OF_VERTEX = 100000000;
	int noOfVertex;

	private String onlyInTheseDirectories[] = { "/lib", "/usr/lib", "/usr/local/lib", "/lib64", "/usr/lib64", "/usr/local/lib64" };

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
		ELFNode node;
		if (file.isFile()) {
			node = analystELF(file, null);
		} else {
			node = new ELFNode(file, null, null, true);
			for (File f : file.listFiles()) {
				analystELF(f, node);
			}
		}
		((MyTreeModel) jTree.getModel()).setRoot(node);
		this.jCancelButton.setText("Finished");
		this.setVisible(false);
	}

	private ELFNode analystELF(File file, ELFNode parent) {
		if (noOfVertex >= MAX_NUMBER_OF_VERTEX) {
			return null;
		}

		if (allNodes.get(file.getName()) == null) {
			noOfVertex++;
			jLabel1.setText(noOfVertex + " " + file.getName());
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
			String words[] = line.split("[\\[\\]]");
			if (words.length > 1 && line.toLowerCase().contains("needed")) {
				boolean match = false;
				File childFile = null;
				Setting setting = Setting.getInstance();
				for (String s : setting.getLookupDirectory()) {
					if (new File(s + "/" + words[1]).exists()) {
						childFile = new File(s + "/" + words[1]);
						break;
					}
				}
				/*
				if (new File(file.getParent() + "/" + words[1]).exists()) {
					childFile = new File(file.getParent() + "/" + words[1]);
				} else if (new File("/lib/" + words[1]).exists()) {
					childFile = new File("/lib/" + words[1]);
				} else if (new File("/usr/lib/" + words[1]).exists()) {
					childFile = new File("/usr/lib/" + words[1]);
				} else if (new File("/usr/local/lib/" + words[1]).exists()) {
					childFile = new File("/usr/local/lib/" + words[1]);
				} else if (new File("/lib64/" + words[1]).exists()) {
					childFile = new File("/lib64/" + words[1]);
				} else if (new File("/usr/lib64/" + words[1]).exists()) {
					childFile = new File("/usr/lib64/" + words[1]);
				} else if (new File("/usr/local/lib64/" + words[1]).exists()) {
					childFile = new File("/usr/local/lib64/" + words[1]);
				} else {
					node.child.add(new ELFNode(new File(words[1]), "", parent, true));
				}
				*/
				if (childFile != null) {
					for (String s : onlyInTheseDirectories) {
						if (childFile.getAbsolutePath().startsWith(s)) {
							match = true;
							break;
						}
					}
					if (match) {
						analystELF(childFile, node);
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
