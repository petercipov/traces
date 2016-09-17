package com.petercipov.traces.api;


/**
 *
 * @author pcipov
 */
public final class NoopTrace implements FinishableTrace {

	private static final String UUID = "NOOP-"+System.currentTimeMillis();
	
	private static final Event EVENT = new Event() {

		@Override
		public void end() {
			//NOOP
		}
	};

	NoopTrace() {
		// SHOULD NOT BE CALLED DIRECTLY, USE FACTORY
	}

	@Override
	public boolean isEnabled(Object marker) {
		return false;
	}

	@Override
	public Event start(String message, Object... values) {
		return EVENT;
	}

	@Override
	public void event(String message, Object... values) {
		//NOOP
	}

	@Override
	public String getUUID() {
		return UUID;
	}
	@Override
	public Event start(String message) {
		return EVENT;
	}

	@Override
	public void event(String message) {
		//NOOP
	}

	@Override
	public Event start(Object marker, String message) {
		return EVENT;
	}
	
	@Override
	public Event start(Object marker, String message, Object... values) {
		return EVENT;
	}

	@Override
	public void event(Object marker, String message) {
		//NOOP
	}

	@Override
	public void event(Object marker, String message, Object... values) {
		//NOOP
	}

	@Override
	public void finish() {
		//NOOP
	}

	@Override
	public Trace fork(String message) {
		return this;
	}

	@Override
	public Trace fork(String message, Object... values) {
		return this;
	}

	@Override
	public Trace fork(Object marker, String message) {
		return this;
	}

	@Override
	public Trace fork(Object marker, String message, Object... values) {
		return this;
	}

	@Override
	public void join(Trace trace, String message) {
		//NOOP
	}

	@Override
	public void join(Trace trace, String message, Object... values) {
		//NOOP
	}

	@Override
	public void join(Trace trace, Object marker, String message) {
		//NOOP
	}

	@Override
	public void join(Trace trace, Object marker, String message, Object... values) {
		//NOOP
	}
}