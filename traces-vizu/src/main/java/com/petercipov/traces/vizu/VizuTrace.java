package com.petercipov.traces.vizu;

import com.petercipov.traces.api.*;

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
	private static final Event EVENT = new Event() {

		@Override
		public void end() {
			//NOOP
		}
	};
	private static final NoopTraceFactory NOOP_TRACE_FACTORY = new NoopTraceFactory();

	private static final Object[] EMPTY = new Object[0];
	private final ConcurrentLinkedQueue<SerializedEvent> trace;
	private final String uuid;
	private final TraceConfiguration configuration;

	public VizuTrace(TraceConfiguration traceConfiguration) {
		this.trace = new ConcurrentLinkedQueue<SerializedEvent>();
		this.configuration = traceConfiguration;
		this.uuid = traceConfiguration.generateUUID();
	}

	@Override
	public boolean isEnabled(Object marker) {
		return configuration.isEnabled(marker);
	}

	@Override
	public String getUUID() {
		return uuid;
	}

	@Override
	public Event start(String message) {
		return start(configuration.getDefaultMarker(), message);
	}
	
	@Override
	public Event start(String message, Object... values) {
		return start(configuration.getDefaultMarker(), message, values);
	}
	
	@Override
	public Event start(Object marker, String message) {
		if (! configuration.isEnabled(marker)) { return EVENT; }
		return createEndEvent(message, EMPTY);
	}
	
	@Override
	public Event start(Object marker, String name, Object... values) {
		if (! configuration.isEnabled(marker)) { return EVENT; }
		return createEndEvent(name, values);
	}

	private Event createEndEvent(String message, Object [] values) {
		final SimpleEvent simple = simple(message, values);
		
		return new Trace.Event() {
			
			@Override
			public void end() {
				trace.add(new EndEvent(simple, System.currentTimeMillis(), Thread.currentThread().getName()));
			}
		};
	}

		@Override
	public void event(String message) {
		event(configuration.getDefaultMarker(), message);
	}

	@Override
	public void event(String message, Object... values) {
		event(configuration.getDefaultMarker(), message, values);
	}
	
	@Override
	public void event(Object marker, String message) {
		if (! configuration.isEnabled(marker)) { return; }
		simple(message, EMPTY);
	}

	@Override
	public void event(Object marker, String name, Object... values) {
		if (! configuration.isEnabled(marker)) { return; }
		simple(name, values);
	}

	private SimpleEvent simple(String name, Object[] values) {
		SimpleEvent event = new SimpleEvent(name, values, Thread.currentThread().getName(), System.currentTimeMillis());
		trace.add(event);
		return event;
	}

	public void write(Writer writer) throws IOException {
		VizuFormatter.format(uuid, trace, writer);
	}

	@Override
	public void finish() {
		if (LOGGER.isInfoEnabled(MARKER)) {
			event("Trace finished");
			StringWriter sw = new StringWriter();
			try {
				this.write(sw);
			} catch(Exception ex) {
				LOGGER.error("Failed to finish trace", ex);
				return;
			}

			LOGGER.info(MARKER, sw.toString());
		}
	}

	@Override
	public Trace fork(String message) {
		return fork(configuration.getDefaultMarker(), message, EMPTY);
	}

	@Override
	public Trace fork(String message, Object... values) {
		return fork(configuration.getDefaultMarker(), message, values);
	}

	@Override
	public Trace fork(Object marker, String message) {
		return fork(marker, message, EMPTY);
	}

	@Override
	public Trace fork(Object marker, String message, Object... values) {
		if (configuration.isEnabled(marker)) {
			VizuTrace trace =  new VizuTrace(this.configuration);
			trace.event(marker, "Trace was forked (parent trace uuid)", uuid);
			this.event(marker, "Trace was forked (child trace uuid)", trace.uuid);
			this.event(marker, message, values == null ? EMPTY : values);
			return trace;
		} else {
			return NOOP_TRACE_FACTORY.create(this.configuration);
		}
	}

	@Override
	public void join(Trace trace, String message) {
		join(trace, configuration.getDefaultMarker(), message, EMPTY);
	}

	@Override
	public void join(Trace trace, String message, Object... values) {
		join(trace, configuration.getDefaultMarker(), message, values);
	}

	@Override
	public void join(Trace trace, Object marker, String message) {
		join(trace, marker, message, EMPTY);
	}

	@Override
	public void join(Trace trace, Object marker, String message, Object... values) {
		if (configuration.isEnabled(marker)) {
			this.event("Trace joined (joining trace uuid)", trace.getUUID());
			this.event(marker, message, values == null ? EMPTY : values);
		}

		trace.event("Trace joined (joined trace uuid)", getUUID());
	}
}
