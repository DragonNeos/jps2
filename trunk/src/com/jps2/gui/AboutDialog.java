package com.jps2.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jps2.ResourceManager;

@SuppressWarnings("serial")
public class AboutDialog extends JDialog {
	public AboutDialog() {
		super(MainWindow.getInstance(), ResourceManager.getString("about.title"), true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIconImage(ResourceManager.getIcon("/icons/16x16/about.png").getImage());
		initComponents();
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private final void initComponents() {
		add(new JLabel(ResourceManager.getIcon("/icons/about_logo.png")), BorderLayout.NORTH);
		add(new JPanel() {
			{
				add(new JButton(new AbstractAction(ResourceManager.getString("about.close.text")) {

					@Override
					public void actionPerformed(final ActionEvent e) {
						dispose();
					}
				}));
			}
		}, BorderLayout.SOUTH);
	}
}
