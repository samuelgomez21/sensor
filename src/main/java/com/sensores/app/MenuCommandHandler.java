package com.sensores.app;

import com.sensores.output.Output;
import java.util.Objects;

public final class MenuCommandHandler {
    private static final String EXIT_OPTION = "0";

    private final DeviceOperationService deviceOperationService;
    private final Output output;

    public MenuCommandHandler(DeviceOperationService deviceOperationService, Output output) {
        this.deviceOperationService = Objects.requireNonNull(deviceOperationService);
        this.output = Objects.requireNonNull(output);
    }

    public boolean handle(String option) {
        switch (option) {
            case "1" -> deviceOperationService.showDevices();
            case "2" -> deviceOperationService.turnOnDevice();
            case "3" -> deviceOperationService.turnOffDevice();
            case "4" -> deviceOperationService.monitorDevice();
            case "5" -> deviceOperationService.runAutomaticDemo();
            case EXIT_OPTION -> {
                output.println("Programa finalizado.");
                return false;
            }
            default -> output.println("Opción no válida.");
        }
        return true;
    }
}
