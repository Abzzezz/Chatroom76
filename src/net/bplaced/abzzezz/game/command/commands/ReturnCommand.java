/*
 * @author Roman
 * Last modified: 06.01.21, 22:15 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.command.commands;

import net.bplaced.abzzezz.game.command.Command;

/**
 * Command to return to the previous screen
 */
public class ReturnCommand implements Command {

    @Override
    public void execute(final String in) {
        if (!screens.isEmpty())
            OPEN_GL_CORE_INSTANCE.returnTo(screens.pop());
    }

    @Override
    public String[] trigger() {
        return new String[]{"return", "re", "r"};
    }

}
