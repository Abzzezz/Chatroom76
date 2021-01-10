/*
 * @author Roman
 * Last modified: 10.01.21, 20:52 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.ui.screen;

import net.bplaced.abzzezz.core.ui.component.components.ProgressbarComponent;
import net.bplaced.abzzezz.core.ui.component.components.TextFieldComponent;
import net.bplaced.abzzezz.core.ui.screen.Screen;
import net.bplaced.abzzezz.game.Game;

public class ImportScreen extends Screen {

    @Override
    public void initialise() {
        final ProgressbarComponent downloadProgress;

        addUIComponent(downloadProgress = new ProgressbarComponent("Download progress", currentY, 100, 0, false));

        addUIComponent(new TextFieldComponent("URL", currentY, s -> Game.GAME.getDialogHandler().download(s, downloadProgress::setMax, downloadProgress::incrementCurrent, System.out::println)));
        super.initialise();
    }
}
