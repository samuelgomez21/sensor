package com.sensores.app;

import com.sensores.input.ConsoleInput;
import com.sensores.output.ConsoleOutput;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        try (ConsoleInput input = new ConsoleInput()) {
            new MonitoringApplication(new ConsoleOutput(), input).run();
        }
    }
}
