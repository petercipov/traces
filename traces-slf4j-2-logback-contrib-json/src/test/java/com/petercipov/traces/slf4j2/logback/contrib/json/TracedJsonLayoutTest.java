package com.petercipov.traces.slf4j2.logback.contrib.json;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.petercipov.traces.slf4j2.testutils.ConsoleBuffer;
import com.petercipov.traces.slf4j2.testutils.UserTrace;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class TracedJsonLayoutTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(TracedJsonLayoutTest.class);

    @RegisterExtension
    static ConsoleBuffer buffer = new ConsoleBuffer();

    @Test
    public void markerIsProperlyTransformedIntoOutput() {

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

        TracedJsonLayout tracedJsonLayout = new TracedJsonLayout();
        tracedJsonLayout.setContext(lc);
        tracedJsonLayout.start();

        ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<>();
        appender.setContext(lc);
        appender.setLayout(tracedJsonLayout);
        appender.start();

        ch.qos.logback.classic.Logger ll = (ch.qos.logback.classic.Logger)LOGGER;
        ll.addAppender(appender);
        ll.setAdditive(false);
        ll.setLevel(Level.INFO);

        UserTrace trace = UserTrace
                .of("I0f6bf39e-639c-4a53-a818-7ff9fb8482af")
                .withContextOf("Ac32463a2-3466-463a-b02c-bb5fe1561b80", "U30361475-6ea0-4904-a767-fa0c54e486bb");

        LOGGER.info(trace, "XYZ");


        JsonObject json = new Gson().fromJson(buffer.getConsoleOutput().trim(), JsonObject.class);

        json.remove("timestamp");
        json.remove("thread");
        json.remove("logger");
        json.remove("context");


        Assertions.assertEquals(
                "{\"level\":\"INFO\",\"message\":\"XYZ\",\"accountId\":\"Ac32463a2-3466-463a-b02c-bb5fe1561b80\"," +
                "\"userId\":\"U30361475-6ea0-4904-a767-fa0c54e486bb\",\"invocationId\":\"I0f6bf39e-639c-4a53-a818-7ff9fb8482af\"}",
                json.toString());


    }

}