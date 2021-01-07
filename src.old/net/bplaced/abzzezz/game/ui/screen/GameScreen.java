/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.game.ui.screen;

import net.bplaced.abzzezz.core.handler.ShaderHandler;
import net.bplaced.abzzezz.core.ui.screen.BasicScreen;
import net.bplaced.abzzezz.core.util.render.RenderUtil;
import net.bplaced.abzzezz.core.util.render.ScissorUtil;
import net.bplaced.abzzezz.game.GameMain;
import net.bplaced.abzzezz.game.dialog.DialogLine;
import net.bplaced.abzzezz.game.ui.elements.PauseMenuElement;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;


public class GameScreen extends BasicScreen {


    private final int halfWidth = getWidth() / 2;
    private final int quadHeight = getHeight() / 4;


    private final List<PauseMenuElement> pauseMenuElements = new ArrayList<>();


    private int scrollY;
    private boolean paused;

    @Override
    public void init() {
        final String[] pauseMenu = {"Resume", "Back to menu"};
        final Consumer<Void>[] actions = new Consumer[]{o -> paused = !paused, o -> GameMain.INSTANCE.getDialogHandler().savePreviousDialog()};


        int yStack = 0;
        for (int i = 0; i < pauseMenu.length; i++) {
            final PauseMenuElement pauseMenuElement = new PauseMenuElement(pauseMenu[i], halfWidth - textFont.getStringWidth(pauseMenu[i]) / 2, quadHeight + yStack, actions[i]);

            pauseMenuElements.add(pauseMenuElement);
            yStack += pauseMenuElement.getHeight();
        }

        GameMain.INSTANCE.getDialogHandler().getNextDialog();
        super.init();
    }

    @Override
    public void draw() {
        int yBuffer = 0;
        ScissorUtil.enableScissor();
        ScissorUtil.scissor(0, 0, getWidth(), getHeight());

        for (final DialogLine dialogLine : GameMain.INSTANCE.getDialogHandler().getDisplayDialog()) {
            textFont.drawString(dialogLine.getDialog(), 0, 20 + yBuffer + scrollY, dialogLine.getTextColor());
            yBuffer += textFont.getHeight(dialogLine.getDialog()) + 5;
        }

        if (GameMain.INSTANCE.getDialogHandler().isPending())
            RenderUtil.drawQuad(10, 50 + yBuffer + scrollY, 20, 5, Color.WHITE);

        if ((yBuffer >= getHeight() - textFont.getHeight()) && Mouse.hasWheel()) {
            int wheel = Mouse.getDWheel() / 120;
            if (wheel < 0) {
                if (!(scrollY > yBuffer) && scrollY < 0)
                    scrollY += textFont.getHeight();
            } else if (wheel > 0) {
                if (!(scrollY < -yBuffer))
                    scrollY -= textFont.getHeight();
            }
        }
        ScissorUtil.disableScissor();

        if (paused) pauseMenuElements.forEach(PauseMenuElement::drawElement);

        super.draw();
    }

    @Override
    public void drawShader() {
        ShaderHandler.SHADER_HANDLER.getTextureShader().draw();
        super.drawShader();
    }

    @Override
    public void keyTyped(int keyCode, char keyTyped) {
        GameMain.INSTANCE.getDialogHandler().selectOption(keyTyped);

        if (keyCode == Keyboard.KEY_ESCAPE) paused = !paused;
        else if (keyCode == Keyboard.KEY_SPACE)
            GameMain.INSTANCE.getDialogHandler().getNextDialog();

        super.keyTyped(keyCode, keyTyped);
    }

}
