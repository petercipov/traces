package io.samepage.traces.vizu;

import io.samepage.traces.api.TraceFactory;


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
