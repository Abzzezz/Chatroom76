/*
 * @author Roman
 * Last modified: 07.01.21, 17:56 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.command.commands;

import net.bplaced.abzzezz.game.command.Command;
import net.bplaced.abzzezz.game.command.OptionType;

public class ExecuteCommand implements Command {

    @Override
    public void execute(String in) {
        final String[] arguments = getArguments(in);
        if (arguments.length == 1) {
            final String optionTitle = arguments[0];
            OPEN_GL_CORE_INSTANCE.getScreen().getOptions().stream().filter(option -> option.getTitle().equalsIgnoreCase(optionTitle) && option.getOptionType() == OptionType.EXECUTABLE).findAny().ifPresent(option -> option.getConsumer().accept(null));
        }
    }

    @Override
    public String[] trigger() {
        return new String[]{"exec", "e", "exe", "execute"};
    }
}
