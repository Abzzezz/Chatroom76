package net.bplaced.abzzezz.game.screen;

import net.bplaced.abzzezz.engine.EngineCore;
import net.bplaced.abzzezz.engine.ui.Screen;
import net.bplaced.abzzezz.engine.utils.FontUtil;
import net.bplaced.abzzezz.engine.utils.MouseUtil;
import net.bplaced.abzzezz.engine.utils.RenderUtil;
import net.bplaced.abzzezz.engine.utils.Util;
import net.bplaced.abzzezz.game.GameMain;
import net.bplaced.abzzezz.game.dialog.Dialog;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoomScreen extends Screen {

    protected FontUtil textFont;
    private List<Dialog> dialogs;
    private Dialog selected;

    @Override
    public void init() {
        this.dialogs = new ArrayList<>();
        this.textFont = new FontUtil(Util.textFont, 20);
        for (int i = 0; i < Objects.requireNonNull(EngineCore.getInstance().getMainDir().listFiles()).length; i++) {
            File file = Objects.requireNonNull(EngineCore.getInstance().getMainDir().listFiles())[i];
            if (!file.getName().contains(".")) {
                dialogs.add(new Dialog(file).loadMetaData());
            }
        }
        final int buttonWidth = 100, height = 15;
        final int yPos = getHeight() - height*3;
        getUiComponents().add(new CustomButton(0, "Play", 50, yPos, buttonWidth, height));
        getUiComponents().add(new CustomButton(1, "Import", getWidth() / 2 - 50, yPos, buttonWidth, height));
        getUiComponents().add(new CustomButton(2, "Delete", getWidth() - 150, yPos, buttonWidth, height));
        super.init();
    }

    @Override
    public void buttonPressed(float buttonID) {

        if (buttonID == 1) GameMain.getInstance().getDialogHandler().downloadDialog();
        else if (buttonID == 2) GameMain.getInstance().getDialogHandler().deleteDialog(selected);
        else if (buttonID == 0) GameMain.getInstance().getDialogHandler().loadDialog(selected);
        super.buttonPressed(buttonID);
    }

    @Override
    public void keyTyped(int keyCode, char keyTyped) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            EngineCore.getInstance().setScreen(new MainMenu());
        }
        super.keyTyped(keyCode, keyTyped);
    }

    @Override
    public void drawScreen() {
        int yBuffer = 0;
        for (final Dialog dialog : dialogs) {
            final String name = dialog.getDialogName();
            final int xPos = getWidth() / 2 - textFont.getStringWidth(name) / 2;
            final int yPos = getHeight() / 4 + yBuffer;

            RenderUtil.drawQuad(xPos, yPos, 100, textFont.getHeight() + 5, new Color(0,0,0, 200));
            textFont.drawString(name, xPos, yPos, dialog == selected ? Color.RED : Color.WHITE);
            yBuffer += textFont.getHeight() + 5;
        }
        super.drawScreen();
    }

    @Override
    public void mousePressed(int mouseButton) {
        int yBuffer = 0;
        for (Dialog dialog : dialogs) {
            if (MouseUtil.mouseHovered(getWidth() / 2 - textFont.getStringWidth(dialog.getDialogName()) / 2, getHeight() / 4 + yBuffer, textFont.getStringWidth(dialog.getDialogName()), textFont.getHeight())) {
                if (selected == dialog) selected = null;
                else
                    selected = dialog;
            }
            yBuffer += textFont.getHeight() + 5;
        }
        super.mousePressed(mouseButton);
    }

    @Override
    public void drawShader() {
        GameMain.getInstance().getShader().draw();
        super.drawShader();
    }
}
