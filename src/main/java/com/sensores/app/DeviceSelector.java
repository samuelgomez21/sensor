package com.sensores.app;

import com.sensores.input.Input;
import com.sensores.output.Output;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class DeviceSelector {
    private final Input input;
    private final Output output;
    private final DeviceListView deviceListView;

    public DeviceSelector(Input input, Output output, DeviceListView deviceListView) {
        this.input = Objects.requireNonNull(input);
        this.output = Objects.requireNonNull(output);
        this.deviceListView = Objects.requireNonNull(deviceListView);
    }

    public Optional<RegisteredDevice> selectFrom(List<RegisteredDevice> devices) {
        deviceListView.show(devices);
        output.println("Seleccione el número del dispositivo:");

        String value = input.readLine().trim();
        try {
            int selectedIndex = Integer.parseInt(value) - 1;
            if (selectedIndex >= 0 && selectedIndex < devices.size()) {
                return Optional.of(devices.get(selectedIndex));
            }
        } catch (NumberFormatException exception) {
            output.println("Debe ingresar un número.");
            return Optional.empty();
        }

        output.println("Dispositivo no válido.");
        return Optional.empty();
    }
}
