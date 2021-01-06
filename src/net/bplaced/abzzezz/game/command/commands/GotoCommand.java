/*
 * @author Roman
 * Last modified: 06.01.21, 22:30 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.command.commands;

import net.bplaced.abzzezz.game.command.Command;


public class GotoCommand implements Command {

    @Override
    public void execute(String in) {
        final String[] arguments = getArguments(in);
        if (arguments.length == 1) {
            final String optionTitle = arguments[0];
            OPEN_GL_CORE_INSTANCE.getScreen().getOptions().stream().filter(option -> option.getTitle().equalsIgnoreCase(optionTitle)).findAny().ifPresent(option -> {
                option.getConsumer().accept(null);
            });
        }
    }

    @Override
    public String[] trigger() {
        return new String[]{"goto", "go"};
    }
}
