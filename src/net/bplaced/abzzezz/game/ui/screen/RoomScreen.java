package net.bplaced.abzzezz.game.ui.screen;

import net.bplaced.abzzezz.core.Core;
import net.bplaced.abzzezz.core.handler.ShaderHandler;
import net.bplaced.abzzezz.core.ui.BasicScreen;
import net.bplaced.abzzezz.core.ui.components.Button;
import net.bplaced.abzzezz.core.util.io.MouseUtil;
import net.bplaced.abzzezz.core.util.render.ColorUtil;
import net.bplaced.abzzezz.core.util.render.RenderUtil;
import net.bplaced.abzzezz.game.GameMain;
import net.bplaced.abzzezz.game.dialog.Dialog;
import net.bplaced.abzzezz.game.dialog.DialogLoader;
import net.bplaced.abzzezz.game.ui.button.CustomButton;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RoomScreen extends BasicScreen {

    private List<Dialog> dialogs;
    private Dialog selected;

    private Button playButton, deleteButton;

    @Override
    public void init() {
        this.dialogs = GameMain.INSTANCE.getDialogLoader().getDialogs();

        final int buttonWidth = 100, height = 15;
        final int yPos = getHeight() - height * 3;
        getUiComponents().add(playButton = new CustomButton(0, "Play", 50, yPos, buttonWidth, height, false));
        getUiComponents().add(new CustomButton(1, "Import", getWidth() / 2 - 50, yPos, buttonWidth, height));
        getUiComponents().add(deleteButton = new CustomButton(2, "Delete", getWidth() - 150, yPos, buttonWidth, height, false));
        super.init();
    }

    @Override
    public void buttonPressed(float buttonID) {

        if (buttonID == 1) Core.getInstance().setScreen(new ImportDialogScreen(this));
        else if (buttonID == 2) GameMain.INSTANCE.getDialogHandler().deleteDialog(selected);
        else if (buttonID == 0) GameMain.INSTANCE.getDialogHandler().loadDialog(selected);
        super.buttonPressed(buttonID);
    }

    @Override
    public void keyTyped(int keyCode, char keyTyped) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            Core.getInstance().setScreen(new MainMenu());
        }
        super.keyTyped(keyCode, keyTyped);
    }

    @Override
    public void drawScreen() {
        int yBuffer = 0;
        for (final Dialog dialog : dialogs) {
            final String name = dialog.getDialogName();
            final int xPos = getWidth() / 2 - 100;
            final int yPos = getHeight() / 4 + yBuffer;

            RenderUtil.drawQuad(xPos, yPos, 200, textFont.getHeight() * 2 + 5, dialog == selected ? ColorUtil.MAIN_COLOR : ColorUtil.MAIN_COLOR.darker());

            textFont.drawString(name, xPos, yPos, ColorUtil.TEXT_COLOR);
            textFont.drawString(dialog.getCreationDate(), xPos, yPos + textFont.getHeight() + 5, ColorUtil.TEXT_COLOR);

            yBuffer += 50;
        }
        super.drawScreen();
    }

    @Override
    public void mousePressed(int mouseButton) {
        int yBuffer = 0;
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
        super.mousePressed(mouseButton);
    }

    @Override
    public void drawShader() {
        ShaderHandler.SHADER_HANDLER.getBackgroundShader().draw();
        super.drawShader();
    }
}
