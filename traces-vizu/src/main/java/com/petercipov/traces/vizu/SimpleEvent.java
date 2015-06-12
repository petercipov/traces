package com.petercipov.traces.vizu;

import com.petercipov.traces.api.Event;

/**
 *
 * @author pcipov
 */
class SimpleEvent extends Event {

	SimpleEvent(int id, String name, Object[] values) {
		super(id, name, values);
	}
}
