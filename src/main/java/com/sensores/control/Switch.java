package com.sensores.control;

import com.sensores.interfaces.Switchable;
import java.util.Objects;

public final class Switch {
    private final Switchable device;

    public Switch(Switchable device) {
        this.device = Objects.requireNonNull(device);
    }

    public void turnOn() {
        device.turnOn();
    }

    public void turnOff() {
        device.turnOff();
    }
}
