/*
 * @author Roman
 * Last modified: 10.01.21, 17:51 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.core.ui.component.components;

import net.bplaced.abzzezz.core.ui.component.UIComponent;

import java.awt.*;
import java.util.function.Consumer;

public class OptionComponent implements UIComponent {

    private final String optionName;
    private final int yPos;
    private int yStack;

    private boolean activated;
    private final Consumer<Void> action;
    private final int height;

    private Color color = textColor;

    public OptionComponent(String text, int yPos, Consumer<Void> action) {
        this.optionName = text;
        this.yPos = yPos;
        this.action = action;
        this.height = textFont.getHeight(getOptionName());
    }

    @Override
    public void draw() {
        textFont.drawString(getOptionName(), xPos, yPos + yStack, color);
    }

    public void trigger() {
        if (!activated) {
            action.accept(null);
            activated = true;
            color = accentColor;
            //TODO: Replace with text component
        }
    }

    @Override
    public void setYStack(int increment) {
        this.yStack -= increment;
    }

    public String getOptionName() {
        return optionName;
    }


    @Override
    public int yPos() {
        return yPos;
    }

    @Override
    public int height() {
        return height;
    }
}
