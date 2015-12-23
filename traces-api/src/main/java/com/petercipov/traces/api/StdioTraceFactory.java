package com.petercipov.traces.api;

/**
 *
 * @author pcipov
 */
public class StdioTraceFactory implements TraceFactory<StdioTrace>{

	@Override
	public StdioTrace create() {
		return new StdioTrace();
	}
	
}
