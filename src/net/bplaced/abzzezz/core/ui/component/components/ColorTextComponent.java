/*
 * @author Roman
 * Last modified: 09.01.21, 19:36 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.core.ui.component.components;

import net.bplaced.abzzezz.core.ui.component.UIComponent;

import java.awt.*;

public class ColorTextComponent implements UIComponent {

    private final String text;
    private final int yPos;
    private final Color color;
    private int yStack;

    public ColorTextComponent(final String text, final int yPos, final Color color) {
        this.text = text;
        this.yPos = yPos;
        this.color = color;
    }

    @Override
    public void draw() {
        textFont.drawString(getText(), xPos, yPos + yStack, color);
    }

    @Override
    public void setYStack(int increment) {
        yStack -= increment;
    }

    @Override
    public int yPos() {
        return yPos;
    }

    @Override
    public int height() {
        return textFont.getHeight(getText());
    }

    public String getText() {
        return text;
    }
}
