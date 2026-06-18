package com.sensores.devices;

import com.sensores.interfaces.Switchable;
import com.sensores.output.Output;
import java.util.Objects;

public final class LightBulb implements Switchable {
    private final Output output;

    public LightBulb(Output output) {
        this.output = Objects.requireNonNull(output);
    }

    @Override
    public void turnOn() {
        output.println("Bombilla encendida.");
    }

    @Override
    public void turnOff() {
        output.println("Bombilla apagada.");
    }

    @Override
    public String toString() {
        return "Bombilla";
    }
}
