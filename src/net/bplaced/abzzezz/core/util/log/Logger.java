/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.core.util.log;

public class Logger {

    public static void log(final String message, final LogType logType) {
        System.out.println("[".concat(logType.name()) + "] ".concat(message));
    }

    public static void logf(final String message, final LogType logType, final Object... objects) {
        System.out.printf("[".concat(logType.name()) + "] ".concat(message), objects);
    }

}
