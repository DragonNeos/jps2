package com.jps2.gui.config;

import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.NORTHWEST;

import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jps2.util.ResourceManager;

public class GSConfigPanel extends JPanel {

	private static final Insets	EMPTY_INSETS	= new Insets(0, 0, 0, 0);

	public GSConfigPanel() {
		super(new GridBagLayout());
		initComponents();
	}

	private final void initComponents() {
		add(new JLabel(ResourceManager.getString("config.plugin.gs.plugin")), new GridBagConstraints(0, 0, 1, 1, 0, 0, NORTHWEST, HORIZONTAL, EMPTY_INSETS, 0, 0));

		add(new JLabel(ResourceManager.getString("config.plugin.gs.fullscreen.resolution")), new GridBagConstraints(0, 1, 1, 1, 0, 0, NORTHWEST, HORIZONTAL, EMPTY_INSETS, 0, 0));

		final ArrayList<DisplayModeView> modes = new ArrayList<DisplayModeView>();

		for (final DisplayMode mode : GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayModes()) {
			if (mode.getBitDepth() >= 16 && mode.getRefreshRate() <= 60) {
				modes.add(new DisplayModeView(mode));
			}
		}

		add(new JComboBox(modes.toArray()), new GridBagConstraints(1, 1, 3, 1, 0, 0, NORTHWEST, HORIZONTAL, EMPTY_INSETS, 0, 0));
	}

	private static final class DisplayModeView {
		private final DisplayMode	mode;

		DisplayModeView(final DisplayMode mode) {
			this.mode = mode;
		}

		public DisplayMode getMode() {
			return mode;
		}

		@Override
		public String toString() {
			return mode.getWidth() + "x" + mode.getHeight() + "x" + mode.getBitDepth() + "@" + mode.getRefreshRate();
		}
	}
}
