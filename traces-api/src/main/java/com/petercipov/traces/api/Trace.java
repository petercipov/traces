package com.petercipov.traces.api;

/**
 *
 * @author pcipov
 */
public interface Trace {
	
	interface Event {
		void end();
	}

	boolean isEnabled(Object marker);

	String getUUID();
	
	Event start(String message);
	Event start(String message, Object ... values);
	Event start(Object marker, String message);
	Event start(Object marker, String message, Object ... values);
	
	void event(String message);
	void event(String message, Object ... values);
	void event(Object marker, String message);
	void event(Object marker, String message, Object ... values);
	
	Trace fork(String message);
	Trace fork(String message, Object ... values);
	Trace fork(Object marker, String message);
	Trace fork(Object marker, String message, Object ... values);
	
	void join(Trace trace, String message);
	void join(Trace trace, String message, Object ... values);
	void join(Trace trace, Object marker, String message);
	void join(Trace trace, Object marker, String message, Object ... values);
}