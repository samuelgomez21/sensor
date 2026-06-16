package com.sensores.app;

import com.sensores.input.Input;
import com.sensores.output.Output;
import java.util.List;
import java.util.Objects;

public final class MonitoringApplication {
    private final Input input;
    private final ConsoleMenu menu;
    private final MenuCommandHandler commandHandler;

    public MonitoringApplication(Output output, Input input) {
        Output checkedOutput = Objects.requireNonNull(output);
        this.input = Objects.requireNonNull(input);

        List<RegisteredDevice> devices = new DeviceRegistry(checkedOutput).createDevices();
        DeviceListView deviceListView = new DeviceListView(checkedOutput);
        DeviceSelector deviceSelector = new DeviceSelector(input, checkedOutput, deviceListView);
        DeviceOperationService operationService =
                new DeviceOperationService(devices, deviceListView, deviceSelector, checkedOutput);

        this.menu = new ConsoleMenu(checkedOutput);
        this.commandHandler = new MenuCommandHandler(operationService, checkedOutput);
    }

    public void run() {
        boolean running;
        do {
            menu.show();
            running = commandHandler.handle(input.readLine().trim());
        } while (running);
    }
}
