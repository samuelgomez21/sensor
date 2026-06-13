package com.sensores.devices;

import com.sensores.interfaces.Monitorable;
import com.sensores.interfaces.Switchable;
import com.sensores.output.Output;
import java.util.Objects;

public final class SmartFridge implements Switchable, Monitorable {
    private static final String DEVICE_NAME = "SmartFridge";
    private static final int INTERNAL_TEMPERATURE = 4;

    private final Output output;
    private boolean on;

    public SmartFridge(Output output) {
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
        return DEVICE_NAME + " -> " + (on ? "Activo" : "Apagado")
                + ", Temperatura interna: " + INTERNAL_TEMPERATURE + "°C";
    }
}
