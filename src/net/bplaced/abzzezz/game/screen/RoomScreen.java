package net.bplaced.abzzezz.game.screen;

import net.bplaced.abzzezz.engine.EngineCore;
import net.bplaced.abzzezz.engine.ui.Screen;
import net.bplaced.abzzezz.engine.utils.FontUtil;
import net.bplaced.abzzezz.engine.utils.MouseUtil;
import net.bplaced.abzzezz.engine.utils.Util;
import net.bplaced.abzzezz.game.GameMain;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoomScreen extends Screen {

    protected FontUtil textFont;
    private List<File> dialogs;
    private File selected;

    @Override
    public void init() {
        this.dialogs = new ArrayList<>();
        this.textFont = new FontUtil(Util.textFont, 20);
        for (int i = 0; i < Objects.requireNonNull(EngineCore.getInstance().getMainDir().listFiles()).length; i++) {
            File file = Objects.requireNonNull(EngineCore.getInstance().getMainDir().listFiles())[i];
            if (!file.getName().contains(".")) {
                dialogs.add(file);
            }
        }

        getUiComponents().add(new CustomButton(0, "Play", 10, getHeight() - 30));
        getUiComponents().add(new CustomButton(1, "Import", 200, getHeight() - 30));
        getUiComponents().add(new CustomButton(2, "Delete", 400, getHeight() - 30));
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
    public void drawScreen() {
        int yBuffer = 0;
        for (File dialog : dialogs) {
            textFont.drawString(dialog.getName(), getWidth() / 2 - textFont.getStringWidth(dialog.getName()), getHeight() / 4 + yBuffer, dialog == selected ? Color.RED : Color.WHITE);
            yBuffer += textFont.getHeight() + 5;
        }
        super.drawScreen();
    }

    @Override
    public void mousePressed(int mouseButton) {
        int yBuffer = 0;
        for (File dialog : dialogs) {
            if (MouseUtil.mouseHovered(getWidth() / 2 - textFont.getStringWidth(dialog.getName()), getHeight() / 4 + yBuffer, textFont.getStringWidth(dialog.getName()), textFont.getHeight())) {
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
        GameMain.getInstance().getGlslShaderUtil().draw();
        super.drawShader();
    }
}
