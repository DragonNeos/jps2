package com.jps2.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import org.jps2.mac.MacApplication;
import org.lwjgl.opengl.AWTGLCanvas;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.explodingpixels.macwidgets.MacUtils;
import com.explodingpixels.macwidgets.UnifiedToolBar;
import com.jps2.core.Emulator;
import com.jps2.core.EmulatorStateListener;
import com.jps2.util.ResourceManager;
import com.jps2.util.SystemInfo;

/**
 * JPS2's main window.
 * 
 * @author dyorgio
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private static MainWindow	instance;

	private static AWTGLCanvas	canvas;

	private boolean				fullscreen	= false;

	private JComponent			toolBar;

	private MainWindow() {
		super("JPS2 - Java PS2 emulator");
		try {
			setIconImages(Arrays.asList(ResourceManager.getIcon("/icons/16x16/joystick.png").getImage(),/**/
			ResourceManager.getIcon("/icons/32x32/joystick.png").getImage(),/**/
			ResourceManager.getIcon("/icons/48x48/joystick.png").getImage(),/**/
			ResourceManager.getIcon("/icons/128x128/joystick.png").getImage(),/**/
			ResourceManager.getIcon("/icons/256x256/joystick.png").getImage())/**/);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setMinimumSize(new Dimension(300, 240));
			if (SystemInfo.isMac()) {
				try {
					new MacApplication(this, getClass().getDeclaredMethod("about"), getClass().getDeclaredMethod("preferences"), getClass().getDeclaredMethod("close"),
							ResourceManager.getIcon("/icons/256x256/joystick.png").getImage());
				} catch (final Exception e) {
					throw new RuntimeException(e);
				}
			}
			makeMenu();
			makeToolBar();
			canvas = new AWTGLCanvas() {
				{
					setPreferredSize(new Dimension(256, 256));
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
						resized = false;
						GL11.glMatrixMode(GL11.GL_PROJECTION_MATRIX);
						GL11.glLoadIdentity();
						GL11.glOrtho(0, 256, 0, 256, 1, -1);
						GL11.glMatrixMode(GL11.GL_MODELVIEW_MATRIX);
					}
					GL11.glPushMatrix();
					{
						// center square according to screen size
						GL11.glTranslatef(256f, 256f, (getHeight() / 256f) - 1);

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

			add(canvas);
			pack();
			new Thread("Repaint Process") {
				{
					setDaemon(true);
				}

				@Override
				public void run() {
					while (!isInterrupted()) {
						if (Emulator.getInstance().isEmulating()) {
							if (canvas.isVisible()) {
								canvas.repaint();
							}
							if (Emulator.EE != null && Emulator.EE.eeCpu.counters[0].mode.isCounting() && (Emulator.EE.eeCpu.counters[0].mode.getClockSource() == 0x3)){
								Emulator.EE.eeCpu.counters[0].count++;	
							}
							Display.sync(60);
						} else {
							try {
								sleep(100);
							} catch (final InterruptedException e) {
								interrupt();
							}
						}
					}
				}
			}.start();
			setLocationRelativeTo(null);
			setVisible(true);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void about() {
		new AboutDialog();
	}

	public void close() {
		dispose();
	}
	
	@Override
	public void dispose() {
		Emulator.getInstance().stop();
		super.dispose();
	}

	public void preferences() {
		if (Emulator.getInstance().isEmulating()) {
			Emulator.getInstance().stop();
		}
		new PreferencesDialog();
	}

	// construct menu
	private void makeMenu() {
		final JMenuBar menuBar = new JMenuBar();

		final JMenu fileMenu = new JMenu(ResourceManager.getString("menu.file"));
		// if not is mac
		if (!SystemInfo.isMac()) {

			final JMenuItem exitMenu = new JMenuItem(ResourceManager.getString("menu.file.exit"), ResourceManager.getIcon("/icons/16x16/exit.png"));
			exitMenu.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					close();
				}
			});
			fileMenu.add(exitMenu);

			menuBar.add(fileMenu);

			final JMenu configMenu = new JMenu(ResourceManager.getString("menu.config"));

			final JMenuItem preferencesMenuItem = new JMenuItem(ResourceManager.getString("menu.config.preferences"), ResourceManager.getIcon("/icons/16x16/preferences.png"));
			preferencesMenuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					preferences();
				}
			});
			configMenu.add(preferencesMenuItem);

			menuBar.add(configMenu);
		} else {
			menuBar.add(fileMenu);
		}
		final JMenu helpMenu = new JMenu(ResourceManager.getString("menu.help"));

		// if not is mac
		if (!SystemInfo.isMac()) {
			final JMenuItem aboutMenuItem = new JMenuItem(ResourceManager.getString("menu.help.about"), ResourceManager.getIcon("/icons/16x16/about.png"));
			aboutMenuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					about();
				}
			});
			helpMenu.add(aboutMenuItem);
		}

		menuBar.add(helpMenu);

		setJMenuBar(menuBar);
	}

	public final void fullscreenSwitch() {
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		if (fullscreen) {
			if (toolkit.isAlwaysOnTopSupported()) {
				setAlwaysOnTop(false);
			}
			getGraphicsConfiguration().getDevice().setFullScreenWindow(null);
			dispose();
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					setUndecorated(false);
//					getJMenuBar().setVisible(true);
//					toolBar.setVisible(true);
					setVisible(true);
				}
			});
		} else {
			dispose();
//			getJMenuBar().setVisible(false);
//			toolBar.setVisible(false);
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					if (toolkit.isAlwaysOnTopSupported()) {
						setAlwaysOnTop(false);
					}
					setUndecorated(true);
					getGraphicsConfiguration().getDevice().setFullScreenWindow(MainWindow.this);
					getGraphicsConfiguration().getDevice().setDisplayMode(new DisplayMode(640, 480, 32, 0));
				}
			});
		}

		fullscreen = !fullscreen;
	}

	public final boolean isFullscreen() {
		return fullscreen;
	}

	// contruct toolbar
	private void makeToolBar() {

		final JButton playButton = new JButton(ResourceManager.getIcon("/icons/16x16/play.png"));
		playButton.setFocusable(false);
		final JToggleButton pauseButton = new JToggleButton(ResourceManager.getIcon("/icons/16x16/pause.png"));
		pauseButton.setEnabled(false);
		pauseButton.setFocusable(false);
		final JButton stopButton = new JButton(ResourceManager.getIcon("/icons/16x16/stop.png"));
		stopButton.setEnabled(false);
		stopButton.setFocusable(false);
		final JToggleButton fullscreenButton = new JToggleButton(ResourceManager.getIcon("/icons/16x16/fullscreen.png"));
		fullscreenButton.setFocusable(false);
		playButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				playButton.setEnabled(false);
				stopButton.setEnabled(true);
				pauseButton.setEnabled(true);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						Emulator.getInstance().start();
					}
				});
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
				playButton.setEnabled(true);
				stopButton.setEnabled(false);
				pauseButton.setEnabled(false);
				pauseButton.setSelected(false);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						Emulator.getInstance().stop();
					}
				});
			}
		});

		fullscreenButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						fullscreenSwitch();
					}
				});
			}
		});

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
				// throwable.printStackTrace();
			}
		});
		// if is mac
		if (SystemInfo.isMac()) {
			// adjust for leopard, if necessary
			MacUtils.makeWindowLeopardStyle(getRootPane());

			final UnifiedToolBar toolBar = new UnifiedToolBar();
			toolBar.installWindowDraggerOnWindow(this);
			final Box layoutBox = Box.createHorizontalBox();

			playButton.putClientProperty("JButton.buttonType", "segmentedTextured");
			playButton.putClientProperty("JButton.segmentPosition", "first");
			layoutBox.add(playButton);
			pauseButton.putClientProperty("JButton.buttonType", "segmentedTextured");
			pauseButton.putClientProperty("JButton.segmentPosition", "middle");
			layoutBox.add(pauseButton);
			stopButton.putClientProperty("JButton.buttonType", "segmentedTextured");
			stopButton.putClientProperty("JButton.segmentPosition", "last");
			layoutBox.add(stopButton);
			toolBar.addComponentToLeft(layoutBox);
			fullscreenButton.putClientProperty("JButton.buttonType", "textured");
			toolBar.addComponentToRight(fullscreenButton);
			add(toolBar.getComponent(), BorderLayout.NORTH);
			this.toolBar = toolBar.getComponent();
			this.toolBar.setBorder(BorderFactory.createEmptyBorder());
		} else {
			final JToolBar toolBar = new JToolBar();

			toolBar.setFloatable(false);
			toolBar.add(playButton);
			toolBar.add(pauseButton);
			toolBar.add(stopButton);
			toolBar.addSeparator();
			toolBar.add(fullscreenButton);
			add(toolBar, BorderLayout.NORTH);
			this.toolBar = toolBar;
		}
	}

	public static AWTGLCanvas getCanvas() {
		return canvas;
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
