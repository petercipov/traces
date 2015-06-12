package com.petercipov.traces.api;


/**
 *
 * @author pcipov
 */
public final class NoopTrace implements Trace {
	
	private static final Event EVENT = new Event(1, "NOOP", new Object[0]);

	NoopTrace() {}
	
	@Override
	public Event start(String name, Object... values) {
		return EVENT;
	}

	@Override
	public void end(Event event) {
		//NOOP
	}

	@Override
	public void event(String name, Object... values) {
		//NOOP
	}

	@Override
	public Event start(String name) {
		return EVENT;
	}

	@Override
	public Event start(String name, Object v1) {
		return EVENT;
	}

	@Override
	public Event start(String name, Object v1, Object v2) {
		return EVENT;
	}

	@Override
	public Event start(String name, Object v1, Object v2, Object v3) {
		return EVENT;
	}

	@Override
	public Event start(String name, Object v1, Object v2, Object v3, Object v4) {
		return EVENT;
	}

	@Override
	public Event start(String name, Object v1, Object v2, Object v3, Object v4, Object v5) {
		return EVENT;
	}

	@Override
	public void event(String name) {
		//NOOP
	}

	@Override
	public void event(String name, Object v1) {
		//NOOP
	}

	@Override
	public void event(String name, Object v1, Object v2) {
		//NOOP
	}

	@Override
	public void event(String name, Object v1, Object v2, Object v3) {
		//NOOP
	}

	@Override
	public void event(String name, Object v1, Object v2, Object v3, Object v4) {
		//NOOP
	}

	@Override
	public void event(String name, Object v1, Object v2, Object v3, Object v4, Object v5) {
		//NOOP
	}
}