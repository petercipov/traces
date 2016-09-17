package com.petercipov.traces.api;

/**
 * Created by petercipov on 17/09/16.
 */
public interface TraceConfiguration {

    boolean isEnabled(Object marker);

    String generateUUID();

    Object getDefaultMarker();

}
