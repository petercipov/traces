package com.petercipov.traces.stdio;

import com.petercipov.traces.api.*;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author pcipov
 */
public class StdioTrace implements FinishableTrace {
	
	private static final Object[] EMPTY = new Object[0];
	private static final Event EVENT = new Event() {

		@Override
		public void end() {
			//NOOP
		}
	};
	private static final NoopTraceFactory NOOP_TRACE_FACTORY = new NoopTraceFactory();
	
	private final AtomicInteger counter;
	private final Printer printer;
	private final String uuid;
	private final TraceConfiguration configuration;

	public StdioTrace(TraceConfiguration configuration) {
		this.counter = new AtomicInteger();
		this.uuid = configuration.generateUUID();
		this.printer = new Printer(this.uuid);
		this.configuration = configuration;
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
		
		int id = counter.incrementAndGet();
		printer.printlnEventStart(id, message, EMPTY);
		return new StdioEvent(printer, id, counter);
	}

	@Override
	public Event start(Object marker, String message, Object... values) {
		if (! configuration.isEnabled(marker)) { return EVENT; }
		
		int id = counter.incrementAndGet();
		printer.printlnEventStart(id, message, values);
		return new StdioEvent(printer, id, counter);
	}

	@Override
	public void event(Object marker, String message) {
		if (! configuration.isEnabled(marker)) { return; }
		printer.printlnSimpleEvent(counter.incrementAndGet(), message, EMPTY);
	}

	@Override
	public void event(Object marker, String message, Object ... values) {
		if (! configuration.isEnabled(marker)) { return; }
		printer.printlnSimpleEvent(counter.incrementAndGet(), message, values);
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
	public boolean isEnabled(Object marker) {
		return this.configuration.isEnabled(marker);
	}

	@Override
	public void finish() {
		printer.printTraceEnd(counter.incrementAndGet());
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
			StdioTrace trace =  new StdioTrace(this.configuration);
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
