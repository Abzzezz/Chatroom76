/*
 * @author Roman
 * Last modified: 10.01.21, 18:22 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.command.commands;

import net.bplaced.abzzezz.game.Game;
import net.bplaced.abzzezz.game.command.Command;

public class ReturnCommand implements Command {

    @Override
    public String execute(String in) {
        if (!previousScreens.isEmpty())
            Game.GAME.returnTo(previousScreens.pop());
        return "";
    }

    @Override
    public String[] trigger() {
        return new String[]{"return", "re", "r"};
    }

    @Override
    public String description() {
        return "Returns to the previous screen.";
    }
}
