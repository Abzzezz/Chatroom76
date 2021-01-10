/*
 * @author Roman
 * Last modified: 09.01.21, 19:32 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.ui.screen;

import net.bplaced.abzzezz.core.ui.component.components.TextComponent;
import net.bplaced.abzzezz.core.ui.screen.Screen;

public class LoadingScreen extends Screen {

    @Override
    public void initialise() {
        addUIComponent(new TextComponent(gameName, currentY));
        addLineBreak(3);

        super.initialise();
    }

    @Override
    public void draw() {
        super.draw();
    }
}
