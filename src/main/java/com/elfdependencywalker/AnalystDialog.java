package com.elfdependencywalker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.LayoutStyle;

import com.peterswing.CommonLib;

public class AnalystDialog extends JDialog implements Runnable {
	private JButton cancelButton;
	private JLabel label1;
	private JTree jTree;
	private File files[];
	boolean started;

	// private String onlyInTheseDirectories[] = { "/lib", "/usr/lib",
	// "/usr/local/lib", "/lib64", "/usr/lib64", "/usr/local/lib64" };

	// Vector<String> parsedFiles = new Vector<String>();
	HashMap<String, ELFNode> parsedFiles = new HashMap<String, ELFNode>();
	HashMap<String, String[]> cache = new HashMap<String, String[]>();
	static ArrayList<File> dirs = new ArrayList<File>();

	public AnalystDialog(JFrame frame, JTree jTree, File files[]) {
		super(frame, true);
		this.jTree = jTree;
		this.files = files;

		if (dirs.size() == 0) {
			for (String s : Setting.getInstance().lookupDirectory) {
				listDirectory(s, dirs);
			}
		}

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
			root.updateLevel(0, new Vector<ELFNode>());
			((MyTreeModel) jTree.getModel()).setRoot(root);
		}
		this.cancelButton.setText("Finished");
		this.setVisible(false);
	}

	private void analystELF(ELFNode parent, File file, String debugStr) {
		try {
			if (parent.file.getName().equals(file.getName())) {
				return;
			}
			if (file.isDirectory()) {
				for (File f : file.listFiles()) {
					String filename = f.getName().toLowerCase();
					if (filename.contains(".so") || filename.contains(".dylib") || filename.contains(".a") || filename.contains(".o")) {
						label1.setText("Processing directory " + file.getAbsolutePath());
						analystELF(parent, f, debugStr + "    ");
					}
				}
				return;
			} else {
				ELFNode currentNode;
				String canonicalPath = file.getCanonicalPath();
				if (parsedFiles.keySet().contains(canonicalPath)) {
					currentNode = parsedFiles.get(canonicalPath);
					parent.child.add(currentNode);
					return;
				} else {
					currentNode = new ELFNode(parent, file, null, false);
					parent.child.add(currentNode);
					parsedFiles.put(canonicalPath, currentNode);
				}
				Global.debug(debugStr + "," + parent.getFile().getName() + " >> " + file.getName());

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
							//							outer1: for (String s : Setting.getInstance().lookupDirectory) {
							//								ArrayList<File> dirs = new ArrayList<File>();
							//								listDirectory(s, dirs);
							for (File d : dirs) {
								if (new File(d.getAbsolutePath() + File.separator + words[1]).exists()) {
									childFile = new File(d.getAbsolutePath() + File.separator + words[1]);
									found = true;
									break;
								}
							}
							//							}
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
							analystELF(currentNode, childFile, debugStr + " ");
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

	public void listDirectory(String directoryName, ArrayList<File> files) {
		if (directoryName == null || files == null) {
			return;
		}
		File directory = new File(directoryName);
		if (directory.isDirectory()) {
			files.add(directory);
			File[] fList = directory.listFiles();
			if (fList == null) {
				return;
			}
			for (File file : fList) {
				if (file.isDirectory()) {
					listDirectory(file.getAbsolutePath(), files);
				}
			}
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
