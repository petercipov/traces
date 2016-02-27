package com.petercipov.traces.stdio;

import com.petercipov.traces.api.FinishableTrace;
import com.petercipov.traces.api.Level;
import com.petercipov.traces.api.NoopTrace;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author pcipov
 */
public class StdioTrace implements FinishableTrace {
	
	private static final AtomicLong LOCAL_COUNTER = new AtomicLong();
	private final AtomicInteger counter = new AtomicInteger();
	private final Level expectedLevel;
	private final long localGuid = LOCAL_COUNTER.incrementAndGet();

	public StdioTrace(Level expectedLevel) {
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
		if (! expectedLevel.enables(level)) { return NoopTrace.EVENT; }
		
		int id = counter.incrementAndGet();
		printStart(id, name, "");
		return new StdioEvent(localGuid, id, counter);
	}

	@Override
	public Event start(Level level, String name, Object... values) {
		if (! expectedLevel.enables(level)) { return NoopTrace.EVENT; }
		
		int id = counter.incrementAndGet();
		printStart(id, name, Arrays.toString(values));
		return new StdioEvent(localGuid, id, counter);
	}

	private void printStart(int id, String name, String valuesString) {
		System.out.format("guid:%d | id:%d | thread:%s | t:%d | \t type:start | n:%s | values:%s \n",
			localGuid,
			id,
			Thread.currentThread().getName(),
			System.currentTimeMillis(),
			name,
			valuesString);
	}

	@Override
	public void event(Level level, String name) {
		if (! expectedLevel.enables(level)) { return; }
		printEvent(name, "");
	}

	@Override
	public void event(Level level, String name, Object... values) {
		if (! expectedLevel.enables(level)) { return; }
		printEvent(name, Arrays.toString(values));
	}

	private void printEvent(String name, String values) {
		System.out.format("guid:%d | id:%d | thread:%s | t:%d | \t n:%s | values:%s \n",
			localGuid,
			counter.incrementAndGet(),
			Thread.currentThread().getName(),
			System.currentTimeMillis(),
			name,
			values);
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
	public boolean isDebugEnabled() {
		return expectedLevel.enables(Level.DEBUG);
	}

	@Override
	public boolean isInfoEnabled() {
		return expectedLevel.enables(Level.DEBUG);
	}

	@Override
	public boolean isWarnEnabled() {
		return expectedLevel.enables(Level.DEBUG);
	}

	@Override
	public boolean isErrorEnabled() {
		return expectedLevel.enables(Level.DEBUG);
	}	

	@Override
	public void finish() {
		printEvent("Trace has finished", "");
	}
}
