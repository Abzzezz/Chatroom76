/*
 * @author Roman
 * Last modified: 06.01.21, 22:15 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.handler;

import net.bplaced.abzzezz.game.command.Command;
import net.bplaced.abzzezz.game.command.commands.ClearScreenCommand;
import net.bplaced.abzzezz.game.command.commands.ExecuteCommand;
import net.bplaced.abzzezz.game.command.commands.HelpCommand;
import net.bplaced.abzzezz.game.command.commands.ReturnCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandHandler {

    public static final CommandHandler COMMAND_HANDLER = new CommandHandler();
    private final List<Command> commands = new ArrayList<>();

    public CommandHandler() {
        addCommands(
                new ClearScreenCommand(),
                new HelpCommand(),
                new ExecuteCommand(),
                new ReturnCommand()
        );
    }

    public void addCommands(final Command... commands) {
        getCommands().addAll(Arrays.asList(commands));
    }

    public List<Command> getCommands() {
        return commands;
    }
}
