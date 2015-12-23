package com.petercipov.traces.api;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pcipov
 */
public class LevelTest {
	private static final int EXPECTED_LEVELS_COUNT = 4;
	
	@Test
	public void levelEnablingRules() {
		assertEquals(EXPECTED_LEVELS_COUNT, Level.values().length);
		
		assertTrue(Level.ERROR.enables(Level.ERROR));
		assertFalse(Level.ERROR.enables(Level.WARN));
		assertFalse(Level.ERROR.enables(Level.INFO));
		assertFalse(Level.ERROR.enables(Level.DEBUG));
		
		assertTrue(Level.WARN.enables(Level.ERROR));
		assertTrue(Level.WARN.enables(Level.WARN));
		assertFalse(Level.WARN.enables(Level.INFO));
		assertFalse(Level.WARN.enables(Level.DEBUG));
		
		assertTrue(Level.INFO.enables(Level.ERROR));
		assertTrue(Level.INFO.enables(Level.WARN));
		assertTrue(Level.INFO.enables(Level.INFO));
		assertFalse(Level.INFO.enables(Level.DEBUG));
		
		assertTrue(Level.DEBUG.enables(Level.ERROR));
		assertTrue(Level.DEBUG.enables(Level.WARN));
		assertTrue(Level.DEBUG.enables(Level.INFO));
		assertTrue(Level.DEBUG.enables(Level.DEBUG));
	}
}
