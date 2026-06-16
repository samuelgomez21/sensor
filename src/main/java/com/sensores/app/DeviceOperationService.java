package com.sensores.app;

import com.sensores.control.Switch;
import com.sensores.interfaces.Monitorable;
import com.sensores.monitoring.Sensor;
import com.sensores.output.Output;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class DeviceOperationService {
    private final List<RegisteredDevice> devices;
    private final DeviceListView deviceListView;
    private final DeviceSelector deviceSelector;
    private final Output output;

    public DeviceOperationService(
            List<RegisteredDevice> devices,
            DeviceListView deviceListView,
            DeviceSelector deviceSelector,
            Output output) {
        this.devices = List.copyOf(Objects.requireNonNull(devices));
        this.deviceListView = Objects.requireNonNull(deviceListView);
        this.deviceSelector = Objects.requireNonNull(deviceSelector);
        this.output = Objects.requireNonNull(output);
    }

    public void showDevices() {
        deviceListView.show(devices);
    }

    public void turnOnDevice() {
        deviceSelector.selectFrom(devices).ifPresent(device -> device.getSwitchable().turnOn());
    }

    public void turnOffDevice() {
        deviceSelector.selectFrom(devices).ifPresent(device -> device.getSwitchable().turnOff());
    }

    public void monitorDevice() {
        Optional<RegisteredDevice> selectedDevice = deviceSelector.selectFrom(devices);
        if (selectedDevice.isEmpty()) {
            return;
        }

        Optional<Monitorable> monitorable = selectedDevice.get().getMonitorable();
        if (monitorable.isEmpty()) {
            output.println(selectedDevice.get().getName() + " no es compatible con Sensor.");
            return;
        }

        new Sensor(monitorable.get(), output).monitor();
    }

    public void runAutomaticDemo() {
        output.blankLine();
        output.println("=== Switch con dispositivos normales ===");
        new Switch(devices.get(0).getSwitchable()).operate();
        new Switch(devices.get(1).getSwitchable()).operate();

        output.blankLine();
        output.println("=== Switch con dispositivos inteligentes ===");
        for (int index = 2; index < devices.size(); index++) {
            new Switch(devices.get(index).getSwitchable()).operate();
        }

        output.blankLine();
        output.println("=== Monitoreo ===");
        for (RegisteredDevice device : devices) {
            device.getMonitorable().ifPresent(monitorable -> new Sensor(monitorable, output).monitor());
        }

        output.blankLine();
        output.println("=== Dispositivos no monitoreables ===");
        output.println("LightBulb y Fan no pueden pasar al Sensor por diseño.");
    }
}
