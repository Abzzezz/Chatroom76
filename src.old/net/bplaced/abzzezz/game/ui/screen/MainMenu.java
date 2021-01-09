/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.game.ui.screen;

import net.bplaced.abzzezz.core.ui.screen.BasicScreen;
import net.bplaced.abzzezz.core.ui.component.Text;
import net.bplaced.abzzezz.core.util.TimeUtil;
import net.bplaced.abzzezz.core.util.render.RenderUtil;
import net.bplaced.abzzezz.game.GameMain;
import net.bplaced.abzzezz.game.command.OptionType;
import net.bplaced.abzzezz.game.ui.component.InputLine;
import net.bplaced.abzzezz.game.ui.component.Option;

public class MainMenu extends BasicScreen {

    private final TimeUtil bounceTime = new TimeUtil(), bounceTime2 = new TimeUtil();

    @Override
    public void init() {
        final int buttonWidth = 100;
        final int xPos = getWidth() / 2 - buttonWidth / 2;

        getOptions().add(new Option(xPos, getHeight() / 2, "Rooms", OptionType.SCREEN, consumer -> OPEN_GL_CORE_INSTANCE.setScreen(new RoomScreen())));
        getOptions().add(new Option(xPos, getHeight() / 2 + 40, "Settings", OptionType.SCREEN, consumer -> OPEN_GL_CORE_INSTANCE.setScreen(new SettingsScreen())));

        getUiComponents().add(new Text(getWidth() / 2, getHeight() / 6, GameMain.INSTANCE.getGameName(), mainColor, true, bigFont));
        getUiComponents().add(new InputLine());

        super.init();
    }

    @Override
    public void draw() {
        if (bounceTime.isTimeOver(1000)) {
            if (bounceTime2.isTimeOver(1600)) {
                bounceTime2.reset();
                bounceTime.reset();
            }
        } else
            RenderUtil.drawQuad(getWidth() / 2 + bigFont.getStringWidth(GameMain.INSTANCE.getGameName()) / 2, getHeight() / 6 + 45, 30, 5, mainColor);
        super.draw();
    }
}
