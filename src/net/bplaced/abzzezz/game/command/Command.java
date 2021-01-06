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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Command extends Basic {

    /**
     * Execute the command
     *
     * @param in string to get arguments from
     */
    void execute(final String in);

    /*
    Array of all triggers
     */
    String[] trigger();

    default String[] getArguments(final String in) {
        final String[] rawArgs = in.split("\\s+");
        return Arrays.copyOfRange(rawArgs, 1, rawArgs.length);
    }
}
