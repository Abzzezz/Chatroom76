package net.bplaced.abzzezz.engine.utils;

public class Logger {

    public static void log(final String message, final LogType logType) {
        System.out.println("[".concat(logType.name()) + "] ".concat(message));
    }
}
