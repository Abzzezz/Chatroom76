/*
 * @author Roman
 * Last modified: 10.01.21, 21:44 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.command.commands;

import net.bplaced.abzzezz.game.Game;
import net.bplaced.abzzezz.game.command.Command;
import org.apache.commons.lang3.StringUtils;

public class RoomCommand implements Command {

    @Override
    public String execute(String in) {
        final String[] arguments = getArguments(in);
        if (arguments.length >= 2) {
            final String arg0 = arguments[0].toLowerCase();
            final String arg1 = arguments[1];
            switch (arg0) {
                case "delete":
                case "del":
                    if (StringUtils.isNumeric(arg1))
                        Game.GAME.getDialogHandler().deleteDialog(Integer.parseInt(arg1));
                    break;
                case "join":
                    if (StringUtils.isNumeric(arg1)) {
                        Game.GAME.getDialogHandler().loadDialog(Integer.parseInt(arg1));
                    }
                    break;
            }
        }
        return null;
    }

    @Override
    public String[] trigger() {
        return new String[]{"room"};
    }

    @Override
    public String description() {
        return "Loads the room. Only works when in the room screen. Second argument is the index of the dialog, starting at 0.";
    }
}
