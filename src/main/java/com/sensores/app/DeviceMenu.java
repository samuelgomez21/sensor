package com.sensores.app;

import com.sensores.control.Switch;
import com.sensores.input.Input;
import com.sensores.interfaces.Monitorable;
import com.sensores.monitoring.Sensor;
import com.sensores.output.Output;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class DeviceMenu {
    private final List<RegisteredDevice> devices;
    private final Input input;
    private final Output output;

    public DeviceMenu(List<RegisteredDevice> devices, Input input, Output output) {
        this.devices = List.copyOf(Objects.requireNonNull(devices));
        this.input = Objects.requireNonNull(input);
        this.output = Objects.requireNonNull(output);
    }

    public boolean showAndHandleOption() {
        showMenu();
        return handleOption(input.readLine().trim());
    }

    private void showMenu() {
        output.blankLine();
        output.println("=== Sistema de monitoreo de dispositivos ===");
        output.println("1. Ver dispositivos");
        output.println("2. Encender dispositivo");
        output.println("3. Apagar dispositivo");
        output.println("4. Monitorear dispositivo");
        output.println("5. Ejecutar demostración automática");
        output.println("0. Salir");
        output.println("Seleccione una opción:");
    }

    private boolean handleOption(String option) {
        switch (option) {
            case "1" -> showDevices();
            case "2" -> selectDevice().ifPresent(device -> device.getSwitchable().turnOn());
            case "3" -> selectDevice().ifPresent(device -> device.getSwitchable().turnOff());
            case "4" -> monitorSelectedDevice();
            case "5" -> runAutomaticDemo();
            case "0" -> {
                output.println("Programa finalizado.");
                return false;
            }
            default -> output.println("Opción no válida.");
        }
        return true;
    }

    private void showDevices() {
        output.blankLine();
        output.println("=== Dispositivos registrados ===");
        for (int index = 0; index < devices.size(); index++) {
            RegisteredDevice device = devices.get(index);
            String monitorableText = device.isMonitorable() ? "monitoreable" : "no monitoreable";
            output.println((index + 1) + ". " + device.getName() + " - " + monitorableText);
        }
    }

    private void monitorSelectedDevice() {
        Optional<RegisteredDevice> selectedDevice = selectDevice();
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

    private Optional<RegisteredDevice> selectDevice() {
        showDevices();
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

    private void runAutomaticDemo() {
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
