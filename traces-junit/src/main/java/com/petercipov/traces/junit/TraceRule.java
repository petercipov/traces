package com.petercipov.traces.junit;

import com.petercipov.traces.api.FinishableTrace;
import com.petercipov.traces.api.Level;
import com.petercipov.traces.api.Trace;
import java.util.LinkedList;
import org.junit.rules.ExternalResource;

/**
 *
 * @author Peter Cipov
 */
public class TraceRule extends ExternalResource {
	private static final String FACTORY_CLASS = "com.petercipov.traces.impl.TraceFactoryImpl";

	private final LinkedList<FinishableTrace> traces;
	private final TraceImplFactory factory;

	public TraceRule() {
		this(new ReflexTraceImplFactory(FACTORY_CLASS, Level.INFO));
	}

	public TraceRule(TraceImplFactory factory) {
		this.traces = new LinkedList<FinishableTrace>();
		this.factory = factory;
	}
	
	public Trace create() {
		FinishableTrace t = factory.create();
		traces.add(t);
		return t;
	}
	
	public Trace trace() {
		return traces.getFirst();
	}

	@Override
	protected void before() throws Throwable {
		this.traces.clear();
		create();
	}

	@Override
	protected void after() {
		Exception failure = null;
		for(FinishableTrace trace : traces) {
			try {
				trace.finish();
			} catch(Exception ex) {
				if (failure != null) {
					failure = ex;
				}
			}
		}
		
		if(failure != null) {
			throw new IllegalStateException("Error while closing traces", failure);
		}
	}
}
