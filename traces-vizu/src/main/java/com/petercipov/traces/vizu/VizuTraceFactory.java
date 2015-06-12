package com.petercipov.traces.vizu;

import com.petercipov.traces.api.TraceFactory;


/**
 *
 * @author pcipov
 */
public class VizuTraceFactory implements TraceFactory<VizuTrace> {
	
	@Override
	public VizuTrace create() {
		return new VizuTrace();
	}
}
