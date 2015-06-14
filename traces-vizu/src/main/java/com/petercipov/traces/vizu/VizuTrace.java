package com.petercipov.traces.vizu;

import com.petercipov.traces.api.Event;
import com.petercipov.traces.api.Trace;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author pcipov
 */
public class VizuTrace implements Trace {
	
	private static final Object[] EMPTY = new Object[0];
	private final ConcurrentLinkedQueue<Event> trace;

	public VizuTrace() {
		this.trace = new ConcurrentLinkedQueue<Event>();
	}
	
	@Override
	public Event start(String name) {
		return createEvent(name, EMPTY);
	}

	@Override
	public Event start(String name, Object v1) {
		return createEvent(name, new Object[] {v1});
	}

	@Override
	public Event start(String name, Object v1, Object v2) {
		return createEvent(name, new Object[] {v1, v2});
	}

	@Override
	public Event start(String name, Object v1, Object v2, Object v3) {
		return createEvent(name, new Object[] {v1, v2, v3});
	}

	@Override
	public Event start(String name, Object v1, Object v2, Object v3, Object v4) {
		return createEvent(name, new Object[] {v1, v2, v3, v4});
	}
	
	@Override
	public Event start(String name, Object v1, Object v2, Object v3, Object v4, Object v5) {
		return createEvent(name, new Object[] {v1, v2, v3, v4, v5});
	}
	
	@Override
	public Event start(String name, Object... values) {
		return createEvent(name, values);
	}

	private Event createEvent(String name, Object [] values) {
		Event event = new Event(trace.size(), name, values);
		trace.add(event);
		return event;
	}

		@Override
	public void event(String name) {
		simple(name, EMPTY);
	}

	@Override
	public void event(String name, Object v1) {
		simple(name, new Object[] {v1});
	}

	@Override
	public void event(String name, Object v1, Object v2) {
		simple(name, new Object[] {v1, v2});
	}

	@Override
	public void event(String name, Object v1, Object v2, Object v3) {
		simple(name, new Object[] {v1, v2, v3});
	}

	@Override
	public void event(String name, Object v1, Object v2, Object v3, Object v4) {
		simple(name, new Object[] {v1, v2, v3, v4});
	}

	@Override
	public void event(String name, Object v1, Object v2, Object v3, Object v4, Object v5) {
		simple(name, new Object[] {v1, v2, v3, v4, v5});
	}

	@Override
	public void event(String name, Object... values) {
		simple(name, values);
	}

	private void simple(String name, Object[] values) {
		trace.add(new SimpleEvent(trace.size(), name, values));
	}
	
	
	@Override
	public void end(Event event) {
		trace.add(new End(trace.size(),event));
	}

	@Override
	public String toString() {
		try {
			StringWriter sw = new StringWriter();
			write(sw);
			return sw.toString();
		} catch(Exception ex) {
			return ex.getMessage();
		}
	}

	public void write(Writer writer) throws IOException {
		VizuFormatter.format(trace, writer);
	}
}
