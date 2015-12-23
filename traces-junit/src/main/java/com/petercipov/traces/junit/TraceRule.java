package com.petercipov.traces.junit;

import com.petercipov.traces.api.NoopTraceFactory;
import com.petercipov.traces.api.StdioTraceFactory;
import com.petercipov.traces.api.Trace;
import com.petercipov.traces.api.TraceFactory;
import com.petercipov.traces.vizu.VizuTrace;
import com.petercipov.traces.vizu.VizuTraceFactory;
import java.io.StringWriter;
import java.util.LinkedList;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Peter Cipov
 */
public class TraceRule extends ExternalResource implements TraceFactory<Trace> {
	
	public static enum Type {
		NOOP,
		STDIO,
		VIZU
	}
	
	private static final Logger logger = LoggerFactory.getLogger(TraceRule.class);
	private final TraceFactory<? extends Trace> factory;
	private final LinkedList<Trace> traces;
	private final Type type;

	public TraceRule() {
		this(Type.VIZU);
	}

	public TraceRule(Type type) {
		this.type = type;

		switch(type) {
			case NOOP:
				this.factory = new NoopTraceFactory();
			break;
			case STDIO:
				this.factory = new StdioTraceFactory();
			break;
			case VIZU:
				this.factory = new VizuTraceFactory();
			break;
			default:
				throw new IllegalArgumentException("unknown type: "+ type);
		}
		
		this.traces = new LinkedList<Trace>();
	}
	
	@Override
	public Trace create() {
		Trace t = factory.create();
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
		if (type == Type.VIZU) {
			for(Trace trace : traces) {
				try {
					logTrace(trace);
				} catch(Exception ex) {
					logger.error("Could not properly log trace due to error", ex);
				}
			}
		}
	}

	private void logTrace(Trace trace) throws Exception {
		StringWriter sw = new StringWriter();
		((VizuTrace) trace).write(sw);
		logger.info(sw.toString());
	}
}
