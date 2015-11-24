package io.samepage.traces.vizu;

import io.samepage.traces.api.Trace;
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
	private final ConcurrentLinkedQueue<SerializedEvent> trace;

	public VizuTrace() {
		this.trace = new ConcurrentLinkedQueue<SerializedEvent>();
	}
	
	@Override
	public Event start(String name) {
		return createEndEvent(name, EMPTY);
	}

	@Override
	public Event start(String name, Object v1) {
		return createEndEvent(name, new Object[] {v1});
	}

	@Override
	public Event start(String name, Object v1, Object v2) {
		return createEndEvent(name, new Object[] {v1, v2});
	}

	@Override
	public Event start(String name, Object v1, Object v2, Object v3) {
		return createEndEvent(name, new Object[] {v1, v2, v3});
	}

	@Override
	public Event start(String name, Object v1, Object v2, Object v3, Object v4) {
		return createEndEvent(name, new Object[] {v1, v2, v3, v4});
	}
	
	@Override
	public Event start(String name, Object v1, Object v2, Object v3, Object v4, Object v5) {
		return createEndEvent(name, new Object[] {v1, v2, v3, v4, v5});
	}
	
	@Override
	public Event start(String name, Object... values) {
		return createEndEvent(name, values);
	}

	private Event createEndEvent(String name, Object [] values) {
		final SimpleEvent simple = simple(name, values);
		
		return new Trace.Event() {
			
			@Override
			public void end() {
				trace.add(new EndEvent(simple, System.currentTimeMillis(), Thread.currentThread().getName()));
			}
		};
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

	private SimpleEvent simple(String name, Object[] values) {
		SimpleEvent event = new SimpleEvent(name, values, Thread.currentThread().getName(), System.currentTimeMillis());
		trace.add(event);
		return event;
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
