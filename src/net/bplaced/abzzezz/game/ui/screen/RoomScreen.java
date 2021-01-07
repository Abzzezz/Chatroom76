/*
 * @author Roman
 * Last modified: 03.01.21, 21:52 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.game.ui.screen;

import net.bplaced.abzzezz.core.ui.screen.BasicScreen;
import net.bplaced.abzzezz.core.ui.components.Text;
import net.bplaced.abzzezz.core.util.render.RenderUtil;
import net.bplaced.abzzezz.game.GameMain;
import net.bplaced.abzzezz.game.command.OptionType;
import net.bplaced.abzzezz.game.dialog.Dialog;
import net.bplaced.abzzezz.game.ui.component.InputLine;
import net.bplaced.abzzezz.game.ui.component.Option;

import java.util.List;

public class RoomScreen extends BasicScreen {

    private List<Dialog> dialogs;
    private Dialog selected;

    @Override
    public void init() {
        this.dialogs = GameMain.INSTANCE.getDialogLoader().getDialogs();

        final int height = 15;
        final int yPos = getHeight() - height * 3;

        getOptions().add(new Option(getWidth() / 2 - 50, yPos, "Import", OptionType.SCREEN, con -> OPEN_GL_CORE_INSTANCE.setScreen(new ImportDialogScreen())));


        getUiComponents().add(new Text(getWidth() / 2, getHeight() / 6, "Rooms", mainColor, true, bigFont));

        if (dialogs.size() > 0) selected = dialogs.get(0);

        getUiComponents().add(new InputLine());
        super.init();
    }

    @Override
    public void draw() {
        int yBuffer = 0;
        for (final Dialog dialog : dialogs) {
            final String name = dialog.getDialogName();
            final int xPos = getWidth() / 2 - 100;
            final int yPos = getHeight() / 4 + yBuffer;

            RenderUtil.drawQuad(xPos, yPos, 200, textFont.getHeight() * 2 + 5, dialog == selected ? mainColor : mainColor.darker());

            textFont.drawString(name, xPos, yPos,textColor);
            textFont.drawString(dialog.getCreationDate(), xPos, yPos + textFont.getHeight() + 5, textColor);
            if (dialog == selected)
                RenderUtil.drawTopTriangle(xPos, yPos, 190, 10);

            yBuffer += 50;
        }
        super.draw();
    }
}
