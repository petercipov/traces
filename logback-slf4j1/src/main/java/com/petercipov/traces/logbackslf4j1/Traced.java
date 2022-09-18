package com.petercipov.traces.logbackslf4j1;

import java.util.function.BiConsumer;

public interface Traced {

    void apply(BiConsumer<String, Object> contextApply);

}
