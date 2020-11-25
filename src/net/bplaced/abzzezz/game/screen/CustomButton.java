package net.bplaced.abzzezz.game.screen;

import net.bplaced.abzzezz.engine.ui.uicomponents.Button;
import net.bplaced.abzzezz.engine.utils.ScissorUtil;
import net.bplaced.abzzezz.game.GameMain;

import java.awt.*;

public class CustomButton extends Button {


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
        //  RenderUtil.drawQuad(getXPos() - 20, getYPos(), getDimensions()[0] + 40, getDimensions()[1], new Color(255, 255, 255, 100));
        textFont.drawString(getText(), getXPos() + getDimensions()[0] / 2 - textFont.getStringWidth(getText()) / 2, getYPos(), Color.WHITE);
    }

    @Override
    public void drawShader() {
        ScissorUtil.enableScissor();
        ScissorUtil.scissor(getXPos() - 20, getYPos(), getDimensions()[0] + 40, getDimensions()[1]);
        GameMain.getInstance().getGlslShaderUtil().draw();
        ScissorUtil.disableScissor();
        super.drawShader();
    }
}
