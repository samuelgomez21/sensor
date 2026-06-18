package com.sensores.devices;

import com.sensores.interfaces.Monitorable;
import com.sensores.interfaces.Switchable;
import com.sensores.output.Output;
import java.util.Objects;

public final class ServerDevice implements Switchable, Monitorable {
    private final Output output;
    private boolean on;

    public ServerDevice(Output output) {
        this.output = Objects.requireNonNull(output);
    }

    @Override
    public void turnOn() {
        on = true;
        output.println("Servidor encendido.");
    }

    @Override
    public void turnOff() {
        on = false;
        output.println("Servidor apagado.");
    }

    @Override
    public String getStatus() {
        return "Servidor: " + state() + " | Uso de CPU: 65%";
    }

    private String state() {
        return on ? "encendido" : "apagado";
    }

    @Override
    public String toString() {
        return "Servidor";
    }
}
