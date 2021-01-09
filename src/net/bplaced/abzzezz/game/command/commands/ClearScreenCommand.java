/*
 * @author Roman
 * Last modified: 09.01.21, 20:17 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.command.commands;

import net.bplaced.abzzezz.game.Game;
import net.bplaced.abzzezz.game.command.Command;

public class ClearScreenCommand implements Command {

    @Override
    public String execute(String in) {
        Game.GAME.getScreen().clear();
        return "";
    }

    @Override
    public String[] trigger() {
        return new String[] {"clear", "cls"};
    }

    @Override
    public String description() {
        return "Clears the screen";
    }
}
