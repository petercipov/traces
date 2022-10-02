package com.petercipov.traces.slf4j2.logback;

import com.petercipov.traces.slf4j2.testutils.ConsoleBuffer;
import com.petercipov.traces.slf4j2.testutils.UserTrace;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class MarkerTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(MarkerTest.class);

    @RegisterExtension
    static ConsoleBuffer buffer = new ConsoleBuffer();

    @Test
    public void markerIsProperlyTransformedIntoOutput() throws Exception {
        final LoggerContext context = LoggerContext.getContext(false);
        final Configuration config = context.getConfiguration();
        final PatternLayout layout = PatternLayout
                .newBuilder()
                .withConfiguration(config)
                .withPattern("[%marker] - %m")
                .build();
        final ConsoleAppender appender = ConsoleAppender.createDefaultAppenderForLayout(layout);

        appender.start();

        Field field = LOGGER.getClass().getDeclaredField("logger");
        field.setAccessible(true);
        org.apache.logging.log4j.core.Logger l = (org.apache.logging.log4j.core.Logger)field.get(LOGGER);
        l.addAppender(appender);
        l.setLevel(Level.DEBUG);
        l.setAdditive(false);


        UserTrace trace = UserTrace
                .of("I0f6bf39e-639c-4a53-a818-7ff9fb8482af")
                .withContextOf("Ac32463a2-3466-463a-b02c-bb5fe1561b80", "U30361475-6ea0-4904-a767-fa0c54e486bb");

        LOGGER.info(trace, "XYZ");

        Assertions.assertEquals("[accountId: Ac32463a2-3466-463a-b02c-bb5fe1561b80, " +
                "userId: U30361475-6ea0-4904-a767-fa0c54e486bb, " +
                "invocationId: I0f6bf39e-639c-4a53-a818-7ff9fb8482af] - XYZ", buffer.getConsoleOutput());
    }
}
