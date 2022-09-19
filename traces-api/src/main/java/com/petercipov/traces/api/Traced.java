package com.petercipov.traces.api;

import java.util.function.BiConsumer;

public interface Traced {

    void apply(BiConsumer<String, Object> contextApply);

}
