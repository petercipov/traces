package com.petercipov.traces.vizu;

import com.petercipov.traces.api.Event;


/**
 *
 * @author pcipov
 */
class End extends Event {
	
	private final Event startEvent;

	End(int id, Event startEvent) {
		super(id, null, null);
		this.startEvent = startEvent;
	}
	
	Event getStartEvent() {
		return startEvent;
	}	
}
