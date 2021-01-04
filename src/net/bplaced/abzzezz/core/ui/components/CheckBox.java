/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 07.06.20, 16:58
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.core.ui.components;

import net.bplaced.abzzezz.core.util.animation.AnimationUtil;
import net.bplaced.abzzezz.core.util.animation.easing.Bounce;
import net.bplaced.abzzezz.core.util.animation.easing.Quint;
import net.bplaced.abzzezz.core.util.io.MouseUtil;
import net.bplaced.abzzezz.core.util.render.RenderUtil;

public class CheckBox implements UIComponent {

    private final float xPos;
    private final float yPos;
    private boolean checked;
    private int size;
    private String text;
    private AnimationUtil animationUtil;
    private StateChangedListener stateChangedListener;
    private float stringX, stringY, circleX, circleY;

    public CheckBox(float xPos, float yPos, int size, String text) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.size = size;
        this.text = text;
    }

    public CheckBox(boolean checked, float xPos, float yPos, int size, String text) {
        this.checked = checked;
        this.xPos = xPos;
        this.yPos = yPos;
        this.size = size;
        this.text = text;
    }

    public CheckBox(boolean checked, float xPos, float yPos, String text) {
        this.checked = checked;
        this.xPos = xPos;
        this.yPos = yPos;
        this.text = text;
        //Auto set
        this.size = textFont.getHeight(text);
    }

    public CheckBox(float xPos, float yPos, String text) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.text = text;
        //Auto set
        this.size = textFont.getHeight(text);
    }

    @Override
    public void initComponent() {
        this.animationUtil = new AnimationUtil(new Bounce(), 0, 0, size / 2,  2, false);
        refreshPositions();
    }

    @Override
    public void refreshPositions() {
        stringX = xPos + size;
        stringY = yPos + textFont.getHeight(text) / 2;
        circleX = xPos + size / 2;
        circleY = yPos + size / 2;
    }

    @Override
    public void drawComponent() {
        animationUtil.animate();

        RenderUtil.drawQuad(xPos, yPos, size, size, mainColor);

        RenderUtil.drawCircle(circleX, circleY, animationUtil.getInt(), 3, textColor);

        textFont.drawString(text, stringX, stringY, textColor);
    }

    @Override
    public void keyListener(int keyCode, char keyTyped) {
    }

    @Override
    public void mouseListener(int mouseButton) {
        if (checkBoxHovered() && mouseButton == 0) {
            checked = !checked;
            if (stateChangedListener != null) stateChangedListener.onStateChanged(checked);
            if (!checked) animationUtil.reset(true);
        }
    }

    @Override
    public void drawShader() {
    }

    public void setStateChangedListener(StateChangedListener stateChangedListener) {
        this.stateChangedListener = stateChangedListener;
    }

    private boolean checkBoxHovered() {
        return MouseUtil.mouseHovered(xPos, yPos, size, size);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
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
        return size;
    }

    @Override
    public int getHeight() {
        return size;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public interface StateChangedListener {
        void onStateChanged(boolean state);
    }
}
