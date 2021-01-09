/*
 * @author Roman
 * Last modified: 06.01.21, 21:22 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.ui.component;

import net.bplaced.abzzezz.core.ui.component.CheckBox;

public class DOSCheckBox extends CheckBox {

    public DOSCheckBox(float xPos, float yPos, int size, String text) {
        super(xPos, yPos, size, text);
    }

    public DOSCheckBox(boolean checked, float xPos, float yPos, int size, String text) {
        super(checked, xPos, yPos, size, text);
    }

    public DOSCheckBox(boolean checked, float xPos, float yPos, String text) {
        super(checked, xPos, yPos, text);
    }

    public DOSCheckBox(float xPos, float yPos, String text) {
        super(xPos, yPos, text);
    }

    @Override
    public void drawComponent() {
        textFont.drawString("[" + (isChecked() ? "X" : " ") + "] " + getText(), getXPos(), getYPos(), textColor);
    }
}
