package com.jps2.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jps2.util.ResourceManager;

/**
 * About dialog.
 * 
 * @author dyorgio
 */
@SuppressWarnings("serial")
public class AboutDialog extends JDialog {
	/**
	 * Contruct a about dialog.
	 */
	public AboutDialog() {
		// use MainWindow as onwer and make this dialog modal (block MainWindow)
		super(MainWindow.getInstance(), ResourceManager.getString("about.title"), true);
		// dispose this dialog if presse close button
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// set dialog icon
		setIconImage(ResourceManager.getIcon("/icons/16x16/about.png").getImage());
		// contruct dialog UI
		initComponents();
		// adjust window preferred size
		pack();
		// no resize is allowed
		setResizable(false);
		// center of screen
		setLocationRelativeTo(null);
		// show
		setVisible(true);
	}

	private final void initComponents() {
		// add about logo in north
		add(new JLabel(ResourceManager.getIcon("/icons/about_logo.png")), BorderLayout.NORTH);

		// add close button in a panel on south
		add(new JPanel() {
			{
				add(new JButton(new AbstractAction(ResourceManager.getString("about.close.text")) {

					@Override
					public void actionPerformed(final ActionEvent e) {
						// close about dialog
						dispose();
					}
				}));
			}
		}, BorderLayout.SOUTH);
	}
}
