package com.jps2.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.AWTGLCanvas;
import org.lwjgl.opengl.GL11;

import com.jps2.core.Emulator;
import com.jps2.core.EmulatorStateListener;
import com.jps2.util.ResourceManager;

/**
 * JPS2's main window.
 * 
 * @author dyorgio
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private static MainWindow	instance;

	private final AWTGLCanvas	canvas;

	private MainWindow() {
		super("JPS2 - Java PS2 emulator");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(300, 240));
		makeMenu();
		makeToolBar();
		try {
			canvas = new AWTGLCanvas() {
				{
					setPreferredSize(new Dimension(512, 512));
				}
				float	angle	= 0;
				boolean	resized	= true;

				@Override
				protected void processComponentEvent(final java.awt.event.ComponentEvent e) {
					if (e.getID() == ComponentEvent.COMPONENT_RESIZED) {
						resized = true;
					}
					super.processComponentEvent(e);
				};

				@Override
				protected void paintGL() {
					GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
					if (resized) {
						// resized = false;
						GL11.glMatrixMode(GL11.GL_PROJECTION_MATRIX);
						GL11.glLoadIdentity();
						GL11.glOrtho(0, getWidth(), 0, getHeight(), 1, -1);
						GL11.glMatrixMode(GL11.GL_MODELVIEW_MATRIX);
					}
					GL11.glPushMatrix();
					GL11.glScaled(getWidth() / 512d, getHeight() / 512d, 0);
					{
						// center square according to screen size
						GL11.glTranslatef(256f, 256f, 0.0f);

						// rotate square according to angle
						GL11.glRotatef(angle, 0, 0, 1.0f);
						angle += 0.2f;

						// render the square
						GL11.glBegin(GL11.GL_QUADS);
						{
							GL11.glVertex2i(-50, -50);
							GL11.glVertex2i(50, -50);
							GL11.glVertex2i(50, 50);
							GL11.glVertex2i(-50, 50);
						}
						GL11.glEnd();
					}
					GL11.glPopMatrix();
					try {
						// INFO - Show OpenGL graphics in canvas, important
						swapBuffers();
					} catch (final Exception e) {
						e.printStackTrace();
					}
				}
			};
		} catch (final LWJGLException e) {
			throw new RuntimeException(e);
		}
		// canvas.setBackground(Color.BLACK);
		add(canvas);
		pack();
		// new Thread("Repainter Process") {
		// {
		// setDaemon(true);
		// }
		//
		// @Override
		// public void run() {
		// while (!isInterrupted()) {
		// if (canvas.isVisible()) {
		// canvas.repaint();
		// }
		// Display.sync(600);
		// }
		// }
		// }.start();
		setVisible(true);
	}

	// construct menu
	private void makeMenu() {
		// if is mac
		if (Utilities.isMac()) {
			// TODO - Mac unifedToolBar from
			// http://explodingpixels.wordpress.com/
		} else {
			final JMenuBar menuBar = new JMenuBar();

			final JMenu fileMenu = new JMenu(ResourceManager.getString("menu.file"));

			final JMenuItem exitMenu = new JMenuItem(ResourceManager.getString("menu.file.exit"), ResourceManager.getIcon("/icons/16x16/exit.png"));
			exitMenu.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					dispose();
				}
			});
			fileMenu.add(exitMenu);

			menuBar.add(fileMenu);

			final JMenu configMenu = new JMenu(ResourceManager.getString("menu.config"));

			final JMenuItem pluginsMenuItem = new JMenuItem(ResourceManager.getString("menu.config.plugins"), ResourceManager.getIcon("/icons/16x16/config.png"));
			pluginsMenuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					new PluginConfigDialog();
				}
			});
			configMenu.add(pluginsMenuItem);

			menuBar.add(configMenu);

			final JMenu helpMenu = new JMenu(ResourceManager.getString("menu.help"));

			final JMenuItem aboutMenuItem = new JMenuItem(ResourceManager.getString("menu.help.about"), ResourceManager.getIcon("/icons/16x16/about.png"));
			aboutMenuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					new AboutDialog();
				}
			});
			helpMenu.add(aboutMenuItem);

			menuBar.add(helpMenu);

			setJMenuBar(menuBar);
		}
	}

	// contruct toolbar
	private void makeToolBar() {
		// if is mac
		if (false && Utilities.isMac()) {
			// TODO - Mac unifedToolBar from
			// http://explodingpixels.wordpress.com/
		} else {
			final JToolBar toolBar = new JToolBar();

			toolBar.setFloatable(false);

			final JButton playButton = new JButton(ResourceManager.getIcon("/icons/16x16/play.png"));
			final JButton pauseButton = new JButton(ResourceManager.getIcon("/icons/16x16/pause.png"));
			pauseButton.setEnabled(false);
			final JButton stopButton = new JButton(ResourceManager.getIcon("/icons/16x16/stop.png"));
			stopButton.setEnabled(false);
			playButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					Emulator.getInstance().start();
					playButton.setEnabled(false);
					stopButton.setEnabled(true);
					pauseButton.setEnabled(true);
				}
			});

			pauseButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					Emulator.getInstance().pause(!Emulator.getInstance().isPaused());
				}
			});

			stopButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					Emulator.getInstance().stop();
					playButton.setEnabled(true);
					stopButton.setEnabled(false);
					pauseButton.setEnabled(false);
				}
			});

			toolBar.add(playButton);
			toolBar.add(pauseButton);
			toolBar.add(stopButton);

			Emulator.getInstance().setListener(new EmulatorStateListener() {

				@Override
				public void stopped() {
					playButton.setEnabled(true);
					stopButton.setEnabled(false);
					pauseButton.setEnabled(false);
				}

				@Override
				public void started() {
					playButton.setEnabled(false);
					stopButton.setEnabled(true);
					pauseButton.setEnabled(true);
				}

				@Override
				public void paused(final boolean pause) {
				}

				@Override
				public void error(final Throwable throwable) {
					throwable.printStackTrace();
				}
			});

			add(toolBar, BorderLayout.NORTH);
		}
	}

	/**
	 * Get instance of MainWindow, only one is permited.
	 * 
	 * @return The unique instance of MainWindow
	 */
	public static synchronized final MainWindow getInstance() {
		return instance == null ? instance = new MainWindow() : instance;
	}

	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (final Exception e) {
			// ignore UIManager erros
		}
		MainWindow.getInstance();
	}
}
