/*
 * @author Roman
 * Last modified: 09.01.21, 19:36 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.core.ui.component.components;

import net.bplaced.abzzezz.core.ui.component.UIComponent;

public class TextComponent implements UIComponent {

    private final String text;
    private final int yPos;
    private int yStack;

    public TextComponent(final String text, final int yPos) {
        this.text = text;
        this.yPos = yPos;
    }

    @Override
    public void draw() {
        textFont.drawWhiteString(getText(), xPos, yPos + yStack);
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
