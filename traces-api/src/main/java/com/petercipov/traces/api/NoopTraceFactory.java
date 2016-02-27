package com.petercipov.traces.api;

/**
 *
 * @author pcipov
 */
public class NoopTraceFactory implements TraceFactory {
	
	@Override
	public FinishableTrace create(Level expectedlevel) {
		return NoopTrace.INSTANCE;
	}
}
