/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.core.ui.component;

import net.bplaced.abzzezz.core.util.io.MouseUtil;
import net.bplaced.abzzezz.core.util.render.RenderUtil;

public class Button implements UIComponent {

    private final String text;
    private final float xPos;
    private final float yPos;
    private final float id;
    private final int width, height;
    private boolean enabled;
    /**
     * Button pressed
     *
     * @param mouseButton
     */
    private ButtonPressed buttonPressed;
    private float stringX;

    /**
     * Simple button. Buttons can be added just use this as a parent
     *
     * @param id
     * @param text
     * @param xPos
     * @param yPos
     */
    public Button(float id, String text, float xPos, float yPos) {
        this.id = id;
        this.text = text;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = textFont.getStringWidth(text);
        this.height = (int) textFont.getHeight();
        this.enabled = true;
    }

    public Button(float id, String text, float xPos, float yPos, int width, int height) {
        this.id = id;
        this.text = text;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.enabled = true;
    }

    public Button(float id, String text, float xPos, float yPos, int width, int height, boolean enabled) {
        this.text = text;
        this.xPos = xPos;
        this.yPos = yPos;
        this.id = id;
        this.width = width;
        this.height = height;
        this.enabled = enabled;
    }

    public Button(float id, String text, float xPos, float yPos, boolean enabled) {
        this.text = text;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = textFont.getStringWidth(text);
        this.height = (int) textFont.getHeight();
        this.id = id;
        this.enabled = enabled;
    }

    @Override
    public void refreshPositions() {
        stringX = xPos + width / 2 - textFont.getStringWidth(getText()) / 2;
    }

    public boolean buttonHovered() {
        return MouseUtil.mouseHovered(xPos, yPos, getDimensions()[0], getDimensions()[1]);
    }

    public float getId() {
        return id;
    }

    public int[] getDimensions() {
        return new int[]{width, height};
    }

    @Override
    public void initComponent() {
        refreshPositions();
    }

    /**
     * Size is dependent on text length
     */
    @Override
    public void drawComponent() {
        RenderUtil.drawQuad(xPos, yPos, getDimensions()[0], getDimensions()[1], buttonHovered() ? mainColor.darker() : mainColor);
        textFont.drawString(text, stringX, yPos, buttonHovered() ? textColor.brighter() : textColor);
    }

    @Override
    public void keyListener(int keyCode, char keyTyped) {
    }

    @Override
    public void mouseListener(int mouseButton) {
        if (!isEnabled()) return;
        if (buttonHovered() && mouseButton == 0) OPEN_GL_CORE_INSTANCE.getScreen().buttonPressed(getId());

        if (buttonHovered()) {
            if (buttonPressed != null)
                buttonPressed.onButtonPressed(mouseButton, this);
        }
    }

    @Override
    public void drawShader() {
    }

    @Override
    public float getXPos() {
        return xPos;
    }

    @Override
    public float getYPos() {
        return yPos;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public String getText() {
        return text;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ButtonPressed getButtonPressed() {
        return buttonPressed;
    }

    public void setButtonPressed(final ButtonPressed buttonPressed) {
        this.buttonPressed = buttonPressed;
    }

    public interface ButtonPressed {
        void onButtonPressed(int mouseButton, Button button);
    }
}
