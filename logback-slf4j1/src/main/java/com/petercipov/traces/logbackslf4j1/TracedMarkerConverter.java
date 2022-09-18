package com.petercipov.traces.logbackslf4j1;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.slf4j.Marker;

import java.util.Iterator;

public class TracedMarkerConverter extends ClassicConverter {
    @Override
    public String convert(final ILoggingEvent event) {
        String value = toString(event.getMarker());
        return value == null ? "" : value;
    }

    protected String toString(final Marker marker) {
        if (marker != null) {
            if (marker instanceof TraceAwareMarker) {
                return toString(((TraceAwareMarker) marker).get());
            } else {
                for (Iterator<Marker> it = marker.iterator(); it.hasNext(); ) {
                    String value = toString(it.next());
                    if (value != null) {
                        return value;
                    }
                }
            }
        }
        return null;
    }

    protected String toString(final Traced traced) {
        StringBuilder sb = new StringBuilder();
        traced.apply((key, val) -> {
            sb.append(key).append(": ").append(val).append(", ");
        });
        if (sb.length() > 1) {
            sb.setLength(sb.length()-2);
        }

        return sb.toString();
    }
}
