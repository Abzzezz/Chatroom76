/*
 * @author Roman
 * Last modified: 07.01.21, 18:14 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.command.commands;

import net.bplaced.abzzezz.core.ui.component.TextField;
import net.bplaced.abzzezz.game.command.Command;

import java.util.Arrays;

public class InsertCommand implements Command {

    @Override
    public void execute(String in) {
        final String[] arguments = getArguments(in);
        if (arguments.length >= 2) {
            final String title = arguments[0];
            OPEN_GL_CORE_INSTANCE.getScreen().getUiComponents().stream().filter(component -> component instanceof TextField && ((TextField) component).getTitle().equalsIgnoreCase(title)).findAny().ifPresent(component -> {
                final String joined = String.join(" ", Arrays.copyOfRange(arguments, 1, arguments.length));
                ((TextField) component).append(joined);
            });
        }
    }

    @Override
    public String[] trigger() {
        return new String[]{"insert", "ins"};
    }
}
