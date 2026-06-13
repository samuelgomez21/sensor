package com.sensores.monitoring;

import com.sensores.interfaces.Monitorable;
import com.sensores.output.Output;
import java.util.Objects;

public final class Sensor {
    private final Monitorable device;
    private final Output output;

    public Sensor(Monitorable device, Output output) {
        this.device = Objects.requireNonNull(device);
        this.output = Objects.requireNonNull(output);
    }

    public void monitor() {
        output.println(device.getStatus());
    }
}
