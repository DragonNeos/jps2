package com.jps2.plugins;

/**
 * Plugin interface.
 * 
 * @author dyorgio
 */
public interface Plugin {
	/**
	 * Get plugin name.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Get plugin type.
	 * 
	 * @return
	 * @see PluginType
	 */
	PluginType getType();

	/**
	 * Test plugin.
	 * 
	 * @param messages
	 *            Buffer for messages
	 * @return <code>true</code> if plugin is ok.
	 */
	boolean test(StringBuffer messages);

	/**
	 * Open plugin.
	 * 
	 * @return <code>true</code> if open ok.
	 */
	boolean open();

	/**
	 * Close plugin.
	 * 
	 * @return <code>true</code> if close ok.
	 */
	boolean close();

	/**
	 * Get version of plugin
	 * 
	 * @return
	 */
	int[] getVersion();
}
