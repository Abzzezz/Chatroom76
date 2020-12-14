/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 07.06.20, 15:15
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.core.ui.components;


import net.bplaced.abzzezz.core.ui.BasicComponent;

/**
 * UIComponent interface. Add this to own ui components
 */

public interface UIComponent extends BasicComponent {

    void initComponent();

    void drawComponent();

    void keyListener(int keyCode, char keyTyped);

    void mouseListener(int mouseButton);

    void drawShader();

    void refreshPositions();
}
