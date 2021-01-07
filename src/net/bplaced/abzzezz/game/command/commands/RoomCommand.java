/*
 * @author Roman
 * Last modified: 07.01.21, 18:46 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.command.commands;

import net.bplaced.abzzezz.game.GameMain;
import net.bplaced.abzzezz.game.command.Command;
import org.apache.commons.lang3.StringUtils;


public class RoomCommand implements Command {

    @Override
    public void execute(String in) {
        final String[] arguments = getArguments(in);
        if (arguments.length >= 2) {
            final String arg0 = arguments[0].toLowerCase();
            final String arg1 = arguments[1];
            switch (arg0) {
                case "delete":
                case "del":
                    if (StringUtils.isNumeric(arg1))
                        GameMain.INSTANCE.getDialogHandler().deleteDialog(Integer.parseInt(arg1));
                    break;
                case "join":
                    if (StringUtils.isNumeric(arg1)) {
                        GameMain.INSTANCE.getDialogHandler().loadDialog(Integer.parseInt(arg1));
                    }
                    break;
            }
        }
    }

    @Override
    public String[] trigger() {
        return new String[]{"room"};
    }
}
