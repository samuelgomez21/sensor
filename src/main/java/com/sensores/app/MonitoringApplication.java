package com.sensores.app;

import com.sensores.control.Switch;
import com.sensores.devices.AirConditioner;
import com.sensores.devices.Fan;
import com.sensores.devices.LightBulb;
import com.sensores.devices.ServerDevice;
import com.sensores.devices.SmartFridge;
import com.sensores.input.Input;
import com.sensores.interfaces.Monitorable;
import com.sensores.monitoring.Sensor;
import com.sensores.output.Output;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class MonitoringApplication {
    private static final String EXIT_OPTION = "0";

    private final Output output;
    private final Input input;
    private final List<RegisteredDevice> devices;

    public MonitoringApplication(Output output, Input input) {
        this.output = Objects.requireNonNull(output);
        this.input = Objects.requireNonNull(input);
        this.devices = createDevices(output);
    }

    public void run() {
        String option;
        do {
            showMenu();
            option = input.readLine().trim();
            handleOption(option);
        } while (!EXIT_OPTION.equals(option));
    }

    private List<RegisteredDevice> createDevices(Output output) {
        LightBulb lightBulb = new LightBulb(output);
        Fan fan = new Fan(output);
        SmartFridge smartFridge = new SmartFridge(output);
        AirConditioner airConditioner = new AirConditioner(output);
        ServerDevice serverDevice = new ServerDevice(output);

        return List.of(
                new RegisteredDevice("LightBulb", lightBulb),
                new RegisteredDevice("Fan", fan),
                new RegisteredDevice("SmartFridge", smartFridge, smartFridge),
                new RegisteredDevice("AirConditioner", airConditioner, airConditioner),
                new RegisteredDevice("ServerDevice", serverDevice, serverDevice));
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

    private void handleOption(String option) {
        switch (option) {
            case "1" -> showDevices();
            case "2" -> turnOnDevice();
            case "3" -> turnOffDevice();
            case "4" -> monitorDevice();
            case "5" -> runAutomaticDemo();
            case EXIT_OPTION -> output.println("Programa finalizado.");
            default -> output.println("Opción no válida.");
        }
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

    private void turnOnDevice() {
        selectDevice().ifPresent(device -> device.getSwitchable().turnOn());
    }

    private void turnOffDevice() {
        selectDevice().ifPresent(device -> device.getSwitchable().turnOff());
    }

    private void monitorDevice() {
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
