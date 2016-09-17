package com.petercipov.traces.stdio;

import com.petercipov.traces.api.Trace;
import com.petercipov.traces.api.Trace.Event;
import com.petercipov.traces.api.TraceConfiguration;
import org.junit.Test;

import java.util.UUID;

/**
 *
 * @author petercipov
 */
public class StdioTraceTest {

	@Test
	public void simpleTracePrint() {
		StdioTrace trace = new StdioTrace(new TestConfiguration());
		trace.event("Load user");
		User user = loadLoadUser(trace, "some user id");
		loadUsersBooks(trace, user);
		trace.finish();
	}
	
	private User loadLoadUser(Trace trace, String userId) {
		Event loadEvent = trace.start("Loading user", userId);
		User user = new User(userId, "Peter", "Cipov", "285 Park Place Morrisville, PA 19067");
		trace.event("Loaded user", user);
		loadEvent.end();
		return user;
	}
	
	private void loadUsersBooks(Trace trace, User user) {
		Event booksLoadEvent = trace.start("loading books for user", user.guid);
		
		try {
			throw new IllegalStateException("Failed to load books, connection lost!");
		} catch(Exception ex) {
			trace.event("Load failure", ex);
		}
		
		booksLoadEvent.end();
	}
	
	private class User {
		private final String guid;
		private final String name;
		private final String surname;
		private final String adress;

		public User(String guid, String name, String surname, String adress) {
			this.guid = guid;
			this.name = name;
			this.surname = surname;
			this.adress = adress;
		}

		@Override
		public String toString() {
			return "User (" + "guid=" + guid + ", name=" + name + ", surname=" + surname + ", adress=" + adress + ')';
		}
	}

	class TestConfiguration implements TraceConfiguration {
		@Override
		public boolean isEnabled(Object marker) {
			return true;
		}

		@Override
		public String generateUUID() {
			return UUID.randomUUID().toString().substring(0, 23);
		}

		@Override
		public Object getDefaultMarker() {
			return "DEFAULT";
		}
	}
}
