package net.bplaced.abzzezz.game.screen;

import net.bplaced.abzzezz.engine.EngineCore;
import net.bplaced.abzzezz.engine.ui.Screen;
import net.bplaced.abzzezz.engine.utils.FontUtil;
import net.bplaced.abzzezz.engine.utils.Util;
import net.bplaced.abzzezz.game.GameMain;

public class MainMenu extends Screen {

    protected FontUtil textFont;

    @Override
    public void init() {
        textFont = new FontUtil(Util.textFont, 20);
        getUiComponents().add(new CustomButton(-1, "Rooms", getWidth() / 2 - textFont.getStringWidth("Rooms") / 2, getHeight() / 4));
        getUiComponents().add(new CustomButton(1, "Settings", getWidth() / 2 - textFont.getStringWidth("Settings") / 2, getHeight() / 4 + 40));

        super.init();
    }

    @Override
    public void buttonPressed(float buttonID) {
        if (buttonID == -1) {
            EngineCore.getInstance().setScreen(new RoomScreen());
        } else if (buttonID == 1) {
            EngineCore.getInstance().setScreen(new SettingsScreen());
        }
        super.buttonPressed(buttonID);
    }

    @Override
    public void drawScreen() {
        super.drawScreen();
    }

    @Override
    public void drawShader() {
        GameMain.getInstance().getGlslShaderUtil().draw();
        super.drawShader();
    }
}
