/*
 * @author Roman
 * Last modified: 09.01.21, 22:44 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.core.ui.component.components;

import net.bplaced.abzzezz.core.ui.component.UIComponent;
import net.bplaced.abzzezz.game.Game;

import java.awt.*;

public class Progressbar implements UIComponent {


    private final String title;

    private final StringBuilder text = new StringBuilder("[]");

    private final int maxBar = 10;
    private final float step;

    private final float max;
    private float current;

    private boolean restrictsInput;

    private final int yPos;
    private int yStack;
    private final int height;


    private Color progressColor = Color.WHITE;

    public Progressbar(final String title, final int yPos, float max, float current) {
        this.title = title;
        this.max = max;
        this.current = current;
        this.yPos = yPos;
        this.height = textFont.getHeight(title + "\n [==========]");

        this.step = max / maxBar;

        final int i = (int) (current / maxBar);
        for (int j = 0; j < i; j++) {
            this.text.insert(1, '=');
        }
    }

    public Progressbar(String title, float max, float current, boolean restrictsInput, int yPos) {
        this.title = title;
        this.max = max;
        this.current = current;
        this.restrictsInput = restrictsInput;
        this.yPos = yPos;
        this.height = textFont.getHeight(title + "\n [==========]");

        this.step = max / maxBar;

        final int i = (int) (current / maxBar);
        for (int j = 0; j < i; j++) {
            this.text.insert(1, '=');
        }

        if (restrictsInput)
            Game.GAME.getCommandLine().setInputRestricted(true);
    }

    @Override
    public void draw() {
        textFont.drawString(title + "\n" + text.toString(), xPos, yPos + yStack, progressColor);
    }

    @Override
    public void setYStack(int increment) { }

    public void incrementCurrent(float increment) {
        if (this.current < max) {
            this.current += increment;

            if ((current / step) * maxBar % maxBar == 0) {
                this.text.insert(1, '=');
            }
        } else {
            progressColor = accentColor;
            //Stop restricting input
            if (restrictsInput)
                Game.GAME.getCommandLine().setInputRestricted(false);
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
