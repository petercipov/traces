package io.samepage.traces.test;

import io.samepage.traces.api.Trace;
import io.samepage.traces.api.Trace.Event;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;

/**
 *
 * @author Peter Cipov
 */
public class VizuTraceRuleTest {
	
	@Rule
	public TraceRule traceRule = new TraceRule();
	
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
		String USER_ID = "userId";
		Trace trace = traceRule.trace();
		
		checkACL(trace, USER_ID);
		purgeUserHistory(trace, USER_ID);
		respondOK(trace);
		
	}

	private void purgeUserHistory(Trace trace, String userId) throws Exception {
		Event purgingEvent = trace.start("Purging user history (userId)", userId);
		
		try {
			callRestService(trace, 1, "{\"data\": \"remove\"}", true);
		} catch (Exception ex) {
			trace.event("retrying purge call");
			callRestService(trace, 1, "{\"data\": \"remove\"}", false);
		}
		
		purgingEvent.end();
	}
	
	private void checkACL(Trace trace, String userId) {
		trace.event("User has sufficient privileges (userid)", userId);
	} 
	
	private void respondOK(Trace trace) {
		trace.event("Responding OK ");
	}
	
	private String callRestService(final Trace trace, final int id, final String data, final boolean fail) throws Exception {
		return es.submit(new Callable<String>() {

			@Override
			public String call() {
				Event callEvent = trace.start("calling remote service (id, data)", id, data);
				String code;
				
				try {
					if (fail) {
						code = "500";
						throw new IllegalStateException("remote service responded with HTTP 500"); 
					} else {
						code = "200";
					}
					trace.event("call succeeded (response)", code);
				} catch(RuntimeException ex ) {
					trace.event("call failed", ex);
					throw ex;
				} finally {
					callEvent.end();
				}
				
				return code;
			}
		}).get();
	}
	
}
