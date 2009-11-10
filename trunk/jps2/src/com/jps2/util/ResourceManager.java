package com.jps2.util;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

/**
 * Resource manager.
 * 
 * @author dyorgio
 */
public final class ResourceManager {

	private static final ResourceBundle	resourceBundleDefault	= ResourceBundle.getBundle("com.jps2.resources.lang", Locale.US);
	private static final ResourceBundle	resourceBundle			= ResourceBundle.getBundle("com.jps2.resources.lang");
	{

	}

	private ResourceManager() {
	}

	public static final String getString(final String key) {
		return resourceBundle.containsKey(key) ? resourceBundle.getString(key) : resourceBundleDefault.getString(key);
	}

	public static final ImageIcon getIcon(final String icon) {
		return new ImageIcon(ResourceManager.class.getResource(icon));
	}
}
