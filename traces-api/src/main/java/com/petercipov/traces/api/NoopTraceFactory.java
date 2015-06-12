package com.petercipov.traces.api;

/**
 *
 * @author pcipov
 */
public class NoopTraceFactory implements TraceFactory<NoopTrace>{
	
	private static final NoopTrace INSTANCE = new NoopTrace();

	@Override
	public NoopTrace create() {
		return INSTANCE;
	}

}
