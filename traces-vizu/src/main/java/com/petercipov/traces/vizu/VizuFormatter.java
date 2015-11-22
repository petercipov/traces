package com.petercipov.traces.vizu;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author Peter Cipov
 */
final class VizuFormatter {
	
	private static final ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<SimpleDateFormat>() {

		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss.SSS'Z'");
		}
		
	};
	
	private static final String DELIMITER = "\n###>>>\n"; 
	private static final JsonFactory factory = new JsonFactory();

	private VizuFormatter() {
		//Singleton
	}
	
	public static void format(Collection<SerializedEvent> events, Writer w) throws IOException {
		JsonGenerator jg = factory.createGenerator(w).configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
		
		assignIds(events);
		
		jg.writeRaw(DELIMITER);
		
		long startTime = events.isEmpty() 
			? System.currentTimeMillis() 
			: events.iterator().next().getTime();
		
		jg.writeStartObject();
		jg.writeStringField("time", serializeTime(new Date(startTime)));
		jg.writeArrayFieldStart("trace");
		
		
		for (SerializedEvent e : events) {
			jg.writeRaw("\n\t");
			e.serialize(jg, startTime);
		}

		jg.writeEndArray();
		jg.writeRaw("\n ");
		jg.writeEndObject();
		jg.writeRaw(DELIMITER);
		jg.close();
	}
	
	private static void assignIds(Collection<SerializedEvent> events) {
		int id = 1;
		for(SerializedEvent event : events) {
			event.setId(id);
			id++;
		}
	
	}
	
	private static String serializeTime(Date date) {
		return tl.get().format(date);
	}
	
}
