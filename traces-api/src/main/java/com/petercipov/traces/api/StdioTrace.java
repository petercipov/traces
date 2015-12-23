package com.petercipov.traces.api;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author pcipov
 */
public class StdioTrace implements Trace {
	
	private static final AtomicLong uuid = new AtomicLong();
	private final AtomicInteger counter = new AtomicInteger();
	
	private final long localGuid = uuid.incrementAndGet();

	@Override
	public Event start(String name) {
		return start(name, new Object[] {});
	}

	@Override
	public Event start(String name, Object v1) {
		return start(name, new Object[] {v1});
	}

	@Override
	public Event start(String name, Object v1, Object v2) {
		return start(name, new Object[] {v1, v2});
	}

	@Override
	public Event start(String name, Object v1, Object v2, Object v3) {
		return start(name, new Object[] {v1, v2, v3});
	}

	@Override
	public Event start(String name, Object v1, Object v2, Object v3, Object v4) {
		return start(name, new Object[] {v1, v2, v3, v4});
	}

	@Override
	public Event start(String name, Object v1, Object v2, Object v3, Object v4, Object v5) {
		return start(name, new Object[] {v1, v2, v3, v4, v5});
	}

	@Override
	public Event start(String name, Object... values) {
		int id = counter.incrementAndGet();
		
		System.out.format("guid:%d | id:%d | thread:%s | t:%d | \t type:start | n:%s | values:%s \n", 
			localGuid, 
			id,
			Thread.currentThread().getName(), 
			System.currentTimeMillis(), 
			name, 
			Arrays.toString(values));
		
		return new StdioEvent(localGuid, id, counter);
	}

	@Override
	public void event(String name) {
		event(name, new Object[]{});
	}

	@Override
	public void event(String name, Object v1) {
		event(name, new Object[]{v1});
	}

	@Override
	public void event(String name, Object v1, Object v2) {
		event(name, new Object[]{v1, v2});
	}

	@Override
	public void event(String name, Object v1, Object v2, Object v3) {
		event(name, new Object[]{v1, v2, v3});
	}

	@Override
	public void event(String name, Object v1, Object v2, Object v3, Object v4) {
		event(name, new Object[]{v1, v2, v3, v4});
	}

	@Override
	public void event(String name, Object v1, Object v2, Object v3, Object v4, Object v5) {
		event(name, new Object[]{v1, v2, v3, v4, v5});
	}

	@Override
	public void event(String name, Object... values) {
		System.out.format("guid:%d | id:%d | thread:%s | t:%d | \t n:%s | values:%s \n", 
			localGuid, 
			counter.incrementAndGet(), 
			Thread.currentThread().getName(), 
			System.currentTimeMillis(), 
			name, 
			Arrays.toString(values));
	}
	
}
