package com.petercipov.traces.api;

/**
 *
 * @author pcipov
 */
public class NoopTraceFactory implements TraceFactory {

	private static final NoopTrace INSTANCE = new NoopTrace();

	@Override
	public FinishableTrace create(TraceConfiguration configuration) {
		return INSTANCE;
	}
}
