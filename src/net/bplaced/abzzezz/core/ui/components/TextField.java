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

import net.bplaced.abzzezz.core.util.io.KeyboardUtil;
import net.bplaced.abzzezz.core.util.io.MouseUtil;
import net.bplaced.abzzezz.core.util.render.ColorUtil;
import net.bplaced.abzzezz.core.util.render.FontUtil;
import net.bplaced.abzzezz.core.util.render.RenderUtil;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TextField implements UIComponent {

    private final List<Character> backupText = new CopyOnWriteArrayList<>();
    private final StringBuilder displayText = new StringBuilder();
    private final float xPos;
    private final float yPos;
    private final int width;
    private final int height;
    private final String name;
    private boolean clicked, selectedAll;
    private FontUtil fontUtil;

    /*
    TODO: More work, Adding to clipboard etc. Text moving, selecting
     */
    public TextField(float xPos, float yPos, String name) {
        this.xPos = xPos;
        this.yPos = yPos;
        //Auto set
        this.width = 100;
        this.height = 20;
        this.name = name;
    }

    public TextField(float xPos, float yPos, int width, int height, String name) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.name = name;
    }

    @Override
    public void initComponent() {
        fontUtil = new FontUtil(ColorUtil.TEXT_FONT, width / 16);
    }

    @Override
    public void drawComponent() {
        RenderUtil.drawQuad(xPos, yPos, width, height, clicked ? ColorUtil.MAIN_COLOR : ColorUtil.MAIN_COLOR.darker());
        //    ScissorUtil.enableScissor();
        // ScissorUtil.scissor(xPos, yPos, width, height);
        fontUtil.drawString(displayText.toString(), xPos, yPos + height / 2 - fontUtil.getHeight() / 2, selectedAll ? Color.LIGHT_GRAY : textColor);
        // ScissorUtil.disableScissor();
        textFont.drawString(name, xPos, yPos - height, textColor);
    }

    /**
     * @param keyCode
     * @param keyTyped
     */
    @Override
    public void keyListener(int keyCode, char keyTyped) {
        if (isClicked()) {

            if (KeyboardUtil.isControlA()) {
                selectedAll = true;
            } else if (KeyboardUtil.isDeleteAll()) {
                deleteAllText();
            } else if (KeyboardUtil.isControlV()) {
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

                    if (backupText.size() > 0) {
                        int index = backupText.size() - 1;
                        displayText.insert(0, backupText.get(index));
                        backupText.remove(index);
                    }
                }
            } else {
                //If text out of bounds append old characters to backuptext and delete from displayed string
                boolean disallowed = !(keyCode == Keyboard.KEY_LSHIFT) && !(keyCode == Keyboard.KEY_RSHIFT) && !(keyCode == Keyboard.KEY_RCONTROL) && !(keyCode == Keyboard.KEY_LCONTROL);
                if (disallowed)
                    displayText.append(keyTyped);
            }

            while (fontUtil.getStringWidth(displayText.toString()) > width - 10) {
                backupText.add(displayText.charAt(0));
                displayText.deleteCharAt(0);
            }
        }
    }


    private void deleteAllText() {
        displayText.delete(0, displayText.length());
        backupText.clear();
    }

    @Override
    public void mouseListener(int mouseButton) {
        if (isTextFieldHovered() && mouseButton == 0) clicked = !clicked;
    }

    @Override
    public void drawShader() {

    }

    private boolean isTextFieldHovered() {
        return MouseUtil.mouseHovered(xPos, yPos, width, height);
    }

    /**
     * Return text as string by combining backup and displayed text
     *
     * @return
     */
    public String toString() {
        StringBuilder backupOut = new StringBuilder();
        backupText.forEach(backupOut::append);
        backupOut.append(displayText);
        return backupOut.toString();
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
