package com.petercipov.traces.slf4j2;

import com.petercipov.traces.api.TracedAware;
import org.slf4j.Marker;

import java.util.Iterator;

public abstract class TraceAwareMarker implements Marker, TracedAware {

    @Override
    public String getName() {
        return "TracedMarker";
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
    public boolean hasChildren() {
        return true;
    }

    @Override
    public boolean hasReferences() {
        return true;
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
        return getName();
    }
}

