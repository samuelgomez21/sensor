package com.sensores.devices;

import com.sensores.interfaces.Monitorable;
import com.sensores.interfaces.Switchable;
import com.sensores.output.Output;
import java.util.Objects;

public final class AirConditioner implements Switchable, Monitorable {
    private final Output output;
    private boolean on;

    public AirConditioner(Output output) {
        this.output = Objects.requireNonNull(output);
    }

    @Override
    public void turnOn() {
        on = true;
        output.println("Aire acondicionado encendido.");
    }

    @Override
    public void turnOff() {
        on = false;
        output.println("Aire acondicionado apagado.");
    }

    @Override
    public String getStatus() {
        return "Aire acondicionado: " + state() + " | Temperatura: 22 °C";
    }

    private String state() {
        return on ? "encendido" : "apagado";
    }

    @Override
    public String toString() {
        return "Aire acondicionado";
    }
}
