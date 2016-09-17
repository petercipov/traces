package com.petercipov.traces.api;

/**
 *
 * @author pcipov
 */
public interface TraceFactory {

	FinishableTrace create(TraceConfiguration configuration);

}
