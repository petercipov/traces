package com.petercipov.traces.api;

/**
 *
 * @author pcipov
 */
public class NoopTraceFactory implements TraceFactory<NoopTrace>{

	@Override
	public NoopTrace create() {
		return NoopTrace.INSTANCE;
	}
}
