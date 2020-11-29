package net.bplaced.abzzezz.game.screen;

import net.bplaced.abzzezz.core.Core;
import net.bplaced.abzzezz.core.ui.BasicScreen;
import net.bplaced.abzzezz.core.util.render.ColorUtil;
import net.bplaced.abzzezz.core.util.render.RenderUtil;
import net.bplaced.abzzezz.core.util.TimeUtil;
import net.bplaced.abzzezz.game.GameMain;

public class MainMenu extends BasicScreen {


    private final TimeUtil bounceTime = new TimeUtil(), bounceTime2 = new TimeUtil();

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
            Core.getInstance().setScreen(new RoomScreen());
        } else if (buttonID == 1) {
            Core.getInstance().setScreen(new SettingsScreen());
        }
        super.buttonPressed(buttonID);
    }

    @Override
    public void drawScreen() {
        drawCenteredMenuString("Chatroom76", getWidth() / 2, getHeight() / 6);


        if(bounceTime.isTimeOver(1000)) {
            if(bounceTime2.isTimeOver(1600)) {
                bounceTime2.reset();
                bounceTime.reset();
            }
        } else {
            RenderUtil.drawQuad(getWidth() / 2 + bigFont.getStringWidth("Chatroom76") / 2, getHeight() / 6 + 45, 30, 5, ColorUtil.MAIN_COLOR);
        }
        super.drawScreen();
    }

    @Override
    public void drawShader() {
        GameMain.INSTANCE.getShader().draw();
        super.drawShader();
    }
}
