package com.petercipov.traces.logbackslf4j1;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TracedTest {
    private static Logger logger = LoggerFactory.getLogger(TracedTest.class);

    @Test
    public void tracedCanBePassedAsMarker() {
        UserTrace trace = UserTrace
                .of("IN"+ UUID.randomUUID())
                .withContextOf(
                "AC"+UUID.randomUUID(),
                "US"+UUID.randomUUID()
                );
        logger.info(trace, "user request received");

        MarkerFactory.getMarker("marker");
    }
}