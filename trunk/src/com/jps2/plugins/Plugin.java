package com.jps2.plugins;

public interface Plugin {
	String getName();

	PluginType getType();
	
	boolean test(StringBuffer messages);
	
	boolean open();
	
	boolean close();
}
