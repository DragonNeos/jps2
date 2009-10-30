package com.jps2.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.jps2.util.ResourceManager;

/**
 * Plugin configuration dialog.
 * 
 * @author dyorgio
 */
@SuppressWarnings("serial")
public class PluginConfigDialog extends JDialog {
	/**
	 * Contruct a plugin configuration dialog.
	 */
	public PluginConfigDialog() {
		// use MainWindow as onwer and make this dialog modal (block MainWindow)
		super(MainWindow.getInstance(), ResourceManager
				.getString("config.plugin.title"), true);
		// dispose this dialog if presse close button
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// set dialog icon
		setIconImage(ResourceManager.getIcon("/icons/16x16/config.png")
				.getImage());
		// contruct dialog UI
		initComponents();
		// adjust window preferred size
		pack();
		// center of screen
		setLocationRelativeTo(null);
		// show
		setVisible(true);
	}

	private final void initComponents() {

		// add ok/cancel button in a panel on south
		add(new JPanel(new FlowLayout(FlowLayout.TRAILING)) {
			{
				add(new JButton(new AbstractAction(ResourceManager
						.getString("config.plugin.ok.text")) {

					@Override
					public void actionPerformed(final ActionEvent e) {
						// save config
						save();
						// close about dialog
						dispose();
					}
				}));
				add(new JButton(new AbstractAction(ResourceManager
						.getString("config.plugin.cancel.text")) {

					@Override
					public void actionPerformed(final ActionEvent e) {
						// close about dialog
						dispose();
					}
				}));
			}
		}, BorderLayout.SOUTH);
	}

	private final void save() {

	}
}
