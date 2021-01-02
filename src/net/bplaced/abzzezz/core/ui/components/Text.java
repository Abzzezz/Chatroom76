package net.bplaced.abzzezz.core.ui.components;

import net.bplaced.abzzezz.core.util.render.FontUtil;

import java.awt.*;

/**
 * Immutable Text ui component
 */
public class Text implements UIComponent {

    private final String text;
    private final FontUtil fontUtil;
    private final int textWidth;
    private final int textHeight;
    private final org.newdawn.slick.Color textColor;
    private float xPos, yPos;

    public Text(float xPos, float yPos, String text, final Color textColor, final FontUtil fontUtil) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.text = text;
        this.fontUtil = fontUtil;
        this.textWidth = fontUtil.getStringWidth(text);
        this.textHeight = fontUtil.getHeight(text);
        this.textColor = new org.newdawn.slick.Color(textColor.getRGB());
    }

    public Text(float xPos, float yPos, String text, final Color textColor, final boolean center, final FontUtil fontUtil) {
        this.xPos = center ? xPos - fontUtil.getStringWidth(text) / 2 : xPos;
        this.yPos = yPos;
        this.text = text;
        this.fontUtil = fontUtil;
        this.textWidth = fontUtil.getStringWidth(text);
        this.textHeight = fontUtil.getHeight(text);
        this.textColor = new org.newdawn.slick.Color(textColor.getRGB());
    }


    @Override
    public void initComponent() {

    }

    @Override
    public void drawComponent() {
        fontUtil.drawString(getText(), getXPos(), getYPos(), getTextColor());
    }

    @Override
    public void keyListener(int keyCode, char keyTyped) {

    }

    @Override
    public void mouseListener(int mouseButton) {

    }

    @Override
    public void drawShader() {

    }

    @Override
    public void refreshPositions() {

    }

    public String getText() {
        return text;
    }

    public org.newdawn.slick.Color getTextColor() {
        return textColor;
    }

    @Override
    public float getXPos() {
        return xPos;
    }

    public void setXPos(float xPos) {
        this.xPos = xPos;
    }

    @Override
    public float getYPos() {
        return yPos;
    }

    public void setYPos(float yPos) {
        this.yPos = yPos;
    }

    @Override
    public int getWidth() {
        return textWidth;
    }

    @Override
    public int getHeight() {
        return textHeight;
    }
}
