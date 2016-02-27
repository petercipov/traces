package com.petercipov.traces.junit;

import com.petercipov.traces.api.FinishableTrace;

/**
 *
 * @author petercipov
 */
public interface TraceImplFactory {
	
	FinishableTrace create();

}
