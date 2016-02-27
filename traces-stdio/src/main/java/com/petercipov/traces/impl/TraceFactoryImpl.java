package com.petercipov.traces.impl;

import com.petercipov.traces.api.FinishableTrace;
import com.petercipov.traces.api.Level;
import com.petercipov.traces.api.TraceFactory;
import com.petercipov.traces.stdio.StdioTrace;

/**
 *
 * @author petercipov
 */
public class TraceFactoryImpl implements TraceFactory{
	
	@Override
	public FinishableTrace create(Level expectedLevel) {
		return new StdioTrace(expectedLevel);
	}
	
}
