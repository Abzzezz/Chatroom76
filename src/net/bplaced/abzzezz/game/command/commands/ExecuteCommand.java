/*
 * @author Roman
 * Last modified: 10.01.21, 18:01 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.command.commands;

import net.bplaced.abzzezz.core.ui.component.components.OptionComponent;
import net.bplaced.abzzezz.game.command.Command;

public class ExecuteCommand implements Command {

    @Override
    public String execute(String in) {
        final String[] arguments = getArguments(in);
        if (arguments.length == 1) {
            final String optionTitle = arguments[0];
            activeOptions.stream().filter(option -> option.getOptionName().equalsIgnoreCase(optionTitle)).findFirst().ifPresent(OptionComponent::trigger);
            return "Executing: " + arguments[0];
        } else
            return "Not enough arguments specified.";
    }

    @Override
    public String[] trigger() {
        return new String[]{"exec", "exe", "execute"};
    }

    @Override
    public String description() {
        return "Executes a available option on screen.";
    }
}
