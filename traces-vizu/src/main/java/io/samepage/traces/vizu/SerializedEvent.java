package io.samepage.traces.vizu;

import com.fasterxml.jackson.core.JsonGenerator;
import java.io.IOException;


abstract class SerializedEvent {
	
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	abstract long getTime();
	
	abstract String getThreadName();
	
	abstract void serialize(JsonGenerator jg, long startTime) throws IOException;

}
