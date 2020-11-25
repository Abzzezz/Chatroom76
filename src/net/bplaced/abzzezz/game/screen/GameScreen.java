package net.bplaced.abzzezz.game.screen;

import net.bplaced.abzzezz.engine.EngineCore;
import net.bplaced.abzzezz.engine.ui.Screen;
import net.bplaced.abzzezz.engine.utils.*;
import net.bplaced.abzzezz.game.GameMain;
import net.bplaced.abzzezz.game.dialog.DialogLine;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class GameScreen extends Screen {

    private FontUtil textFont;
    private GameMain mainInst;
    private boolean paused;
    private int scrollY;

    @Override
    public void init() {
        this.textFont = new FontUtil(Util.textFont, 20);
        this.mainInst = GameMain.getInstance();
        GameMain.getInstance().getDialogHandler().getNextDialog();
        super.init();
    }

    @Override
    public void drawScreen() {
        int yBuffer = 0;
        ScissorUtil.enableScissor();
        ScissorUtil.scissor(0, 0, getWidth(), getHeight());

        for (final DialogLine dialogLine : mainInst.getDialogHandler().getDisplayDialog()) {
            textFont.drawString(dialogLine.getDialog(), 0, 20 + yBuffer + scrollY, dialogLine.getTextColor());
            yBuffer += textFont.getHeight() + 5;
        }

        if (mainInst.getDialogHandler().isPending())
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

        /**
         * Paused
         */
        if (paused) {
            textFont.drawString("Resume", getWidth() / 2 - textFont.getStringWidth("Resume") / 2, getHeight() / 4, Color.decode("#836E81"));
            textFont.drawString("Back to menu", getWidth() / 2 - textFont.getStringWidth("Back to menu") / 2, getHeight() / 4 + textFont.getHeight(), Color.decode("#836E81"));
        }
        super.drawScreen();
    }

    @Override
    public void drawShader() {
        GameMain.getInstance().getShader().draw();
        super.drawShader();
    }

    @Override
    public void mousePressed(int mouseButton) {
        if (paused) {
            if (MouseUtil.mouseHovered(getWidth() / 2 - textFont.getStringWidth("Resume") / 2, getHeight() / 4, textFont.getStringWidth("Resume"), textFont.getHeight())) {
                paused = !paused;
            } else if (MouseUtil.mouseHovered(getWidth() / 2 - textFont.getStringWidth("Back to menu") / 2, getHeight() / 4 + textFont.getHeight(), textFont.getStringWidth("Back to menu"), textFont.getHeight())) {
                mainInst.getDialogHandler().savePreviousDialog();
                EngineCore.getInstance().setScreen(new RoomScreen());
            }
        } else
            mainInst.getDialogHandler().getNextDialog();

        super.mousePressed(mouseButton);
    }

    @Override
    public void keyTyped(int keyCode, char keyTyped) {
        if (keyCode == Keyboard.KEY_ESCAPE) paused = !paused;
        mainInst.getDialogHandler().selectOption(keyTyped);
        super.keyTyped(keyCode, keyTyped);
    }
}
