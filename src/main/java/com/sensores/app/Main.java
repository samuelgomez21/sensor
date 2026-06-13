package com.sensores.app;

import com.sensores.output.ConsoleOutput;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        new MonitoringApplication(new ConsoleOutput()).run();
    }
}
