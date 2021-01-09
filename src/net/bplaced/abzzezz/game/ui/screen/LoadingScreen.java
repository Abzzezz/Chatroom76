/*
 * @author Roman
 * Last modified: 09.01.21, 19:32 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.ui.screen;

import net.bplaced.abzzezz.core.ui.component.components.TextComponent;
import net.bplaced.abzzezz.core.ui.component.components.TextFieldComponent;
import net.bplaced.abzzezz.core.ui.screen.Screen;

public class LoadingScreen extends Screen {

    @Override
    public void initialise() {
        addUIComponent(new TextComponent(gameName, yPos));
        addLineBreak(3);
        addUIComponent(new TextFieldComponent("Input test", yPos, unused -> System.out.println("TextField was entered: " + unused)));
        super.initialise();
    }
}
