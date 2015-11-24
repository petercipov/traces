package io.samepage.traces.api;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author pcipov
 */
class StdioEvent implements Trace.Event{
	private final long guid;
	private final int refId;
	private final AtomicInteger counter;
	
	public StdioEvent(long guid, int refId, AtomicInteger counter) {		
		this.guid = guid;
		this.refId = refId;
		this.counter = counter;
	}

	@Override
	public void end() {
		System.out.format("guid:%d | id:%d | thread:%s | t:%d | \t type:end | of:%d \n",
			guid,
			counter.incrementAndGet(), 
			Thread.currentThread().getName(), 
			System.currentTimeMillis(), 
			refId
		);
	}
}
