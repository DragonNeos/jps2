package com.jps2.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.AWTGLCanvas;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.jps2.ResourceManager;

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

				@Override
				protected void paintGL() {
					GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
					GL11.glMatrixMode(GL11.GL_PROJECTION_MATRIX);
					GL11.glLoadIdentity();
					GL11.glOrtho(0, getWidth(), 0, getHeight(), 1, -1);
					GL11.glMatrixMode(GL11.GL_MODELVIEW_MATRIX);

					GL11.glPushMatrix();
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
		new Thread() {
			{
				setDaemon(true);
			}

			@Override
			public void run() {
				while (!isInterrupted()) {
					if (canvas.isVisible()) {
						canvas.repaint();
					}
					Display.sync(60);
				}
			}
		}.start();
		setVisible(true);
	}

	// construct menu
	private void makeMenu() {
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

	// contruct toolbar
	private void makeToolBar() {
		if (Utilities.isMac()) {
			// TODO - Mac unifedToolBar from http://explodingpixels.wordpress.com/
		} else {
			final JToolBar toolBar = new JToolBar();

			toolBar.setFloatable(false);

			toolBar.add(new AbstractAction(null, ResourceManager.getIcon("/icons/16x16/play.png")) {

				@Override
				public void actionPerformed(final ActionEvent e) {
					// TODO Auto-generated method stub

				}
			});

			toolBar.add(new AbstractAction(null, ResourceManager.getIcon("/icons/16x16/pause.png")) {

				@Override
				public void actionPerformed(final ActionEvent e) {
					// TODO Auto-generated method stub

				}
			});

			toolBar.add(new AbstractAction(null, ResourceManager.getIcon("/icons/16x16/stop.png")) {

				@Override
				public void actionPerformed(final ActionEvent e) {
					// TODO Auto-generated method stub

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
}
