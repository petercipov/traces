package com.petercipov.traces.stdio;

import java.util.Arrays;

/**
 *
 * @author petercipov
 */
class Printer {

	private static final String HEADER = "%10d | %23s | %-3d | %-15s | ";
	private final String guid;

	public Printer(String guid) {
		this.guid = guid;
	}
	
	public void printTraceEnd(int id) {
		printHeader(id);
		System.out.println("Finished");
	}

	private void printHeader(int id) {
		System.out.format(HEADER,
			System.currentTimeMillis(),
			guid,
			id,
			Thread.currentThread().getName()
		);
	}
	
	public void printlnSimpleEvent(int id, String value, Object... params) {
		printHeader(id);
		processParams(params);
		System.out.print(value);
		if (params.length > 0) {
			System.out.print(", ");
			System.out.print(Arrays.toString(params));
		}
		System.out.println();
	}
	
	public void printlnEventStart(int id, String value, Object... params) {
		printHeader(id);
		processParams(params);
		System.out.print(value);
		if (params.length > 0) {
			System.out.print(", ");
			System.out.print(Arrays.toString(params));
		}
		System.out.println();
	}

	public void printlnEventEnd(int id, int refId) {
		printHeader(id);
		System.out.format("end of: %-3d", refId);
		System.out.println();
	}
	
	private void processParams(Object[] params) {
		Object param;
		for (int i=0; i<params.length; i++) {
			param = params[i];
			if (param == null) {
				param = "null";
			} else {
				if (param instanceof Throwable) {
					param = stringify((Throwable)param);
				} else {
					param = stringify(param);
				}
			}
			params[i] = param;
		}
	}

	private StringBuilder stringify(Throwable th) {
		StringBuilder sb = new StringBuilder(70)
			.append("errorMessage: ")
			.append(th)
			.append("# ")
			.append("stack: ");
		
		for (StackTraceElement element : th.getStackTrace()) {
			sb.append(stringify(element)).append("# ");
		}
		return sb;
	}
	
	private StringBuilder stringify(StackTraceElement th) {
		
		StringBuilder sb = new StringBuilder()
			.append(th.getClassName()).append(":")
			.append(th.getMethodName()).append(":")
			.append(th.isNativeMethod() ? "native" :  th.getLineNumber()).append(":")
			.append(th.getFileName() == null ? "Unknown" : th.getFileName())
		;
		return sb;
	}

	private String stringify(Object param) {
		return param.toString();
	}
}
