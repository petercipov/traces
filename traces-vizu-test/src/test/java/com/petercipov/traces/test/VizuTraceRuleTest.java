package com.petercipov.traces.test;

import com.petercipov.traces.api.Event;
import com.petercipov.traces.api.Trace;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;

/**
 *
 * @author Peter Cipov
 */
public class VizuTraceRuleTest {
	
	@Rule
	public VizuTraceRule traceRule = new VizuTraceRule();
	
	private ExecutorService es;
	
	@Before
	public void before() {
		es = Executors.newSingleThreadExecutor(new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "rest-thread");
			}
		});
	}
	
	@After
	public void after() {
		es.shutdownNow();
	}

	@Test
	public void exampleTraceOutput() throws Exception {
		
		Trace trace = traceRule.trace();
		
		trace.event("tracing demo");
		
		Future<String> f1 = callRestService(trace, 1, "{\"data\": \"remove\"}", false);
		Future<String> f2 = callRestService(trace, 2, "{\"data\": \"remove\"}", true);
		Future<String> f3 = callRestService(trace, 3, "{\"data\": \"remove\"}", false);
		
		f1.get();
		f2.get();
		f3.get();
		
	}
	
	private Future<String> callRestService(final Trace trace, final int id, final String data, final boolean fail) {
		return es.submit(new Callable<String>() {

			@Override
			public String call() {
				Event callEvent = trace.start("calling remote service (id, data)", id, data);
				
				String code;
				if (fail) {
					code = "500";
					trace.event("call failed", new IllegalStateException("remote service responded with HTTP 500"));
				} else {
					code = "200";
					trace.event("call succeeded (response)", code);
				}
				
				trace.end(callEvent);
				
				return code;
			}
		});
	}
	
}
