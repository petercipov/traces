package com.petercipov.traces.api;

public enum Level {
	DEBUG,
	INFO,
	WARN,
	ERROR;

	public boolean enables(Level level) {
		switch(this) {
			case ERROR: 
				return level == ERROR;
			case WARN: 
				return level == WARN || level == ERROR;
			case INFO:
				return level == INFO || level == WARN || level == ERROR;
			case DEBUG:
				return true;
			default:
				return false;			
		}
		
	}
}
