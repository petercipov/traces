package com.petercipov.traces.logbackslf4j1;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.contrib.json.classic.JsonLayout;
import com.petercipov.traces.api.Traced;
import org.slf4j.Marker;

import java.util.Map;

public class TracedJsonLayout extends JsonLayout {

    @Override
    protected void addCustomDataToJsonMap(Map<String, Object> map, ILoggingEvent event) {
        Marker marker = event.getMarker();
        if (marker instanceof TraceAwareMarker) {
            Traced traced = ((TraceAwareMarker) marker).getTraced();
            traced.apply((key, value) -> {
                if (value == null) {
                    map.remove(key);
                } else {
                    map.put(key, value);
                }
            });
        }
    }
}
