/*
 * @author Roman
 * Last modified: 06.01.21, 22:15 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.command;

import net.bplaced.abzzezz.core.Basic;

import java.util.Arrays;

public interface Command extends Basic {

    /**
     * Execute the command
     *
     *
     * @param in string to get arguments from
     */
    void execute(final String in);

    /*
    Array of all triggers
     */
    String[] trigger();

    default String[] getArguments(final String in) {
        final String[] rawArgs = in.trim().split("\\s+");
        return Arrays.copyOfRange(rawArgs, 1, rawArgs.length);
    }
}
