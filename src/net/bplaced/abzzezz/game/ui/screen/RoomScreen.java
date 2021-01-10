/*
 * @author Roman
 * Last modified: 10.01.21, 18:30 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.ui.screen;

import net.bplaced.abzzezz.core.ui.component.components.OptionComponent;
import net.bplaced.abzzezz.core.ui.component.components.TextComponent;
import net.bplaced.abzzezz.core.ui.screen.Screen;
import net.bplaced.abzzezz.game.Game;
import net.bplaced.abzzezz.game.dialog.DialogLoader;

public class RoomScreen extends Screen {

    @Override
    public void initialise() {
        addUIComponent(new TextComponent("List of available dialogs: ", currentY));
        DialogLoader.DIALOG_LOADER.getDialogs().forEach(dialog -> addUIComponent(new TextComponent("\n" + dialog.getDialogName() + "\nCreated: " + dialog.getCreationDate(), currentY)));
        addLineBreak(2);
        addOption(new OptionComponent("Import", currentY, unused -> Game.GAME.setScreen(new ImportScreen())));
        super.initialise();
    }


}
