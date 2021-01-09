/*
 * @author Roman
 * Last modified: 09.01.21, 20:25 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.command.commands;

import net.bplaced.abzzezz.game.command.Command;
import net.bplaced.abzzezz.game.handler.CommandHandler;

import java.util.Arrays;
import java.util.stream.Collectors;

public class HelpCommand implements Command {

    @Override
    public String execute(String in) {
        return CommandHandler.COMMAND_HANDLER.getCommands().stream().map(command -> "Command: " + Arrays.toString(command.trigger()) + " " + command.description() + "\n").collect(Collectors.joining());
    }

    @Override
    public String[] trigger() {
        return new String[]{"help"};
    }

    @Override
    public String description() {
        return "Shows all commands and their description";
    }
}
