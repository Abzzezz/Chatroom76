package net.bplaced.abzzezz.game.screen;

import net.bplaced.abzzezz.engine.EngineCore;
import net.bplaced.abzzezz.engine.ui.Screen;
import net.bplaced.abzzezz.engine.utils.FontUtil;
import net.bplaced.abzzezz.engine.utils.ColorUtil;
import net.bplaced.abzzezz.engine.utils.RenderUtil;
import net.bplaced.abzzezz.engine.utils.TimeUtil;
import net.bplaced.abzzezz.game.GameMain;

import javax.swing.plaf.ColorUIResource;
import java.util.concurrent.TimeUnit;

public class MainMenu extends Screen {


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
            EngineCore.getInstance().setScreen(new RoomScreen());
        } else if (buttonID == 1) {
            EngineCore.getInstance().setScreen(new SettingsScreen());
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
        GameMain.getInstance().getShader().draw();
        super.drawShader();
    }
}
