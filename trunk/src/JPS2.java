import javax.swing.UIManager;

import com.jps2.gui.MainWindow;

/**
 * Application entry point for GUI.
 * 
 * @author dyorgio
 * 
 */
public class JPS2 {

	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (final Exception e) {
			// ignore UIManager erros
		}
		MainWindow.getInstance();
	}

}
