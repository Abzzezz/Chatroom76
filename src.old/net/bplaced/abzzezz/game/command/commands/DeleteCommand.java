/*
 * @author Roman
 * Last modified: 07.01.21, 18:21 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.command.commands;

import net.bplaced.abzzezz.core.ui.component.TextField;
import net.bplaced.abzzezz.game.command.Command;
import org.apache.commons.lang3.StringUtils;

public class DeleteCommand implements Command {

    @Override
    public void execute(String in) {
        final String[] arguments = getArguments(in);
        if (arguments.length == 2 && StringUtils.isNumeric(arguments[1])) {
            final String title = arguments[0];
            OPEN_GL_CORE_INSTANCE.getScreen().getUiComponents().stream().filter(component -> component instanceof TextField && ((TextField) component).getTitle().equalsIgnoreCase(title)).findAny().ifPresent(option -> {
                ((TextField) option).delete(Integer.parseInt(arguments[1]));
            });
        }
    }

    @Override
    public String[] trigger() {
        return new String[]{"del", "delete"};
    }
}
