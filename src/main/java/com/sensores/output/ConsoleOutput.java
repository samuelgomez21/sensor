package com.sensores.output;

import java.util.Objects;
import java.util.logging.Logger;

public final class ConsoleOutput implements Output {
    private static final Logger LOGGER = Logger.getLogger(ConsoleOutput.class.getName());

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%n");
    }

    @Override
    public void println(String message) {
        LOGGER.info(Objects.requireNonNull(message));
    }

    @Override
    public void blankLine() {
        LOGGER.info("");
    }
}
