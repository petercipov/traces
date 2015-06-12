package com.petercipov.traces.vizu;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.petercipov.traces.api.Event;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Date;
import java.text.DateFormat;

/**
 *
 * @author Peter Cipov
 */
public final class VizuFormatter {
	
	
	private static final String DELIMITER = "\n###>>>\n"; 

	private VizuFormatter() {
		//Singleton
	}
	
	public static void format(Collection<Event> events, Writer w) throws IOException {
		JsonGenerator jg = new JsonFactory().createGenerator(w);
		List<Event> finishedEvents = constructFinished(events);
		
		w.append(DELIMITER);
		
		jg.writeStartObject();
		jg.writeArrayFieldStart("trace");
		jg.flush();

		for (Event e : events) {
			if (e instanceof SimpleEvent) {
				serializeSimple((SimpleEvent)e, jg);
			} else if (e instanceof End) {
				serializeEnd((End)e, jg);
			} else {
				serializeEvent(e, jg, finishedEvents.contains(e));
			}
			
			jg.flush();
			w.append("\n\t");
		}

		jg.writeEndArray();
		jg.flush();
		w.append("\n ");
		jg.writeEndObject();
		jg.flush();
		w.append(DELIMITER);
	}
	
	private static void serializeEvent(Event event, JsonGenerator jg, boolean finished) throws IOException{
		jg.writeStartObject();
			jg.writeStringField("thread", event.getThreadName());
			jg.writeStringField("t", serializeTime(event.getTime()));			
			jg.writeNumberField("id", event.getId());
			jg.writeStringField("n",  event.getName());
			jg.writeStringField("type", "start");
			jg.writeBooleanField("finished", finished);
			jg.writeArrayFieldStart("values");
				addToArray(jg, event.getValues());
			jg.writeEndArray();
		jg.writeEndObject();		
	}
	
	private static void serializeEnd(End end, JsonGenerator jg) throws IOException {
		jg.writeStartObject();
			jg.writeStringField("thread", end.getThreadName());
			jg.writeStringField("t", serializeTime(end.getTime()));
			jg.writeNumberField("id", end.getId());
			jg.writeNumberField("endOf",  end.getStartEvent().getId());
			jg.writeStringField("type", "end");
		jg.writeEndObject();
	}
	
	private static void serializeSimple(SimpleEvent event, JsonGenerator jg) throws IOException{
		
		jg.writeStartObject();
		jg.writeStringField("thread", event.getThreadName());
		jg.writeStringField("t", serializeTime(event.getTime()));
		jg.writeNumberField("id", event.getId());
		jg.writeStringField("n",  event.getName());
		jg.writeStringField("type", "event");
			jg.writeArrayFieldStart("values");
			addToArray(jg, event.getValues());
			jg.writeEndArray();		
		jg.writeEndObject();
	}

	private static List<Event> constructFinished(Collection<Event> events) {
		ArrayList<Event> finished = new ArrayList<>(0);
		
		for (Event e : events) {
			if (e instanceof End) {
				finished.add(((End)e).getStartEvent());
			}
		}
		
		return finished;
	}
	
	private static void addToArray(JsonGenerator jg, Object[] array) throws IOException {
		for(Object o : array) {
			if (o == null) {
				jg.writeString("null");
			} else {
				if (o instanceof Throwable) {
					writeStacktrace(jg, (Throwable) o);
				} else {
					jg.writeString(o.toString());
				}
			}
		}
	}
	
	private static void writeStacktrace(JsonGenerator jg, Throwable throwable) throws IOException {
		jg.writeStartObject();
			writeThrrowable(jg, throwable, 10);
		jg.writeEndObject();
	}
	
	private static void writeThrrowable(JsonGenerator jg, Throwable th, int nesting) throws IOException {

		jg.writeStringField("errorMessage", th.toString());
		jg.writeArrayFieldStart("stack");
			for (StackTraceElement element : th.getStackTrace()) {
				writeStatckElement(jg, element);
			}
		jg.writeEndArray();
		
		
		Throwable cause = th.getCause();
		if (cause == null || nesting <= 0) {return;}
		
		jg.writeObjectFieldStart("causedBy");
		writeThrrowable(jg, cause, nesting - 1);
		jg.writeEndObject();
	}
	
	private static void writeStatckElement(JsonGenerator jg, StackTraceElement element) throws IOException {
		jg.writeStartObject();
			jg.writeStringField("frame", element.getClassName()+"." +element.getMethodName()+":"+(element.isNativeMethod() ? "native" :  element.getLineNumber()));
			jg.writeStringField("file", element.getFileName() == null ? "Unknown Source" : element.getFileName());
		jg.writeEndObject();
	}
	
	private static String serializeTime(Date date) {
		return DateFormat.getDateInstance().format(date);
	}
}
