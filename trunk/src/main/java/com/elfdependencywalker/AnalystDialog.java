package com.elfdependencywalker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.LayoutStyle;

import com.peterswing.CommonLib;

public class AnalystDialog extends javax.swing.JDialog implements Runnable {
	private JButton cancelButton;
	private JLabel label1;
	private JTree jTree;
	private File files[];
	final int MAX_NUMBER_OF_VERTEX = 100000000;
	int noOfVertex;
	boolean started;

	// private String onlyInTheseDirectories[] = { "/lib", "/usr/lib",
	// "/usr/local/lib", "/lib64", "/usr/lib64", "/usr/local/lib64" };

	// Vector<String> parsedFiles = new Vector<String>();
	HashMap<String, ELFNode> parsedFiles = new HashMap<String, ELFNode>();
	HashMap<String, String[]> cache = new HashMap<String, String[]>();

	public AnalystDialog(JFrame frame, JTree jTree, File files[]) {
		super(frame, true);
		this.jTree = jTree;
		this.files = files;

		initGUI();
		CommonLib.centerDialog(this);
	}

	private void initGUI() {
		try {
			GroupLayout thisLayout = new GroupLayout((JComponent) getContentPane());
			getContentPane().setLayout(thisLayout);
			this.setTitle("Analyzing");

			this.addWindowListener(new WindowAdapter() {
				public void windowActivated(WindowEvent evt) {
					thisWindowActivated(evt);
				}
			});
			cancelButton = new JButton();
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jCancelButtonActionPerformed(evt);
				}
			});
			cancelButton.setText("Cancel");
			label1 = new JLabel();
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup().addContainerGap().addComponent(label1, 0, 44, Short.MAX_VALUE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE).addContainerGap());
			thisLayout.setHorizontalGroup(thisLayout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
							thisLayout
									.createParallelGroup()
									.addComponent(label1, GroupLayout.Alignment.LEADING, 0, 378, Short.MAX_VALUE)
									.addGroup(
											GroupLayout.Alignment.LEADING,
											thisLayout.createSequentialGroup().addGap(0, 317, Short.MAX_VALUE)
													.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))).addContainerGap());

			this.setSize(418, 126);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		noOfVertex = 0;
		ELFNode root = new ELFNode(null, new File("Peter"), null, true);
		parsedFiles.clear();
		for (File file : files) {
			if (file.isFile()) {
				analystELF(root, file, "");
			} else {
				for (File f : file.listFiles()) {
					analystELF(root, f, "");
				}
			}
		}
		System.out.println("finished analystELF()");
		if (root != null) {
			((MyTreeModel) jTree.getModel()).setRoot(root);
		}
		this.cancelButton.setText("Finished");
		this.setVisible(false);
	}

	private void analystELF(ELFNode parent, File file, String debugStr) {
		try {
			if (file.isDirectory()) {
				//				try {
				//					parsedFiles.put(file.getCanonicalPath(), null);
				//				} catch (Exception ex) {
				//					ex.printStackTrace();
				//					return null;
				//				}

				for (File f : file.listFiles()) {
					label1.setText("Processing directory " + file.getAbsolutePath());
					analystELF(parent, f, debugStr + "    ");
				}
				return;
			} else {
				Setting setting = Setting.getInstance();

				if (setting.lookupDirectory.size() == 0) {
					String s[] = "/usr\n/usr/lib\n/lib\n/usr/local/lib\n/lib64\n/usr/lib64\n/usr/local/lib64".split("\n");
					setting.lookupDirectory.clear();
					for (String a : s) {
						if (!a.equals("")) {
							setting.lookupDirectory.add(a);
						}
					}
					setting.save();
				}
				if (noOfVertex >= MAX_NUMBER_OF_VERTEX) {
					return;
				}

				ELFNode currentNode;
				String canonicalPath = parent.getFile().getCanonicalPath() + "-" + file.getCanonicalPath();
				if (parsedFiles.keySet().contains(canonicalPath)) {
					currentNode = parsedFiles.get(canonicalPath);
					parent.child.add(currentNode);
					return;
				} else {
					currentNode = new ELFNode(parent, file, null, false);
					parent.child.add(currentNode);
					parsedFiles.put(parent.getFile().getCanonicalPath() + "-" + file.getCanonicalPath(), currentNode);
				}

				String results[];
				boolean needToCache = false;
				if (cache.get(file.getAbsolutePath()) == null) {
					if (Global.isMac) {
						results = CommonLib.runCommand("otool -L " + file.getAbsolutePath()).split("\n");
						results = Arrays.copyOfRange(results, 1, results.length);
					} else {
						results = CommonLib.runCommand("readelf -a " + file.getAbsolutePath()).split("\n");
					}
					needToCache = true;
				} else {
					results = cache.get(file.getAbsolutePath());
				}
				Vector<String> cacheLines = new Vector<String>();

				for (String line : results) {
					if (!started) {
						return;
					}
					String words[];
					if (Global.isMac) {
						line = line.trim();
						words = line.split(" ");
					} else {
						words = line.split("[\\[\\]]");
					}
					if (words.length > 1 && ((line.toLowerCase().contains("needed") && !Global.isMac) || (Global.isMac))) {
						if (needToCache) {
							cacheLines.add(line);
						}
						File childFile = null;

						boolean found = false;
						if (Global.isMac) {
							if (new File(words[0]).exists()) {
								childFile = new File(words[0]);
								found = true;
							}
						} else {
							for (String s : setting.lookupDirectory) {
								if (new File(s + "/" + words[1]).exists()) {
									childFile = new File(s + "/" + words[1]);
									found = true;
									break;
								}
							}
						}

						if (!found) {
							if (Global.isMac) {
								System.out.println("can't found : " + words[0]);
							} else {
								System.out.println("can't found : " + words[1]);
							}
						}
						if (childFile != null && childFile.isFile()) {
							label1.setText("Processing file " + childFile.getAbsolutePath());
							try {
								//								if (parsedFiles.keySet().contains(file.getCanonicalPath() + "-" + childFile.getCanonicalPath())) {
								//									ELFNode childNode = parsedFiles.get(file.getCanonicalPath() + "-" + childFile.getCanonicalPath());
								//									currentNode.child.add(childNode);
								//									childNode.parent.add(currentNode);
								//									System.out.println("parsed : " + file.getCanonicalPath() + "-" + childFile.getCanonicalPath());
								//								} else {
								//									parsedFiles.put(file.getCanonicalPath() + "-" + childFile.getCanonicalPath(), null);
								analystELF(currentNode, childFile, debugStr + "    ");
								//									if (node != null) {
								//										System.out.println("<<" + file.getCanonicalPath() + "-" + childFile.getCanonicalPath());
								//										parsedFiles.put(file.getCanonicalPath() + "-" + childFile.getCanonicalPath(), node);
								//										currentNode.child.add(node);
								//										jLabel1.setText(noOfVertex + " " + file.getCanonicalPath());
								//										noOfVertex++;
								//									}
								//								}
								Global.debug(debugStr + noOfVertex + "," + currentNode.file.getCanonicalPath() + "======" + childFile.getCanonicalPath());
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				}
				if (needToCache) {
					String[] temp = new String[cacheLines.size()];
					cacheLines.toArray(temp);
					cache.put(file.getAbsolutePath(), temp);
				}
				return;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
	}

	private void thisWindowActivated(WindowEvent evt) {
		if (!started) {
			started = true;
			new Thread(this).start();
		}
	}

	private void jCancelButtonActionPerformed(ActionEvent evt) {
		started = false;
		this.setVisible(false);
	}
}
