package com.peter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.LayoutStyle;

import com.peterswing.CommonLib;

public class JAnalystDialog extends javax.swing.JDialog implements Runnable {
	//$hide>>$
	private JButton jCancelButton;
	private JLabel jLabel1;
	private JTree jTree;
	private File files[];
	//public Hashtable<String, ELFNode> allNodes = new Hashtable<String, ELFNode>();
	final int MAX_NUMBER_OF_VERTEX = 100000000;
	int noOfVertex;

	private String onlyInTheseDirectories[] = { "/lib", "/usr/lib", "/usr/local/lib", "/lib64", "/usr/lib64", "/usr/local/lib64" };

	Vector<String> parsedFiles = new Vector<String>();

	public JAnalystDialog(JFrame frame, JTree jTree, File files[]) {
		super(frame, true);
		this.jTree = jTree;
		this.files = files;

		initGUI();
		CommonLib.centerDialog(this);
	}

	private void initGUI() {
		try {
			{
				GroupLayout thisLayout = new GroupLayout((JComponent) getContentPane());
				getContentPane().setLayout(thisLayout);
				this.setTitle("Analyzing");

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
		//		allNodes.clear();
		noOfVertex = 0;
		ELFNode node = null;
		ELFNode root = new ELFNode(new File("Peter"), null, true);
		parsedFiles.clear();
		for (File file : files) {
			System.out.println(file.getAbsolutePath());
			if (file.isFile()) {
				node = analystELF(file);
				root.child.add(node);
			} else {
				for (File f : file.listFiles()) {
					node = analystELF(f);
					if (node.file.isFile()) {
						root.child.add(node);
					}
				}
			}
		}
		if (node != null) {
			((MyTreeModel) jTree.getModel()).setRoot(root);
		}
		this.jCancelButton.setText("Finished");
		this.setVisible(false);
	}

	private ELFNode analystELF(File file) {
		Setting setting = Setting.getInstance();
		if (setting.getLookupDirectory().size() == 0) {
			JOptionPane.showMessageDialog(this, "Lookup directory empty, please set them in setting!!!");
			return null;
		}
		if (noOfVertex >= MAX_NUMBER_OF_VERTEX) {
			return null;
		}

		String results[] = CommonLib.runCommand("readelf -a " + file.getAbsolutePath()).split("\n");

		ELFNode currentNode = new ELFNode(file, null, false);
		for (String line : results) {
			String words[] = line.split("[\\[\\]]");
			if (words.length > 1 && line.toLowerCase().contains("needed")) {
				//				boolean match = false;
				File childFile = null;

				for (String s : setting.getLookupDirectory()) {
					if (new File(s + "/" + words[1]).exists()) {
						childFile = new File(s + "/" + words[1]);
						break;
					}
				}
				if (childFile != null) {
					if (parsedFiles.contains(file.getName() + "-" + childFile.getName())) {
						continue;
					} else {
						parsedFiles.add(file.getName() + "-" + childFile.getName());
					}
					ELFNode node = analystELF(childFile);
					if (node.file.isFile()) {
						currentNode.child.add(node);
						jLabel1.setText(noOfVertex + " " + file.getName());
						noOfVertex++;
					}
				}
			}
		}
		return currentNode;
	}

	private void thisWindowActivated(WindowEvent evt) {
		new Thread(this).start();
	}

	private void jCancelButtonActionPerformed(ActionEvent evt) {
		this.setVisible(false);
	}
	//$hide<<$
}
