package com.petercipov.traces.impl;

import com.petercipov.traces.api.FinishableTrace;
import com.petercipov.traces.api.TraceConfiguration;
import com.petercipov.traces.api.TraceFactory;
import com.petercipov.traces.vizu.VizuTrace;

/**
 *
 * @author petercipov
 */
public class TraceFactoryImpl implements TraceFactory{

	@Override
	public FinishableTrace create(TraceConfiguration configuration) {
		return new VizuTrace(configuration);
	}
	
}
