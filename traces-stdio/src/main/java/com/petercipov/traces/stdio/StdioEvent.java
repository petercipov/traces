package com.petercipov.traces.stdio;

import com.petercipov.traces.api.Trace;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author pcipov
 */
class StdioEvent implements Trace.Event {
	
	private final Printer printer;
	private final int refId;
	private final AtomicInteger counter;
	
	public StdioEvent(Printer printer, int refId, AtomicInteger counter) {		
		this.printer = printer;
		this.refId = refId;
		this.counter = counter;
	}

	@Override
	public void end() {
		printer.printlnEventEnd(counter.incrementAndGet(), refId);
	}
}
