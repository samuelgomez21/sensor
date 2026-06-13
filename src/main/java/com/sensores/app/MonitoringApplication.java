package com.sensores.app;

import com.sensores.control.Switch;
import com.sensores.devices.AirConditioner;
import com.sensores.devices.Fan;
import com.sensores.devices.LightBulb;
import com.sensores.devices.ServerDevice;
import com.sensores.devices.SmartFridge;
import com.sensores.monitoring.Sensor;
import com.sensores.output.Output;
import java.util.Objects;

public final class MonitoringApplication {
    private final Output output;

    public MonitoringApplication(Output output) {
        this.output = Objects.requireNonNull(output);
    }

    public void run() {
        output.println("=== Switch con dispositivos normales ===");
        new Switch(new LightBulb(output)).operate();
        new Switch(new Fan(output)).operate();

        output.blankLine();
        output.println("=== Switch con dispositivos inteligentes ===");
        SmartFridge smartFridge = new SmartFridge(output);
        AirConditioner airConditioner = new AirConditioner(output);
        ServerDevice serverDevice = new ServerDevice(output);

        new Switch(smartFridge).operate();
        new Switch(airConditioner).operate();
        new Switch(serverDevice).operate();

        output.blankLine();
        output.println("=== Monitoreo ===");
        new Sensor(smartFridge, output).monitor();
        new Sensor(airConditioner, output).monitor();
        new Sensor(serverDevice, output).monitor();

        output.blankLine();
        output.println("=== Dispositivos no monitoreables ===");
        output.println("LightBulb y Fan no pueden pasar al Sensor por diseño.");
    }
}
