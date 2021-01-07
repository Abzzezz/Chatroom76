/*
 * @author Roman
 * Last modified: 07.01.21, 20:10 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.core.ui.elements;

import net.bplaced.abzzezz.core.Basic;

public interface UIElement extends Basic {

    void initElement();

    void drawElement();

    void keyListener(int keyCode, char keyTyped);

    void mouseListener(int mouseButton);

    float getXPos();

    float getYPos();

    int getWidth();

    int getHeight();
}
