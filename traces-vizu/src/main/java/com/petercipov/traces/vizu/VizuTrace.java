package com.petercipov.traces.vizu;

import com.petercipov.traces.api.FinishableTrace;
import com.petercipov.traces.api.Level;
import com.petercipov.traces.api.NoopTrace;
import com.petercipov.traces.api.Trace;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 *
 * @author pcipov
 */
public class VizuTrace implements FinishableTrace {
	private static final Logger LOGGER = LoggerFactory.getLogger(VizuTrace.class);
	private static final Marker MARKER = MarkerFactory.getMarker("Trace");
	
	private static final Object[] EMPTY = new Object[0];
	private final ConcurrentLinkedQueue<SerializedEvent> trace;
	private final Level expectedLevel;

	public VizuTrace(Level expectedLevel) {
		this.trace = new ConcurrentLinkedQueue<SerializedEvent>();
		this.expectedLevel = expectedLevel;
	}
	
	@Override
	public Event start(String name) {
		return start(Level.INFO, name);
	}
	
	@Override
	public Event start(String name, Object... values) {
		return start(Level.INFO, name, values);
	}
	
	@Override
	public Event start(Level level, String name) {
		if (! expectedLevel.enables(level)) { return  NoopTrace.EVENT; }
		return createEndEvent(name, EMPTY);
	}
	
	@Override
	public Event start(Level level, String name, Object... values) {
		if (! expectedLevel.enables(level)) { return  NoopTrace.EVENT; }
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
		event(Level.INFO, name);
	}

	@Override
	public void event(String name, Object... values) {
		event(Level.INFO, name, values);
	}
	
		@Override
	public void event(Level level, String name) {
		if (! expectedLevel.enables(level)) { return; }
		simple(name, EMPTY);
	}

	@Override
	public void event(Level level, String name, Object... values) {
		if (! expectedLevel.enables(level)) { return; }
		simple(name, values);
	}

	private SimpleEvent simple(String name, Object[] values) {
		SimpleEvent event = new SimpleEvent(name, values, Thread.currentThread().getName(), System.currentTimeMillis());
		trace.add(event);
		return event;
	}

	public void write(Writer writer) throws IOException {
		VizuFormatter.format(trace, writer);
	}

	@Override
	public boolean isDebugEnabled() {
		return expectedLevel.enables(Level.DEBUG);
	}

	@Override
	public boolean isInfoEnabled() {
		return expectedLevel.enables(Level.INFO);
	}

	@Override
	public boolean isWarnEnabled() {
		return expectedLevel.enables(Level.WARN);
	}

	@Override
	public boolean isErrorEnabled() {
		return expectedLevel.enables(Level.ERROR);
	}

	@Override
	public void finish() {
		if (LOGGER.isInfoEnabled(MARKER)) {
			event("Trace finished");
			StringWriter sw = new StringWriter();
			try {
				this.write(sw);
			} catch(Exception ex) {
				throw new IllegalStateException("Failed to finish trace", ex);
			}

			LOGGER.info(MARKER, sw.toString());
		}
	}
}
