package com.sensores.app;

import com.sensores.output.Output;
import java.util.Objects;

public final class ConsoleMenu {
    private final Output output;

    public ConsoleMenu(Output output) {
        this.output = Objects.requireNonNull(output);
    }

    public void show() {
        output.blankLine();
        output.println("=== Sistema de monitoreo de dispositivos ===");
        output.println("1. Ver dispositivos");
        output.println("2. Encender dispositivo");
        output.println("3. Apagar dispositivo");
        output.println("4. Monitorear dispositivo");
        output.println("5. Ejecutar demostración automática");
        output.println("0. Salir");
        output.println("Seleccione una opción:");
    }
}
