package com.sensores.input;

import java.util.Scanner;

public final class ConsoleInput implements Input, AutoCloseable {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public void close() {
        scanner.close();
    }
}
