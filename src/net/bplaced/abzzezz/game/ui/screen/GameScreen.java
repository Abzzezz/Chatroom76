package net.bplaced.abzzezz.game.ui.screen;

import net.bplaced.abzzezz.core.Core;
import net.bplaced.abzzezz.core.ui.BasicScreen;
import net.bplaced.abzzezz.core.util.io.MouseUtil;
import net.bplaced.abzzezz.core.util.render.ColorUtil;
import net.bplaced.abzzezz.core.util.render.FontUtil;
import net.bplaced.abzzezz.core.util.render.RenderUtil;
import net.bplaced.abzzezz.core.util.render.ScissorUtil;
import net.bplaced.abzzezz.game.GameMain;
import net.bplaced.abzzezz.game.dialog.DialogLine;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class GameScreen extends BasicScreen {

    private FontUtil textFont;
    private boolean paused;
    private int scrollY;

    @Override
    public void init() {
        this.textFont = new FontUtil(ColorUtil.TEXT_FONT, 20);
        GameMain.INSTANCE.getDialogHandler().getNextDialog();
        super.init();
    }

    @Override
    public void drawScreen() {
        int yBuffer = 0;
        ScissorUtil.enableScissor();
        ScissorUtil.scissor(0, 0, getWidth(), getHeight());

        for (final DialogLine dialogLine : GameMain.INSTANCE.getDialogHandler().getDisplayDialog()) {
            textFont.drawString(dialogLine.getDialog(), 0, 20 + yBuffer + scrollY, dialogLine.getTextColor());
            yBuffer += textFont.getHeight() + 5;
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

        if (paused) {
            textFont.drawString("Resume", getWidth() / 2 - textFont.getStringWidth("Resume") / 2, getHeight() / 4, Color.decode("#836E81"));
            textFont.drawString("Back to menu", getWidth() / 2 - textFont.getStringWidth("Back to menu") / 2, getHeight() / 4 + textFont.getHeight(), Color.decode("#836E81"));
        }
        super.drawScreen();
    }

    @Override
    public void drawShader() {
        GameMain.INSTANCE.getShaderHandler().getBackgroundShader().render();
        GameMain.INSTANCE.getShaderHandler().getTextureShader().render();

        super.drawShader();
    }

    @Override
    public void mousePressed(int mouseButton) {
        if (paused) {
            if (MouseUtil.mouseHovered(getWidth() / 2 - textFont.getStringWidth("Resume") / 2, getHeight() / 4, textFont.getStringWidth("Resume"), textFont.getHeight())) {
                paused = !paused;
            } else if (MouseUtil.mouseHovered(getWidth() / 2 - textFont.getStringWidth("Back to menu") / 2, getHeight() / 4 + textFont.getHeight(), textFont.getStringWidth("Back to menu"), textFont.getHeight())) {
                GameMain.INSTANCE.getDialogHandler().savePreviousDialog();
                Core.getInstance().setScreen(new RoomScreen());
            }
        } else
            GameMain.INSTANCE.getDialogHandler().getNextDialog();

        super.mousePressed(mouseButton);
    }

    @Override
    public void keyTyped(int keyCode, char keyTyped) {
        if (keyCode == Keyboard.KEY_ESCAPE) paused = !paused;
        GameMain.INSTANCE.getDialogHandler().selectOption(keyTyped);
        super.keyTyped(keyCode, keyTyped);
    }
}
