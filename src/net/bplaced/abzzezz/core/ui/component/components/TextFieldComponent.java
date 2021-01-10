/*
 * @author Roman
 * Last modified: 09.01.21, 21:36 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.core.ui.component.components;

import net.bplaced.abzzezz.core.ui.component.UIComponent;
import net.bplaced.abzzezz.game.Game;

import java.util.function.Consumer;

public class TextFieldComponent implements UIComponent {

    private final String title;
    private final int yPos;
    private String text = "";
    private int yStack;

    public TextFieldComponent(final String title, final int yPos, final Consumer<String> enterAction) {
        this.title = title;
        this.yPos = yPos;
        Game.GAME.getCommandLine().requestNextInput(s -> {
            text = s;
            enterAction.accept(text);
        });
    }

    @Override
    public void draw() {
        textFont.drawWhiteString(title + ": " + text, xPos, yPos + yStack);
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
        return textFont.getHeight();
    }
}
