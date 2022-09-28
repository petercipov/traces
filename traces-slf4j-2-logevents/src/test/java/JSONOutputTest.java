import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.petercipov.traces.slf4j2.logevents.TracedMarkerConsoleJsonLogEventFormatter;
import com.petercipov.traces.slf4j2.testutils.ConsoleBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.logevents.LogEventFactory;
import org.logevents.observers.ConsoleLogEventObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.util.HashMap;

public class JSONOutputTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(JSONOutputTest.class);

    @RegisterExtension
    static ConsoleBuffer buffer = new ConsoleBuffer();


    @Test
    public void markerIsFormattedIntoOutput() {
        LogEventFactory instance = LogEventFactory.getInstance();
        ConsoleLogEventObserver observer = new ConsoleLogEventObserver(new TracedMarkerConsoleJsonLogEventFormatter(new HashMap<>(), "abc"));
        observer.setThreshold(Level.INFO);
        instance.addObserver(LOGGER, observer);

        UserTrace trace = UserTrace.of("Ib30afb52-64c1-4304-8cf4-f31af3f627dc")
                .withContextOf("A1177c091-843c-4fbf-a4bd-a59e40d80eca", "Uc0b6ae13-5c8d-4cb0-b95b-c9a3c2f1e54f");

        LOGGER.info(trace, "ABCD");

        JsonObject json = new Gson().fromJson(buffer.getConsoleOutput().trim(), JsonObject.class);
        json.remove("@timestamp");
        json.remove("hostname");

        Assertions.assertEquals("{\"level\":\"INFO\",\"logger\":\"JSONOutputTest\",\"messageFormat\":\"ABCD\",\"thread\":\"main\",\"message\":\"ABCD\",\"app\":\"junit-rt\",\"levelInt\":20,\"accountId\":\"A1177c091-843c-4fbf-a4bd-a59e40d80eca\",\"userId\":\"Uc0b6ae13-5c8d-4cb0-b95b-c9a3c2f1e54f\",\"invocationId\":\"Ib30afb52-64c1-4304-8cf4-f31af3f627dc\"}", json.toString());
    }
}
