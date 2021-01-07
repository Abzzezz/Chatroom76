/*
 * @author Roman
 * Last modified: 03.01.21, 21:52 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.game.ui.screen;

import net.bplaced.abzzezz.core.Core;
import net.bplaced.abzzezz.core.ui.BasicScreen;
import net.bplaced.abzzezz.core.ui.components.Text;
import net.bplaced.abzzezz.core.util.render.ColorUtil;
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
    public void buttonPressed(float buttonID) {

        if (buttonID == 1) Core.getInstance().setScreen(new ImportDialogScreen());
        else if (buttonID == 2) GameMain.INSTANCE.getDialogHandler().deleteDialog(selected);
        else if (buttonID == 0) GameMain.INSTANCE.getDialogHandler().loadDialog(selected);
        super.buttonPressed(buttonID);
    }

    /*
    @Override
    public void keyTyped(int keyCode, char keyTyped) {

        if (keyCode == Keyboard.KEY_ESCAPE)
            Core.getInstance().setScreen(new MainMenu());
        else if (keyCode == Keyboard.KEY_RETURN && selected != null)
            GameMain.INSTANCE.getDialogHandler().loadDialog(selected);
        else if (keyCode == Keyboard.KEY_UP) {
            final int index = dialogs.indexOf(selected);
            int previousIndex;
            if (index == -1) return;
            else if (index - 1 == -1) {
                previousIndex = dialogs.size() - 1;
            } else
                previousIndex = index - 1;

            if (previousIndex == -1) return;
            selected = dialogs.get(previousIndex);
        } else if (keyCode == Keyboard.KEY_DOWN) {
            final int index = dialogs.indexOf(selected);
            int nextIndex;
            if (index == -1) return;
            else if (index + 1 > dialogs.size() - 1) {
                nextIndex = 0;
            } else
                nextIndex = index + 1;
            selected = dialogs.get(nextIndex);
        }
        super.keyTyped(keyCode, keyTyped);


    }

     */

    @Override
    public void draw() {
        int yBuffer = 0;
        for (final Dialog dialog : dialogs) {
            final String name = dialog.getDialogName();
            final int xPos = getWidth() / 2 - 100;
            final int yPos = getHeight() / 4 + yBuffer;

            RenderUtil.drawQuad(xPos, yPos, 200, textFont.getHeight() * 2 + 5, dialog == selected ? ColorUtil.MAIN_COLOR : ColorUtil.MAIN_COLOR.darker());

            textFont.drawString(name, xPos, yPos, ColorUtil.TEXT_COLOR);
            textFont.drawString(dialog.getCreationDate(), xPos, yPos + textFont.getHeight() + 5, ColorUtil.TEXT_COLOR);
            if (dialog == selected)
                RenderUtil.drawTopTriangle(xPos, yPos, 190, 10);

            yBuffer += 50;
        }
        super.draw();
    }

    @Override
    public void mousePressed(int mouseButton) {
        int yBuffer = 0;
        /*
        for (Dialog dialog : dialogs) {
            if (MouseUtil.mouseHovered(getWidth() / 2 - 100, getHeight() / 4 + yBuffer, 200, textFont.getHeight() * 2 + 5)) {
                if (selected == dialog) {
                    deleteButton.setEnabled(false);
                    playButton.setEnabled(false);
                    selected = null;
                } else {
                    selected = dialog;
                    deleteButton.setEnabled(true);
                    playButton.setEnabled(true);
                }
            }
            yBuffer += textFont.getHeight() + 5;
        }

         */
        super.mousePressed(mouseButton);
    }

}
