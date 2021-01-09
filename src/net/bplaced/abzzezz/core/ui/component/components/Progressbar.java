/*
 * @author Roman
 * Last modified: 09.01.21, 22:44 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.core.ui.component.components;

import net.bplaced.abzzezz.core.ui.component.UIComponent;

public class Progressbar implements UIComponent {


    private final String title;

    private final StringBuilder text = new StringBuilder("[]");

    private final int maxBar = 10;
    private final float step;

    private final float max;
    private float current;


    private final int yPos;
    private int yStack;
    private final int height;

    //TODO: Add command line locking

    public Progressbar(final String title, final int yPos, float max, float current) {
        this.title = title;
        this.max = max;
        this.current = current;
        this.yPos = yPos;
        this.height = textFont.getHeight(title + "\n [==========]");

        System.out.println(height);

        this.step = max / maxBar;

        final int i = (int) (current / maxBar);
        for (int j = 0; j < i; j++) {
            this.text.insert(1, '=');
        }
    }


    @Override
    public void draw() {
        textFont.drawWhiteString(title + "\n" + text.toString(), xPos, yPos + yStack);
    }

    @Override
    public void setYStack(int increment) {

    }

    public void incrementCurrent(float increment) {
        if (this.current < max) {
            this.current += increment;

            if ((current / step) * maxBar % maxBar == 0) {
                this.text.insert(1, '=');
            }
        }

    }

    public float getMax() {
        return max;
    }

    public float getCurrent() {
        return current;
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
