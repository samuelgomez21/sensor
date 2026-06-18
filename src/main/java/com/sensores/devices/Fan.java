package com.sensores.devices;

import com.sensores.interfaces.Switchable;
import com.sensores.output.Output;
import java.util.Objects;

public final class Fan implements Switchable {
    private final Output output;

    public Fan(Output output) {
        this.output = Objects.requireNonNull(output);
    }

    @Override
    public void turnOn() {
        output.println("Ventilador encendido.");
    }

    @Override
    public void turnOff() {
        output.println("Ventilador apagado.");
    }

    @Override
    public String toString() {
        return "Ventilador";
    }
}
