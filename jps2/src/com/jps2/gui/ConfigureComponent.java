package com.jps2.gui;

import javax.swing.JComponent;

public interface ConfigureComponent {

	/**
	 * Save configuration.
	 */
	public void save();

	/**
	 * Get Cfg component
	 */
	public JComponent getComponent();
}
