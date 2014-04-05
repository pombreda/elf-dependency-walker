package com.elfdependencywalker;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import com.mxgraph.view.mxGraph;

public class ElfDependencyWalker extends javax.swing.JFrame {
	private static final long serialVersionUID = 4271596071774399164L;
	mxGraph graph;
	private ElfDependencyWalkerPanel elfDependencyWalkerPanel;
	Object parent;
	static CommandLine cmd;

	public static void main(String[] args) {
		CommandLineParser parser = new PosixParser();
		Options options = new Options();
		try {
			options.addOption(OptionBuilder.withDescription("specific config xml").hasArg().withArgName("file").create("f"));
			//			options.addOption("mac", false, "parse for mac");
			cmd = parser.parse(options, args);
		} catch (ParseException e1) {
			e1.printStackTrace();
			System.exit(1);
		}

		if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			Global.isMac = true;
		}
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
		try {
			setTitle("Elf Dependency Walker " + Global.version);
			this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent evt) {
					thisWindowClosing(evt);
				}
			});
			this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("icons/peter.png")).getImage());
			int x = Setting.getInstance().x;
			int y = Setting.getInstance().y;
			setLocation(x, y);
			elfDependencyWalkerPanel = new ElfDependencyWalkerPanel(this);
			getContentPane().add(elfDependencyWalkerPanel, BorderLayout.CENTER);
			this.setSize(814, 533);

			setSize(Setting.getInstance().width, Setting.getInstance().height);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void thisWindowClosing(WindowEvent evt) {
		Setting.getInstance().width = this.getWidth();
		Setting.getInstance().height = this.getHeight();
		Setting.getInstance().x = this.getLocation().x;
		Setting.getInstance().y = this.getLocation().y;
		Setting.getInstance().x = this.getLocation().x;
		Setting.getInstance().divX = elfDependencyWalkerPanel.splitPane1.getDividerLocation();
		Setting.getInstance().save();
	}

}
