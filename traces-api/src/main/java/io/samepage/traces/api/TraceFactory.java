package io.samepage.traces.api;

/**
 *
 * @author pcipov
 */
public interface TraceFactory <T extends Trace> {
	T create();
}
