package io.samepage.traces.api;

/**
 *
 * @author pcipov
 */
public interface Trace {
	
	interface Event {
		void end();
	}
	
	Event start(String name);
	Event start(String name, Object v1);
	Event start(String name, Object v1, Object v2);
	Event start(String name, Object v1, Object v2, Object v3);
	Event start(String name, Object v1, Object v2, Object v3, Object v4);
	Event start(String name, Object v1, Object v2, Object v3, Object v4, Object v5);	
	Event start(String name, Object ... values);
	
	void event(String name);
	void event(String name, Object v1);
	void event(String name, Object v1, Object v2);
	void event(String name, Object v1, Object v2, Object v3);
	void event(String name, Object v1, Object v2, Object v3, Object v4);
	void event(String name, Object v1, Object v2, Object v3, Object v4, Object v5);	
	void event(String name, Object ... values);
}
