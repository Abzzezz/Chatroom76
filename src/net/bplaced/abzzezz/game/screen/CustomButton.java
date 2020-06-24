package net.bplaced.abzzezz.game.screen;

import net.bplaced.abzzezz.engine.ui.uicomponents.Button;
import net.bplaced.abzzezz.engine.utils.ScissorUtil;
import net.bplaced.abzzezz.game.MainClass;

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

    @Override
    public void drawComponent() {
        //  RenderUtil.drawQuad(getXPos() - 20, getYPos(), getDimensions()[0] + 40, getDimensions()[1], new Color(255, 255, 255, 100));
        textFont.drawString(getText(), getXPos() + getDimensions()[0] / 2 - textFont.getStringWidth(getText()) / 2, getYPos(), Color.WHITE);
    }

    @Override
    public void drawShader() {
        ScissorUtil.enableScissor();
        ScissorUtil.scissor(getXPos() - 20, getYPos(), getDimensions()[0] + 40, getDimensions()[1]);
        MainClass.getInstance().getShader().draw();
        ScissorUtil.disableScissor();
        super.drawShader();
    }
}
