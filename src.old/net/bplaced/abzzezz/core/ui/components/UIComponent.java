/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.core.ui.component;


import net.bplaced.abzzezz.core.Basic;

/**
 * UIComponent interface. Add this to own ui components
 */

public interface UIComponent extends Basic {

    void initComponent();

    void drawComponent();

    void keyListener(int keyCode, char keyTyped);

    void mouseListener(int mouseButton);

    void drawShader();

    void refreshPositions();

    float getXPos();

    float getYPos();

    int getWidth();

    int getHeight();

}
