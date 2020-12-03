package net.bplaced.abzzezz.game.ui.button;

import net.bplaced.abzzezz.core.ui.components.Button;
import net.bplaced.abzzezz.core.util.io.MouseUtil;
import net.bplaced.abzzezz.core.util.render.ColorUtil;
import net.bplaced.abzzezz.core.util.render.RenderUtil;

public class CustomButton extends Button {

    private final int d = 10;

    /**
     * Simple button. Buttons can be added just use this as a parent
     *
     * @param id
     * @param text
     * @param xPos
     * @param yPos
     */
    public CustomButton(float id, String text, float xPos, float yPos) {
        super(id, text, xPos, yPos);
    }

    public CustomButton(float id, String text, float xPos, float yPos, int width, int height) {
        super(id, text, xPos, yPos, width, height);
    }

    public CustomButton(float id, String text, float xPos, float yPos, int width, int height, boolean enabled) {
        super(id, text, xPos, yPos, width, height, enabled);
    }

    public CustomButton(float id, String text, float xPos, float yPos, boolean enabled) {
        super(id, text, xPos, yPos, enabled);
    }

    @Override
    public void drawComponent() {
        final int quadHeight = getDimensions()[1] + d * 2;
        RenderUtil.drawQuad(getXPos() - d, getYPos(), getDimensions()[0] + d * 2, quadHeight, ColorUtil.MAIN_COLOR);
        textFont.drawString(getText(), getXPos() + getDimensions()[0] / 2 - textFont.getStringWidth(getText()) / 2, getYPos() + d / 2, isEnabled() ? ColorUtil.TEXT_COLOR : ColorUtil.MAIN_COLOR);
    }

    @Override
    public boolean buttonHovered() {
        return MouseUtil.mouseHovered(getXPos() - d, getYPos(), getDimensions()[0] + d * 2, getDimensions()[1] + d * 2);
    }

    @Override
    public void drawShader() {
        /*
        ScissorUtil.enableScissor();
        ScissorUtil.scissor(getXPos() - 20, getYPos(), getDimensions()[0] + 40, getDimensions()[1]);
        GameMain.getInstance().getGlslShaderUtil().draw();
        ScissorUtil.disableScissor();

         */
        super.drawShader();
    }
}
