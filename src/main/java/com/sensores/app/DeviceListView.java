package com.sensores.app;

import com.sensores.output.Output;
import java.util.List;
import java.util.Objects;

public final class DeviceListView {
    private final Output output;

    public DeviceListView(Output output) {
        this.output = Objects.requireNonNull(output);
    }

    public void show(List<RegisteredDevice> devices) {
        output.blankLine();
        output.println("=== Dispositivos registrados ===");
        for (int index = 0; index < devices.size(); index++) {
            RegisteredDevice device = devices.get(index);
            String monitorableText = device.isMonitorable() ? "monitoreable" : "no monitoreable";
            output.println((index + 1) + ". " + device.getName() + " - " + monitorableText);
        }
    }
}
