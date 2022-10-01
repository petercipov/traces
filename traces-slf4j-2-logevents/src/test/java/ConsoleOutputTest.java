import com.petercipov.traces.slf4j2.testutils.ConsoleBuffer;
import com.petercipov.traces.slf4j2.testutils.UserTrace;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.logevents.LogEventFactory;
import org.logevents.formatters.PatternLogEventFormatter;
import org.logevents.observers.ConsoleLogEventObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

public class ConsoleOutputTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(ConsoleOutputTest.class);

    @RegisterExtension
    static ConsoleBuffer buffer = new ConsoleBuffer();


    @Test
    public void markerIsFormattedIntoOutput() {
        LogEventFactory instance = LogEventFactory.getInstance();
        ConsoleLogEventObserver observer = new ConsoleLogEventObserver(new PatternLogEventFormatter("[%marker] %message"));
        observer.setThreshold(Level.INFO);
        instance.addObserver(LOGGER, observer);

        UserTrace trace = UserTrace.of("Ib30afb52-64c1-4304-8cf4-f31af3f627dc")
                        .withContextOf("A1177c091-843c-4fbf-a4bd-a59e40d80eca", "Uc0b6ae13-5c8d-4cb0-b95b-c9a3c2f1e54f");

        LOGGER.info(trace, "ABCD");

        Assertions.assertEquals("[accountId:A1177c091-843c-4fbf-a4bd-a59e40d80eca, userId:Uc0b6ae13-5c8d-4cb0-b95b-c9a3c2f1e54f, invocationId:Ib30afb52-64c1-4304-8cf4-f31af3f627dc] ABCD", buffer.getConsoleOutput().trim());
    }
}
