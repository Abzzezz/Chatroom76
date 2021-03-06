/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.game.ui.component;

import net.bplaced.abzzezz.core.ui.component.Button;
import net.bplaced.abzzezz.core.util.io.MouseUtil;
import net.bplaced.abzzezz.core.util.render.RenderUtil;
import net.bplaced.abzzezz.game.shader.BlurShader;

public class ShaderButton extends Button {

    private final int d = 10;
    private final BlurShader blurShader;

    /**
     * Simple button. Buttons can be added just use this as a parent
     *
     * @param id
     * @param text
     * @param xPos
     * @param yPos
     */
    public ShaderButton(float id, String text, float xPos, float yPos) {
        super(id, text, xPos, yPos);
        blurShader = new BlurShader(xPos - d, yPos, getDimen()[0], getDimen()[1]);
    }

    public ShaderButton(float id, String text, float xPos, float yPos, int width, int height) {
        super(id, text, xPos, yPos, width, height);
        blurShader = new BlurShader(xPos - d, yPos, getDimen()[0], getDimen()[1]);
    }

    public ShaderButton(float id, String text, float xPos, float yPos, int width, int height, boolean enabled) {
        super(id, text, xPos, yPos, width, height, enabled);
        blurShader = new BlurShader(xPos - d, yPos, getDimen()[0], getDimen()[1]);
    }

    public ShaderButton(float id, String text, float xPos, float yPos, boolean enabled) {
        super(id, text, xPos, yPos, enabled);
        blurShader = new BlurShader(xPos - d, yPos, getDimen()[0], getDimen()[1]);
    }

    @Override
    public void drawComponent() {
        RenderUtil.drawQuad(getXPos() - d, getYPos(), getDimen()[0], getDimen()[1], mainColor);
        textFont.drawString(getText(), getXPos() + getDimensions()[0] / 2 - textFont.getStringWidth(getText()) / 2, getYPos() + d / 2, isEnabled() ? textColor : mainColor);
    }

    public int[] getDimen() {
        return new int[]{getDimensions()[0] + d * 2, getDimensions()[1] + d * 2};
    }

    @Override
    public boolean buttonHovered() {
        return MouseUtil.mouseHovered(getXPos() - d, getYPos(), getDimen()[0], getDimen()[1]);
    }

    @Override
    public void drawShader() {
        blurShader.draw();
        super.drawShader();
    }
}
