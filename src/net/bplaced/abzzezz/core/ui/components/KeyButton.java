package net.bplaced.abzzezz.core.ui.components;

import org.lwjgl.input.Keyboard;

public class KeyButton implements UIComponent {

    private final float xPos, yPos, id;
    private final int width, height;
    private final String text;
    private boolean selected;

    public KeyButton(final float id, float xPos, float yPos, int width, int height, String text) {
        this.id = id;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.text = text;
    }

    public KeyButton(final float id, float xPos, float yPos, int width, int height, String text, boolean selected) {
        this.id = id;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.text = text;
        this.selected = selected;
    }


    @Override
    public void initComponent() {

    }

    @Override
    public void refreshPositions() {

    }

    @Override
    public void drawShader() {

    }

    @Override
    public void drawComponent() {
        textFont.drawString(text, xPos, yPos, isSelected() ? mainColor : textColor);
    }

    @Override
    public void keyListener(int keyCode, char keyTyped) {
        if (this != OPEN_GL_CORE_INSTANCE.getScreen().getSelectedKeyButton()) return;

        if (keyCode == Keyboard.KEY_RETURN) OPEN_GL_CORE_INSTANCE.getScreen().keyButtonEntered(getID());
    }

    @Override
    public void mouseListener(int mouseButton) {

    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getText() {
        return text;
    }

    public float getID() {
        return id;
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
}
