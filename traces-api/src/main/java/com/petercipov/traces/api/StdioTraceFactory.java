package com.petercipov.traces.api;

/**
 *
 * @author pcipov
 */
public class StdioTraceFactory implements TraceFactory<StdioTrace>{
	
	private final Level expectedLevel;

	public StdioTraceFactory() {
		this(Level.INFO);
	}
	
	public StdioTraceFactory(Level expectedLevel) {
		this.expectedLevel = expectedLevel;
	}

	@Override
	public StdioTrace create() {
		return new StdioTrace(expectedLevel);
	}
	
}
