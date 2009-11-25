import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.swing.UIManager;

import com.jps2.gui.MainWindow;
import com.jps2.util.ConfigManager;
import com.jps2.util.SystemInfo;

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

		try {
			// load plataform natives
			if (SystemInfo.isWindows()) {
				extractWindowsNatives();
			} else
				if (SystemInfo.isMac()) {

				} else
					if (SystemInfo.isSolaris()) {

					} else {

					}
		} catch (final Exception e) {
			// ignore path erros
		}

		// init config
		ConfigManager.initialize();
		// get system info
		SystemInfo.getSystemInfo();
		// init GUI
		MainWindow.getInstance();
	}

	private static final void extractWindowsNatives() {
		extractNative("jinput-dx8.dll");
		extractNative("jinput-raw.dll");
		if (SystemInfo.is32Bits()) {
			extractNative("lwjgl.dll");
			extractNative("OpenAL32.dll");
		} else {
			extractNative("lwjgl64.dll");
			extractNative("OpenAL64.dll");
		}
	}

	private static final void extractNative(final String name) {
		try {
			// Finds a stream to the dll. Change path/class if necessary
			final InputStream inputStream = JPS2.class.getResource("/native/" + name).openStream();

			if (inputStream != null) {

				System.err.println("loading " + name);
				// Change name if necessary
				final File temporaryDll = new File(name);
				final FileOutputStream outputStream = new FileOutputStream(temporaryDll);
				final byte[] array = new byte[8192];
				for (int i = inputStream.read(array); i != -1; i = inputStream.read(array)) {
					outputStream.write(array, 0, i);
				}
				outputStream.close();

				temporaryDll.deleteOnExit();
			}
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}
}
