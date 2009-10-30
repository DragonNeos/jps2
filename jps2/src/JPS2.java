import javax.swing.UIManager;

import com.jps2.gui.MainWindow;

/**
 * Application entry point for GUI.<br>
 * Is in default package because in Mac the name of application is the package +
 * class name
 * 
 * @author dyorgio
 * 
 */
public class JPS2 {

	public static void main(final String[] args) {
		try {
			// define system look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (final Exception e) {
			// ignore UIManager erros
		}
		// init GUI
		MainWindow.getInstance();
	}

}
