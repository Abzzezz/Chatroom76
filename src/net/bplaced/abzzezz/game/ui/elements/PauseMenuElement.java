/*
 * @author Roman
 * Last modified: 07.01.21, 19:58 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.ui.elements;

import net.bplaced.abzzezz.core.ui.elements.UIElement;
import org.lwjgl.input.Keyboard;

import java.util.function.Consumer;

/*
Element with keyboard input
 */
public class PauseMenuElement implements UIElement {

    private final String text;
    private final Consumer<Void> action;
    private final float xPos, yPos;
    private boolean selected;

    private int width, height;

    public PauseMenuElement(String text, float xPos, float yPos, Consumer<Void> action) {
        this.text = text;
        this.action = action;
        this.xPos = xPos;
        this.yPos = yPos;
        initElement();
    }

    public PauseMenuElement(String text, float xPos, float yPos, boolean selected, Consumer<Void> action) {
        this.text = text;
        this.action = action;
        this.xPos = xPos;
        this.yPos = yPos;
        this.selected = selected;
        initElement();
    }

    @Override
    public void initElement() {

    }

    @Override
    public void drawElement() {

    }

    @Override
    public void keyListener(int keyCode, char keyTyped) {
        if (selected && keyCode == Keyboard.KEY_RETURN) action.accept(null);
    }

    @Override
    public void mouseListener(int mouseButton) {

    }

    public String getText() {
        return text;
    }

    public Consumer<Void> getAction() {
        return action;
    }

    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
