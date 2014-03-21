package com.elfdependencywalker;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class SettingDialog extends JDialog {
	private JScrollPane jScrollPane1;
	private JLabel jLabel1;
	public JTextArea jTextArea1;
	private JButton saveButton;
	private JPanel jPanel1;

	public SettingDialog(JFrame frame, boolean modal) {
		super(frame, modal);
		try {
			this.setTitle("Lookup directory");

			jScrollPane1 = new JScrollPane();
			getContentPane().add(jScrollPane1, BorderLayout.CENTER);

			jTextArea1 = new JTextArea();
			jScrollPane1.setViewportView(jTextArea1);
			Setting setting = Setting.getInstance();

			if (setting.lookupDirectory == null || setting.lookupDirectory.size() == 0) {
				jTextArea1.setText("/usr\n/usr/lib\n/lib\n/usr/local/lib\n/lib64\n/usr/lib64\n/usr/local/lib64");
			} else {
				String s = "";
				for (String a : setting.lookupDirectory) {
					s += a + "\n";
				}
				jTextArea1.setText(s);
			}

			jPanel1 = new JPanel();
			getContentPane().add(jPanel1, BorderLayout.SOUTH);

			jLabel1 = new JLabel();
			jPanel1.add(jLabel1);
			jLabel1.setText("Add the lookup directories here, line by line");

			saveButton = new JButton();
			jPanel1.add(saveButton);
			saveButton.setText("Save");
			saveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					saveButtonActionPerformed(evt);
				}
			});

			this.setSize(462, 332);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveButtonActionPerformed(ActionEvent evt) {
		Setting setting = Setting.getInstance();
		String s[] = jTextArea1.getText().split("\n");
		setting.lookupDirectory.clear();
		for (String a : s) {
			if (!a.equals("")) {
				setting.lookupDirectory.add(a);
			}
		}
		setting.save();
		setVisible(false);
	}
}
