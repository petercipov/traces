package com.petercipov.traces.slf4j2.logevents;

import com.petercipov.traces.api.TracedAware;
import org.logevents.LogEvent;
import org.logevents.formatters.ConsoleJsonLogEventFormatter;

import java.util.Map;

public class TracedMarkerConsoleJsonLogEventFormatter extends ConsoleJsonLogEventFormatter {

    public TracedMarkerConsoleJsonLogEventFormatter(Map<String, String> properties, String prefix) {
        super(properties, prefix);
    }

    @Override
    public Map<String, Object> toJsonObject(LogEvent event) {
        Map<String, Object> o = super.toJsonObject(event);
        if (event.getMarker() instanceof TracedAware) {
            o.remove("marker");
            TracedAware aware = (TracedAware) event.getMarker();
            aware.getTraced().apply(o::put);
        }
        return o;
    }
}
