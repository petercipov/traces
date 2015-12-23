package com.petercipov.traces.api;

/**
 *
 * @author pcipov
 */
public interface Trace {
	
	interface Event {
		void end();
	}
	
	boolean isDebugEnabled();
	boolean isInfoEnabled();
	boolean isWarnEnabled();
	boolean isErrorEnabled();
	
	Event start(Level level, String name);
	Event start(Level level, String name, Object ... values);
	
	void event(Level level, String name);	
	void event(Level level, String name, Object ... values);
	
	Event start(String name);
	Event start(String name, Object ... values);
	
	void event(String name);
	void event(String name, Object ... values);
}
