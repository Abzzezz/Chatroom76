package net.bplaced.abzzezz.game.ui.screen;

import net.bplaced.abzzezz.core.Core;
import net.bplaced.abzzezz.core.ui.BasicScreen;
import net.bplaced.abzzezz.core.ui.components.KeyButton;
import net.bplaced.abzzezz.core.ui.components.Text;
import net.bplaced.abzzezz.core.util.TimeUtil;
import net.bplaced.abzzezz.core.util.render.ColorUtil;
import net.bplaced.abzzezz.core.util.render.RenderUtil;
import net.bplaced.abzzezz.game.GameMain;

public class MainMenu extends BasicScreen {

    private final TimeUtil bounceTime = new TimeUtil(), bounceTime2 = new TimeUtil();

    @Override
    public void init() {
        final int buttonWidth = 100, height = 15;
        final int xPos = getWidth() / 2 - buttonWidth / 2;

        // getUiComponents().add(new CustomButton(0, "Rooms", xPos, getHeight() / 2, buttonWidth, height));
        //  getUiComponents().add(new CustomButton(1, "Settings", xPos, getHeight() / 2 + 40, buttonWidth, height));

        final KeyButton buttonOne = new KeyButton(0, xPos, getHeight() / 2, buttonWidth, height, "Rooms", true);
        getKeyButtons().add(buttonOne);
        getKeyButtons().add(new KeyButton(1, xPos, getHeight() / 2 + 40, buttonWidth, height, "Settings"));

        getUiComponents().add(new Text(getWidth() / 2, getHeight() / 6, GameMain.INSTANCE.getGameName(), mainColor, true, bigFont));
        super.init();
    }

    @Override
    public void keyButtonEntered(float id) {
        System.out.println(id);
        super.keyButtonEntered(id);
    }

    @Override
    public void buttonPressed(float buttonID) {
        if (buttonID == 0)
            Core.getInstance().setScreen(new RoomScreen());
        else if (buttonID == 1)
            Core.getInstance().setScreen(new SettingsScreen());
        super.buttonPressed(buttonID);
    }

    @Override
    public void draw() {
        if (bounceTime.isTimeOver(1000)) {
            if (bounceTime2.isTimeOver(1600)) {
                bounceTime2.reset();
                bounceTime.reset();
            }
        } else
            RenderUtil.drawQuad(getWidth() / 2 + bigFont.getStringWidth(GameMain.INSTANCE.getGameName()) / 2, getHeight() / 6 + 45, 30, 5, ColorUtil.MAIN_COLOR);
        super.draw();
    }
}
