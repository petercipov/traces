package com.petercipov.traces.api;


/**
 *
 * @author pcipov
 */
public final class NoopTrace implements Trace {
	
	public static final NoopTrace INSTANCE = new NoopTrace();

	public static final Event EVENT = new Event() {

		@Override
		public void end() {
			//NOOP
		}
	};

	private NoopTrace() {}
	
	@Override
	public Event start(String name, Object... values) {
		return EVENT;
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
	public void event(String name) {
		//NOOP
	}

	@Override
	public boolean isDebugEnabled() {
		return false;
	}

	@Override
	public boolean isInfoEnabled() {
		return false;
	}

	@Override
	public boolean isWarnEnabled() {
		return false;
	}

	@Override
	public boolean isErrorEnabled() {
		return false;
	}

	@Override
	public Event start(Level level, String name) {
		return EVENT;
	}
	
	@Override
	public Event start(Level level, String name, Object... values) {
		return EVENT;
	}

	@Override
	public void event(Level level, String name) {
		//NOOP
	}

	@Override
	public void event(Level level, String name, Object... values) {
		//NOOP
	}
}