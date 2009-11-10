package com.jps2.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.jps2.gui.MainWindow;

public abstract class ConfigManager {

	private static final Properties	props	= new Properties();

	private ConfigManager() {
	}

	public static final void initialize() {
		try {
			final File cfgFile = new File("jps2.properties");
			if (cfgFile.exists() && cfgFile.isFile()) {
				props.load(new FileInputStream(cfgFile));
			} else {
				save();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static final void save() {
		try {
			props.store(new FileOutputStream("jps2.properties"), "jps2 configuration file");
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private static final String	biosKey	= "bios.file";

	public static final File getBiosFile() {
		File file = null;
		if (props.getProperty(biosKey) == null || !(file = new File(props.getProperty(biosKey))).exists()) {
			final JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle(ResourceManager.getString("bios.select.title"));
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.setFileFilter(new FileFilter() {

				@Override
				public String getDescription() {
					return "PS2 Bios Files";
				}

				@Override
				public boolean accept(final File f) {
					// only show 4Mb files
					return !f.isFile() || (f.length() >= (1024 * 4096));
				}
			});

			fileChooser.showOpenDialog(MainWindow.getInstance());
			file = fileChooser.getSelectedFile();
			if (file == null) {
				JOptionPane.showMessageDialog(MainWindow.getInstance(), ResourceManager.getString("bios.notfound"), "Erro", JOptionPane.ERROR_MESSAGE);
				throw new RuntimeException(ResourceManager.getString("bios.notfound"));
			} else {
				props.setProperty(biosKey, file.getAbsolutePath());
				save();
			}

		}
		return file;
	}
}
