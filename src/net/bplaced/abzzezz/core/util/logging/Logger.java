package net.bplaced.abzzezz.core.util.logging;

public class Logger {

    public static void log(final String message, final LogType logType) {
        System.out.println("[".concat(logType.name()) + "] ".concat(message));
    }
}