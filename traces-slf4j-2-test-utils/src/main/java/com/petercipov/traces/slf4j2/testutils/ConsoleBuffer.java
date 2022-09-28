package com.petercipov.traces.slf4j2.testutils;

import org.junit.jupiter.api.extension.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ConsoleBuffer implements BeforeAllCallback, BeforeEachCallback, AfterAllCallback, AfterEachCallback {

    private PrintStream oldSystemOut;
    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private final PrintStream bufferedPrintStream = new PrintStream(buffer);

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        oldSystemOut = System.out;
        System.setOut(bufferedPrintStream);
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        System.setOut(bufferedPrintStream);
        buffer.reset();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        System.setOut(oldSystemOut);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        System.setOut(bufferedPrintStream);
        buffer.reset();
    }

    public String getConsoleOutput() {
        return buffer.toString();
    }


}
