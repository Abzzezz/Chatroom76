package net.bplaced.abzzezz.game.screen;

import net.bplaced.abzzezz.engine.ui.uicomponents.Button;
import net.bplaced.abzzezz.engine.utils.MouseUtil;
import net.bplaced.abzzezz.engine.utils.RenderUtil;
import net.bplaced.abzzezz.engine.utils.ColorUtil;

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

    @Override
    public void drawComponent() {
        final int quadHeight = getDimensions()[1] + d * 2;
        RenderUtil.drawQuad(getXPos() - d, getYPos(), getDimensions()[0] + d * 2, quadHeight, ColorUtil.MAIN_COLOR);
        textFont.drawString(getText(), getXPos() + getDimensions()[0] / 2 - textFont.getStringWidth(getText()) / 2, getYPos() + d / 2, ColorUtil.MAIN_COLOR);
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
