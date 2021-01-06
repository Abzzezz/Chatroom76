/*
 * @author Roman
 * Last modified: 06.01.21, 22:47 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.ui.component;

import net.bplaced.abzzezz.core.ui.components.UIComponent;

import java.util.function.Consumer;

public class Option implements UIComponent {

    private final String title;
    private final float xPos;
    private final float yPos;
    private Consumer<Void> consumer;

    public Option(float xPos, float yPos, String text, final Consumer<Void> consumer) {
        this.title = text;
        this.xPos = xPos;
        this.yPos = yPos;
        this.consumer = consumer;
    }

    public Option(float xPos, float yPos, String text) {
        this.title = text;
        this.xPos = xPos;
        this.yPos = yPos;
    }


    @Override
    public void initComponent() {
    }

    @Override
    public void drawComponent() {
        textFont.drawString(title, xPos, yPos, textColor);
    }

    @Override
    public void keyListener(int keyCode, char keyTyped) {

    }

    @Override
    public void mouseListener(int mouseButton) {

    }

    @Override
    public void drawShader() {

    }

    @Override
    public void refreshPositions() {

    }

    public Consumer<Void> getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer<Void> consumer) {
        this.consumer = consumer;
    }

    public String getTitle() {
        return title;
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
        return textFont.getStringWidth(title);
    }

    @Override
    public int getHeight() {
        return textFont.getHeight(title);
    }
}
