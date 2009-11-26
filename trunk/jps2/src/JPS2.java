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
			// ignore UIManager errors
		}

		try {
			// extract plataform natives
			if (SystemInfo.isWindows()) {
				extractWindowsNatives();
			} else
				if (SystemInfo.isMac()) {
					extractMacNatives();
				} else
					if (SystemInfo.isSolaris()) {

					} else {
						extractLinuxNatives();
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

	private static final void extractMacNatives() {
		if (extractNative("libjinput-osx.jnilib")) {
			if (extractNative("liblwjgl.jnilib")) {
				extractNative("openal.dylib");
			}
		}
	}

	private static final void extractWindowsNatives() {
		if (extractNative("jinput-dx8.dll")) {
			if (extractNative("jinput-raw.dll")) {
				if (SystemInfo.is32Bits()) {
					if (extractNative("lwjgl.dll")) {
						extractNative("OpenAL32.dll");
					}
				} else {
					if (extractNative("lwjgl64.dll")) {
						extractNative("OpenAL64.dll");
					}
				}
			}
		}
	}

	private static final void extractLinuxNatives() {
		if (SystemInfo.is32Bits()) {
			if (extractNative("liblwjgl.so")) {
				if (extractNative("libjinput-linux.so")) {
					extractNative("libopenal.so");
				}
			}

		} else {
			if (extractNative("liblwjgl64.so")) {
				if (extractNative("libjinput-linux64.so")) {
					extractNative("libopenal64.so");
				}
			}
		}
	}

	private static final boolean extractNative(final String name) {
		return extractNative("", name);
	}

	private static final boolean extractNative(final String dir, final String name) {
		try {
			// Finds a stream to the dll. Change path/class if necessary
			final InputStream inputStream = JPS2.class.getResourceAsStream("/native/" + dir + name);

			if (inputStream != null) {

				System.err.println("extracting /native/" + name);
				final File temporaryDll = new File(name);
				if (!temporaryDll.exists()) {
					final FileOutputStream outputStream = new FileOutputStream(temporaryDll);
					final byte[] array = new byte[8192];
					for (int i = inputStream.read(array); i != -1; i = inputStream.read(array)) {
						outputStream.write(array, 0, i);
					}
					outputStream.close();

					temporaryDll.deleteOnExit();
				}
				return true;
			}
		} catch (final Throwable e) {
			e.printStackTrace();
		}
		return false;
	}
}
