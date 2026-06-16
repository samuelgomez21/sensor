package com.sensores.app;

import com.sensores.input.Input;
import com.sensores.output.Output;
import java.util.Objects;

public final class MonitoringApplication {
    private final DeviceMenu deviceMenu;

    public MonitoringApplication(Output output, Input input) {
        Output checkedOutput = Objects.requireNonNull(output);
        Input checkedInput = Objects.requireNonNull(input);
        this.deviceMenu = new DeviceMenu(new DeviceCatalog(checkedOutput).getDevices(), checkedInput, checkedOutput);
    }

    public void run() {
        boolean running;
        do {
            running = deviceMenu.showAndHandleOption();
        } while (running);
    }
}
