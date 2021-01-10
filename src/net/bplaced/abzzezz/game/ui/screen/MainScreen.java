/*
 * @author Roman
 * Last modified: 09.01.21, 19:32 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.ui.screen;

import net.bplaced.abzzezz.core.ui.component.components.OptionComponent;
import net.bplaced.abzzezz.core.ui.component.components.TextComponent;
import net.bplaced.abzzezz.core.ui.screen.Screen;
import net.bplaced.abzzezz.game.Game;

public class MainScreen extends Screen {

    @Override
    public void initialise() {
        addUIComponent(new TextComponent("Welcome to " + gameName, currentY));
        addLineBreak(2);

        addOption(new OptionComponent("Rooms", currentY, action -> Game.GAME.setScreen(new RoomScreen())));

        addOption(new OptionComponent("Settings", currentY, action -> {}));
        super.initialise();
    }
}
