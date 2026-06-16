package com.sensores.app;

import com.sensores.devices.AirConditioner;
import com.sensores.devices.Fan;
import com.sensores.devices.LightBulb;
import com.sensores.devices.ServerDevice;
import com.sensores.devices.SmartFridge;
import com.sensores.output.Output;
import java.util.List;
import java.util.Objects;

public final class DeviceRegistry {
    private final Output output;

    public DeviceRegistry(Output output) {
        this.output = Objects.requireNonNull(output);
    }

    public List<RegisteredDevice> createDevices() {
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
}
