package com.petercipov.traces.vizu;

import com.fasterxml.jackson.core.JsonGenerator;
import java.io.IOException;

/**
 *
 * @author pcipov
 */
class SimpleEvent extends SerializedEvent {
    private final String name;
	private final Object[] values;
	private final String threadName;
	private final long time;

	public SimpleEvent(String name, Object[] values, String threadName, long time) {
		this.name = name;
		this.values = values;
		this.threadName = threadName;
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public Object[] getValues() {
		return values;
	}

	@Override
	public String getThreadName() {
		return threadName;
	}

	@Override
	public long getTime() {
		return time;
	}

	@Override
	public void serialize(JsonGenerator jg, long startTime) throws IOException {
		jg.writeStartObject();
			jg.writeNumberField("id", getId());
			jg.writeStringField("th", getThreadName());
			jg.writeNumberField("dt", getTime() - startTime);			
			jg.writeStringField("n",  getName());
			serializeValues(jg);
		jg.writeEndObject();		
	}

	private void serializeValues(JsonGenerator jg) throws IOException {
		if (getValues() != null && getValues().length != 0) {
			jg.writeArrayFieldStart("values");
				addToArray(jg, getValues());
			jg.writeEndArray();
		}
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
		jg.writeRaw("\t\t");
			for (StackTraceElement element : th.getStackTrace()) {
				jg.writeRaw("\n\t\t");
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
}