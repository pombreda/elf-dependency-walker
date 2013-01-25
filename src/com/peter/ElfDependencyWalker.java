package com.peter;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
	mxGraph graph;
	private ElfDependencyWalkerPanel elfDependencyWalkerPanel;
	Object parent;
	static CommandLine cmd;

	public static void main(String[] args) {
		CommandLineParser parser = new PosixParser();
		Options options = new Options();
		try {
			options.addOption(OptionBuilder.withDescription("specific config xml").hasArg().withArgName("file").create("f"));
			options.addOption("mac", false, "parse for mac");
			cmd = parser.parse(options, args);
		} catch (ParseException e1) {
			e1.printStackTrace();
			System.exit(1);
		}

		if (cmd.hasOption("mac")) {
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