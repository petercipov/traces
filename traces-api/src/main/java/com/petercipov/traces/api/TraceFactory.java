package com.petercipov.traces.api;

/**
 *
 * @author pcipov
 */
public interface TraceFactory {
	
	FinishableTrace create(Level expectedLevel);
	
}
