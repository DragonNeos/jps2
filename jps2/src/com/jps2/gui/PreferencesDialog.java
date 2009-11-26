package com.jps2.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import com.explodingpixels.macwidgets.MacUtils;
import com.explodingpixels.macwidgets.UnifiedToolBar;
import com.explodingpixels.macwidgets.plaf.PreferencesTabBarButtonUI;
import com.jps2.gui.preferences.JoystickPreferencesPanel;
import com.jps2.gui.preferences.SoundPreferencesPanel;
import com.jps2.gui.preferences.VideoPreferencesPanel;
import com.jps2.util.ResourceManager;
import com.jps2.util.SystemInfo;

/**
 * Preferences dialog.
 * 
 * @author dyorgio
 */
@SuppressWarnings("serial")
public class PreferencesDialog {
	/**
	 * Contruct a preferences dialog.
	 */
	public PreferencesDialog() {
		Window window;
		if (SystemInfo.isMac()) {
			window = new JFrame(ResourceManager.getString("preferences.title"));
			// dispose this dialog if presse close button
			((JFrame) window).setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			// no resizable
			((JFrame) window).setResizable(false);
		} else {
			window = new JDialog(MainWindow.getInstance(), ResourceManager.getString("preferences.title"), true);
			// dispose this dialog if presse close button
			((JDialog) window).setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			// no resizable
			((JDialog) window).setResizable(false);
		}

		// set dialog icon
		window.setIconImage(ResourceManager.getIcon("/icons/16x16/preferences.png").getImage());
		// contruct dialog UI
		initComponents(window);
		window.setMinimumSize(new Dimension(300, 300));
		// adjust window preferred size
		window.pack();
		// center of screen
		window.setLocationRelativeTo(null);
		// show
		window.setVisible(true);
	}

	private final void initComponents(final Window window) {
		final CardLayout layout = new CardLayout();
		final JPanel contentPane = new JPanel(layout);

		final ButtonGroup buttonGroup = new ButtonGroup();

		final JToggleButton videoButton = new JToggleButton(ResourceManager.getString("preferences.video"), ResourceManager.getIcon("/icons/48x48/video.png"), true);
		prepareToolBarButton(videoButton);
		buttonGroup.add(videoButton);
		videoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				layout.show(contentPane, "VIDEO");
			}
		});

		final JToggleButton joystickButton = new JToggleButton(ResourceManager.getString("preferences.joystick"), ResourceManager.getIcon("/icons/48x48/joystick.png"), true);
		prepareToolBarButton(joystickButton);
		buttonGroup.add(joystickButton);
		joystickButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				layout.show(contentPane, "JOYSTICK");
			}
		});

		final JToggleButton soundButton = new JToggleButton(ResourceManager.getString("preferences.sound"), ResourceManager.getIcon("/icons/48x48/sound.png"), true);
		prepareToolBarButton(soundButton);
		buttonGroup.add(soundButton);
		soundButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				layout.show(contentPane, "SOUND");
			}
		});

		// if is mac
		if (SystemInfo.isMac()) {
			// adjust for leopard, if necessary
			MacUtils.makeWindowLeopardStyle(((JFrame) window).getRootPane());
			((JFrame) window).getRootPane().putClientProperty("Window.style", "small");

			final UnifiedToolBar toolBar = new UnifiedToolBar();
			toolBar.installWindowDraggerOnWindow(window);
			final Box layoutBox = Box.createHorizontalBox();

			videoButton.putClientProperty("JButton.buttonType", "textured");
			layoutBox.add(videoButton);
			joystickButton.putClientProperty("JButton.buttonType", "recessed");
			layoutBox.add(joystickButton);
			soundButton.putClientProperty("JButton.buttonType", "recessed");
			layoutBox.add(soundButton);

			toolBar.addComponentToLeft(layoutBox);

			window.add(toolBar.getComponent(), BorderLayout.NORTH);
		} else {
			final JToolBar toolBar = new JToolBar();

			toolBar.setBackground(Color.WHITE);
			toolBar.setFloatable(false);

			toolBar.add(videoButton);
			toolBar.add(joystickButton);
			toolBar.add(soundButton);

			window.add(toolBar, BorderLayout.NORTH);
		}

		contentPane.add(new VideoPreferencesPanel(), "VIDEO");
		contentPane.add(new JoystickPreferencesPanel(), "JOYSTICK");
		contentPane.add(new SoundPreferencesPanel(), "SOUND");

		window.add(contentPane);

		// add ok/cancel button in a panel on south
		window.add(new JPanel(new FlowLayout(FlowLayout.TRAILING)) {
			{
				add(new JButton(new AbstractAction(ResourceManager.getString("preferences.ok.text")) {

					@Override
					public void actionPerformed(final ActionEvent e) {
						// save config
						save();
						// close config dialog
						window.dispose();
					}
				}));
				add(new JButton(new AbstractAction(ResourceManager.getString("preferences.cancel.text")) {

					@Override
					public void actionPerformed(final ActionEvent e) {
						// close config dialog
						window.dispose();
					}
				}));
			}
		}, BorderLayout.SOUTH);
	}

	private void prepareToolBarButton(final JToggleButton videoButton) {
		if (SystemInfo.isMac()) {
			videoButton.setUI(new PreferencesTabBarButtonUI());
		} else {
			videoButton.setVerticalTextPosition(SwingConstants.BOTTOM);
			videoButton.setHorizontalTextPosition(SwingConstants.CENTER);
			videoButton.setContentAreaFilled(true);
			videoButton.setMargin(new Insets(2, 20, 2, 20));
			videoButton.setFocusPainted(false);
		}
	}

	private final void save() {

	}
}
