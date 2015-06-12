package com.petercipov.traces.api;

import java.util.Date;

/**
 *
 * @author pcipov
 */
public class Event {
	private final Date start;
    private final String name;
	private final Object[] values;
	private final int id;
	private final String threadName;
	
	public Event(int id, String name, Object [] values) {
		this(id, name, values, Thread.currentThread().getName(), new Date());
	}
	
	public Event(int id, String name, Object [] values, String threadName, Date date) {		
		this.name = name;
		this.values = values;
		this.id = id;
		this.threadName = threadName;
		this.start = date;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public Date getTime() {
		return start;
	}

	public Object[] getValues() {
		return values;
	}

	public String getThreadName() {
		return threadName;
	}	
}
