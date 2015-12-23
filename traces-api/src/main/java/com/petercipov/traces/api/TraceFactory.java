package com.petercipov.traces.api;

/**
 *
 * @author pcipov
 */
public interface TraceFactory <T extends Trace> {
	T create();
}
