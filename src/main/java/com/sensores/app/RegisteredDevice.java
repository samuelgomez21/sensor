package com.sensores.app;

import com.sensores.interfaces.Monitorable;
import com.sensores.interfaces.Switchable;
import java.util.Objects;
import java.util.Optional;

public final class RegisteredDevice {
    private final String name;
    private final Switchable switchable;
    private final Monitorable monitorable;

    public RegisteredDevice(String name, Switchable switchable) {
        this(name, switchable, null);
    }

    public RegisteredDevice(String name, Switchable switchable, Monitorable monitorable) {
        this.name = Objects.requireNonNull(name);
        this.switchable = Objects.requireNonNull(switchable);
        this.monitorable = monitorable;
    }

    public String getName() {
        return name;
    }

    public Switchable getSwitchable() {
        return switchable;
    }

    public Optional<Monitorable> getMonitorable() {
        return Optional.ofNullable(monitorable);
    }

    public boolean isMonitorable() {
        return monitorable != null;
    }
}
