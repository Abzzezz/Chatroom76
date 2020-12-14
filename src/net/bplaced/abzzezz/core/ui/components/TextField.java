/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 07.06.20, 15:09
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.core.ui.components;

import net.bplaced.abzzezz.core.util.AllowedCharacter;
import net.bplaced.abzzezz.core.util.TimeUtil;
import net.bplaced.abzzezz.core.util.io.KeyboardUtil;
import net.bplaced.abzzezz.core.util.io.MouseUtil;
import net.bplaced.abzzezz.core.util.render.ColorUtil;
import net.bplaced.abzzezz.core.util.render.FontUtil;
import net.bplaced.abzzezz.core.util.render.RenderUtil;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class TextField implements UIComponent {

    private final StringBuilder backupText = new StringBuilder();
    private final StringBuilder displayText = new StringBuilder();
    private final float xPos;
    private final float yPos;
    private final int width;
    private final int height;
    private final String title;
    private final TimeUtil bounceTime = new TimeUtil(), bounceTime2 = new TimeUtil();
    private boolean clicked, selectedAll;
    private FontUtil fontUtil;

    /*
    TODO: More work, Adding to clipboard etc. Text moving, selecting
     */
    public TextField(float xPos, float yPos, String title) {
        this.xPos = xPos;
        this.yPos = yPos;
        //Auto set
        this.width = 100;
        this.height = 20;
        this.title = title;
    }

    public TextField(float xPos, float yPos, int width, int height, String title) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.title = title;
    }

    private float titleY, displayStringY;

    @Override
    public void initComponent() {
        fontUtil = new FontUtil(ColorUtil.TEXT_FONT, width / 16);
        refreshPositions();
    }

    @Override
    public void refreshPositions() {
        titleY = yPos - height;
        displayStringY = yPos + height / 2 - fontUtil.getHeight() / 2;
    }

    @Override
    public void drawComponent() {
        final String text = displayText.toString();
        RenderUtil.drawQuad(xPos, yPos, width, height, clicked ? ColorUtil.MAIN_COLOR : ColorUtil.MAIN_COLOR.darker());

        if (clicked) {
            if (bounceTime.isTimeOver(1000)) {
                if (bounceTime2.isTimeOver(1600)) {
                    bounceTime2.reset();
                    bounceTime.reset();
                }
            } else {
                final int tabHeight = 4;
                RenderUtil.drawQuad(xPos + fontUtil.getStringWidth(text), yPos + height - tabHeight, tabHeight * 2, tabHeight, ColorUtil.MAIN_COLOR);
            }
        }
        fontUtil.drawString(text, xPos, displayStringY, selectedAll ? Color.LIGHT_GRAY : textColor);
        textFont.drawString(title, xPos, titleY, textColor);
    }

    /**
     * @param keyCode
     * @param keyTyped
     */
    @Override
    public void keyListener(int keyCode, char keyTyped) {
        if (isClicked()) {

            if (KeyboardUtil.ctrlADown()) {
                selectedAll = true;
            } else if (KeyboardUtil.shiftReturn()) {
                deleteAllText();
            } else if (KeyboardUtil.ctrlVDown()) {
                displayText.append(KeyboardUtil.getClipboard());
            }

            if (keyCode == Keyboard.KEY_BACK) {
                //Control A Delete
                if (selectedAll) {
                    deleteAllText();
                    selectedAll = false;
                    return;
                }
                if (displayText.length() > 0) {
                    displayText.deleteCharAt(displayText.length() - 1);

                    if (backupText.length() > 0) {
                        int index = backupText.length() - 1;
                        displayText.insert(0, backupText.charAt(index));
                        backupText.deleteCharAt(index);
                    }
                }
            } else {
                //If text out of bounds append old characters to backuptext and delete from displayed string
                boolean disallowed = !(keyCode == Keyboard.KEY_LSHIFT) && !(keyCode == Keyboard.KEY_RSHIFT) && !(keyCode == Keyboard.KEY_RCONTROL) && !(keyCode == Keyboard.KEY_LCONTROL);
                if (disallowed && AllowedCharacter.isAllowedCharacter(keyTyped))
                    displayText.append(keyTyped);
            }

            while (fontUtil.getStringWidth(displayText.toString()) > width - 10) {
                backupText.append(displayText.charAt(0));
                displayText.deleteCharAt(0);
            }
        }
    }


    private void deleteAllText() {
        displayText.delete(0, displayText.length());
        backupText.delete(0, backupText.length());
    }

    @Override
    public void mouseListener(int mouseButton) {
        if (isTextFieldHovered() && mouseButton == 0) clicked = !clicked;
    }

    @Override
    public void drawShader() {}

    private boolean isTextFieldHovered() {
        return MouseUtil.mouseHovered(xPos, yPos, width, height);
    }

    /**
     * Return text as string by combining backup and displayed text
     *
     * @return
     */
    public String toString() {
        return String.valueOf(backupText) + displayText;
    }

    public boolean isClicked() {
        return clicked;
    }

    public float getYPos() {
        return yPos;
    }

    public float getXPos() {
        return xPos;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
