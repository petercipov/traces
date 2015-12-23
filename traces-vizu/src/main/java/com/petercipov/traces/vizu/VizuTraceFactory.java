package com.petercipov.traces.vizu;

import com.petercipov.traces.api.Level;
import com.petercipov.traces.api.TraceFactory;


/**
 *
 * @author pcipov
 */
public class VizuTraceFactory implements TraceFactory<VizuTrace> {
	
	private final Level expectedLevel;

	public VizuTraceFactory() {
		this(Level.INFO);
	}

	public VizuTraceFactory(Level expectedLevel) {
		this.expectedLevel = expectedLevel;
	}
	
	@Override
	public VizuTrace create() {
		return new VizuTrace(expectedLevel);
	}
}
