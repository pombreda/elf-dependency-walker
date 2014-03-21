package com.elfdependencywalker;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AnalystDirectoryDialog extends javax.swing.JDialog {
	private JScrollPane jScrollPane1;
	private JButton jButton1;
	private JPanel jPanel1;
	private JLabel jLabel1;
	private JTextArea jTextArea1;
	public boolean ok;

	public AnalystDirectoryDialog(JFrame frame) {
		super(frame, true);
		try {
			jScrollPane1 = new JScrollPane();
			getContentPane().add(jScrollPane1, BorderLayout.CENTER);

			jTextArea1 = new JTextArea();
			jScrollPane1.setViewportView(jTextArea1);
			jTextArea1.setText("/lib\n/usr/lib\n/usr/local/lib");
			jLabel1 = new JLabel();
			getContentPane().add(jLabel1, BorderLayout.NORTH);
			jLabel1.setText("Please input the directory path line by line");
			jPanel1 = new JPanel();
			getContentPane().add(jPanel1, BorderLayout.SOUTH);

			jButton1 = new JButton();
			jPanel1.add(jButton1);
			jButton1.setText("Ok");
			jButton1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jButton1ActionPerformed(evt);
				}
			});
			setSize(400, 300);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jButton1ActionPerformed(ActionEvent evt) {
		ok = true;
		setVisible(false);
	}

	public File[] getSelectedDirectory() {
		String strs[] = jTextArea1.getText().split("\n");
		File file[] = new File[strs.length];
		for (int x = 0; x < strs.length; x++) {
			file[x] = new File(strs[x]);
		}
		return file;
	}

}
