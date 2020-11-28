package net.bplaced.abzzezz.game.screen;

import net.bplaced.abzzezz.engine.EngineCore;
import net.bplaced.abzzezz.engine.ui.Screen;
import net.bplaced.abzzezz.engine.utils.FontUtil;
import net.bplaced.abzzezz.engine.utils.ColorUtil;
import net.bplaced.abzzezz.game.GameMain;

public class MainMenu extends Screen {

    @Override
    public void init() {

        final int buttonWidth = 100, height = 15;
        final int xPos = getWidth() / 2 - buttonWidth / 2;

        getUiComponents().add(new CustomButton(0, "Rooms", xPos, getHeight() / 4, buttonWidth, height));
        getUiComponents().add(new CustomButton(1, "Settings", xPos, getHeight() / 4 + 40, buttonWidth, height));
        super.init();
    }

    @Override
    public void buttonPressed(float buttonID) {
        if (buttonID == 0) {
            EngineCore.getInstance().setScreen(new RoomScreen());
        } else if (buttonID == 1) {
            EngineCore.getInstance().setScreen(new SettingsScreen());
        }
        super.buttonPressed(buttonID);
    }

    @Override
    public void drawScreen() {
        drawCenteredMenuString("Chatroom76", getWidth() / 2, getHeight() / 6);
        super.drawScreen();
    }

    @Override
    public void drawShader() {
        GameMain.getInstance().getShader().draw();
        super.drawShader();
    }
}
