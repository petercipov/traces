package com.petercipov.traces.test;

import com.petercipov.traces.api.TraceFactory;
import com.petercipov.traces.vizu.VizuTrace;
import com.petercipov.traces.vizu.VizuTraceFactory;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Peter Cipov
 */
public class VizuTraceRule extends ExternalResource implements TraceFactory<VizuTrace> {
	private final Logger logger = LoggerFactory.getLogger(VizuTraceRule.class);
	private final VizuTraceFactory factory;
	private VizuTrace trace;

	public VizuTraceRule() {
		this.factory = new VizuTraceFactory();
	}
	
	@Override
	public VizuTrace create() {
		return factory.create();
	}
	
	public VizuTrace trace() {
		return trace;
	}

	@Override
	protected void before() throws Throwable {
		this.trace = factory.create();
	}

	@Override
	protected void after() {
		if (trace != null) {
			logger.info("{}", trace);
		}
	}
}
