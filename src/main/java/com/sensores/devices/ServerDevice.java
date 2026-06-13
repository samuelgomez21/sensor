package com.sensores.devices;

import com.sensores.interfaces.Monitorable;
import com.sensores.interfaces.Switchable;
import com.sensores.output.Output;
import java.util.Objects;

public final class ServerDevice implements Switchable, Monitorable {
    private static final String DEVICE_NAME = "ServerDevice";
    private static final int CPU_USAGE = 65;

    private final Output output;
    private boolean on;

    public ServerDevice(Output output) {
        this.output = Objects.requireNonNull(output);
    }

    @Override
    public void turnOn() {
        on = true;
        output.println(DEVICE_NAME + " encendido.");
    }

    @Override
    public void turnOff() {
        on = false;
        output.println(DEVICE_NAME + " apagado.");
    }

    @Override
    public String getStatus() {
        return DEVICE_NAME + " -> " + (on ? "Activo" : "Apagado") + ", Uso de CPU: " + CPU_USAGE + "%";
    }
}
