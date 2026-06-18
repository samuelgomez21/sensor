package com.sensores.devices;

import com.sensores.interfaces.Monitorable;
import com.sensores.interfaces.Switchable;
import com.sensores.output.Output;
import java.util.Objects;

public final class SmartFridge implements Switchable, Monitorable {
    private final Output output;
    private boolean on;

    public SmartFridge(Output output) {
        this.output = Objects.requireNonNull(output);
    }

    @Override
    public void turnOn() {
        on = true;
        output.println("Nevera inteligente encendida.");
    }

    @Override
    public void turnOff() {
        on = false;
        output.println("Nevera inteligente apagada.");
    }

    @Override
    public String getStatus() {
        return "Nevera inteligente: " + state() + " | Temperatura: 4 °C";
    }

    private String state() {
        return on ? "encendida" : "apagada";
    }

    @Override
    public String toString() {
        return "Nevera inteligente";
    }
}
