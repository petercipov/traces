package com.petercipov.traces.slf4j2.logback.contrib.json;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.contrib.json.classic.JsonLayout;
import com.petercipov.traces.api.Traced;
import com.petercipov.traces.api.TracedAware;
import org.slf4j.Marker;

import java.util.List;
import java.util.Map;

public class TracedJsonLayout extends JsonLayout {

    @Override
    protected void addCustomDataToJsonMap(Map<String, Object> map, ILoggingEvent event) {
        List<Marker> markers = event.getMarkerList();
        for (Marker marker: markers) {
            if (marker instanceof TracedAware) {
                Traced traced = ((TracedAware) marker).getTraced();
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
}