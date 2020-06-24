/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 07.06.20, 16:58
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.engine.ui.uicomponents;

import ga.abzzezz.util.easing.Quint;
import ga.abzzezz.util.math.AnimationUtil;
import net.bplaced.abzzezz.engine.utils.MouseUtil;
import net.bplaced.abzzezz.engine.utils.RenderUtil;
import net.bplaced.abzzezz.engine.utils.Util;

import java.awt.*;

public class CheckBox implements UIComponent {

    private boolean checked;
    private float xPos, yPos;
    private int size;
    private String text;
    private AnimationUtil animationUtil;

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
        this.size = (int) textFont.getHeight();
    }

    public CheckBox(float xPos, float yPos, String text) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.text = text;
        //Auto set
        this.size = (int) textFont.getHeight();
    }

    @Override
    public void initComponent() {
        this.animationUtil = new AnimationUtil(Quint.class, 0, 0, size / 2, 1, true, false);
    }

    /**
     * Check box now animated
     */
    @Override
    public void drawComponent() {
        if (checked) animationUtil.animate();
        RenderUtil.drawCircle(xPos + size / 2, yPos, size, 3, Util.mainColor);
        RenderUtil.drawCircle(xPos + size / 2, yPos, animationUtil.getInt(), 3, Util.mainColor.darker());

        textFont.drawString(text, xPos + size * 2, yPos - textFont.getHeight() / 1.5F, Color.BLACK);
    }

    @Override
    public void keyListener(int keyCode, char keyTyped) {
    }

    @Override
    public void mouseListener(int mouseButton) {
        if (checkBoxHovered() && mouseButton == 0) {
            checked = !checked;
            if (!checked) animationUtil.reset();
        }
    }

    @Override
    public void drawShader() {

    }

    private boolean checkBoxHovered() {
        return MouseUtil.mouseHovered(xPos, yPos, size);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public void setYPos(float xPos) {
        this.xPos = xPos;
    }

    public void setYos(float yPos) {
        this.yPos = yPos;
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

}
