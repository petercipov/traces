package com.petercipov.traces.logbackslf4j1;

import com.petercipov.traces.api.Traced;

import java.util.function.BiConsumer;

public class UserTrace extends TraceAwareMarker implements Traced {

    private String accountId;
    private String userId;
    private String invocationId;

    @Override
    public void apply(BiConsumer<String, Object> context) {
        context.accept("accountId", accountId);
        context.accept("userId", userId);
        context.accept("invocationId", invocationId);
    }

    public UserTrace withContextOf(String accountId, String userId) {
        UserTrace trace = new UserTrace();
        trace.invocationId = this.invocationId;
        trace.accountId = accountId;
        trace.userId = userId;
        return trace;
    }

    public static UserTrace of(String invocationId) {
        UserTrace trace =  new UserTrace();
        trace.invocationId = invocationId;
        return trace;
    }

    @Override
    public Traced getTraced() {
        return this;
    }
}
