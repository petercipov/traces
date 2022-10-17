package com.petercipov.traces.slf4j2;

import com.petercipov.traces.api.Traced;
import com.petercipov.traces.api.TracedAware;
import org.slf4j.Marker;

import java.util.Iterator;

public class TraceAwareMarker implements Marker, TracedAware {

    private final Traced traced;


    public TraceAwareMarker(Traced traced) {
        this.traced = traced;
    }

    @Override
    public String getName() {
        return toString();
    }

    @Override
    public void add(Marker marker) {
        //NOTHING
    }

    @Override
    public boolean remove(Marker marker) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean hasChildren() {
        return true;
    }

    @Override
    public boolean hasReferences() {
        return false;
    }

    @Override
    public Iterator<Marker> iterator() {
        return null;
    }

    @Override
    public boolean contains(Marker marker) {
        return false;
    }

    @Override
    public boolean contains(String s) {
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        int sizeAfterPrefix = sb.length();
        getTraced().apply((key, value) -> {
            sb.append(key).append(": ").append(value).append(", ");
        });

        if (sb.length() > sizeAfterPrefix) {
            sb.setLength(sb.length() - 2);
        }

        return sb.toString();
    }

    @Override
    public Traced getTraced() {
        return traced;
    }
}

