package io.samepage.traces.vizu;

import com.fasterxml.jackson.core.JsonGenerator;
import java.io.IOException;

class EndEvent extends SerializedEvent {
	
	private final SerializedEvent refEvent;
	private final long endTime;
	private final String threadName;

	public EndEvent(SimpleEvent refEvent, long endTime, String threadName) {
		this.refEvent = refEvent;
		this.endTime = endTime;
		this.threadName = threadName;
	}

	@Override
	long getTime() {
		return endTime;
	}

	@Override
	String getThreadName() {
		return threadName;
	}
	
	@Override
	public void serialize(JsonGenerator jg, long startTime) throws IOException {
		jg.writeStartObject();
			jg.writeNumberField("id", getId());
			jg.writeStringField("th", threadName);
			jg.writeNumberField("dt", endTime - startTime);
			jg.writeNumberField("endOf",  refEvent.getId());
		jg.writeEndObject();
	}
	
	

}
