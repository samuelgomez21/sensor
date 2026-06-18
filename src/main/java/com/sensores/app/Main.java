package com.sensores.app;

import com.sensores.control.Switch;
import com.sensores.devices.AirConditioner;
import com.sensores.devices.Fan;
import com.sensores.devices.LightBulb;
import com.sensores.devices.ServerDevice;
import com.sensores.devices.SmartFridge;
import com.sensores.input.ConsoleInput;
import com.sensores.input.Input;
import com.sensores.interfaces.Monitorable;
import com.sensores.interfaces.Switchable;
import com.sensores.monitoring.Sensor;
import com.sensores.output.ConsoleOutput;
import com.sensores.output.Output;
import java.util.List;

public final class Main {
    private static final int INVALID_INDEX = -1;

    private Main() {
    }

    public static void main(String[] args) {
        Output output = new ConsoleOutput();
        List<Switchable> devices = createDevices(output);

        try (ConsoleInput input = new ConsoleInput()) {
            runMenu(devices, input, output);
        }
    }

    private static List<Switchable> createDevices(Output output) {
        return List.of(
                new LightBulb(output),
                new Fan(output),
                new SmartFridge(output),
                new AirConditioner(output),
                new ServerDevice(output));
    }

    private static void runMenu(List<Switchable> devices, Input input, Output output) {
        boolean running = true;

        while (running) {
            showMenu(output);
            String option = input.readLine().trim();

            switch (option) {
                case "1" -> showDevices(devices, output);
                case "2" -> changeDeviceState(devices, input, output, true);
                case "3" -> changeDeviceState(devices, input, output, false);
                case "4" -> monitorDevice(devices, input, output);
                case "0" -> running = false;
                default -> output.println("Opción no válida.");
            }
        }

        output.println("Programa finalizado.");
    }

    private static void showMenu(Output output) {
        output.blankLine();
        output.println("=== SISTEMA DE DISPOSITIVOS ===");
        output.println("1. Ver dispositivos");
        output.println("2. Encender dispositivo");
        output.println("3. Apagar dispositivo");
        output.println("4. Monitorear dispositivo");
        output.println("0. Salir");
        output.println("Seleccione una opción:");
    }

    private static void showDevices(List<Switchable> devices, Output output) {
        output.blankLine();
        output.println("=== DISPOSITIVOS ===");

        for (int index = 0; index < devices.size(); index++) {
            Switchable device = devices.get(index);
            String type = device instanceof Monitorable ? "monitoreable" : "no monitoreable";
            output.println((index + 1) + ". " + device + " - " + type);
        }
    }

    private static int selectDevice(
            List<Switchable> devices,
            Input input,
            Output output) {
        showDevices(devices, output);
        output.println("Seleccione un dispositivo:");

        try {
            int index = Integer.parseInt(input.readLine().trim()) - 1;
            if (index >= 0 && index < devices.size()) {
                return index;
            }
        } catch (NumberFormatException exception) {
            output.println("Debe escribir un número.");
            return INVALID_INDEX;
        }

        output.println("Dispositivo no válido.");
        return INVALID_INDEX;
    }

    private static void changeDeviceState(
            List<Switchable> devices,
            Input input,
            Output output,
            boolean turnOn) {
        int index = selectDevice(devices, input, output);
        if (index == INVALID_INDEX) {
            return;
        }

        Switch deviceSwitch = new Switch(devices.get(index));
        if (turnOn) {
            deviceSwitch.turnOn();
        } else {
            deviceSwitch.turnOff();
        }
    }

    private static void monitorDevice(
            List<Switchable> devices,
            Input input,
            Output output) {
        int index = selectDevice(devices, input, output);
        if (index == INVALID_INDEX) {
            return;
        }

        Switchable device = devices.get(index);
        if (device instanceof Monitorable monitorable) {
            new Sensor(monitorable, output).monitor();
        } else {
            output.println(device + " no se puede monitorear.");
        }
    }
}
