/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 07.06.20, 15:15
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.engine.ui.uicomponents;


import net.bplaced.abzzezz.engine.EngineCore;
import net.bplaced.abzzezz.engine.utils.FontUtil;
import net.bplaced.abzzezz.engine.utils.Util;

import java.awt.*;

public interface UIComponent {

    /**
     * UIComponent interface. Add this to own ui components
     */

    EngineCore engineCoreInstance = EngineCore.getInstance();
    FontUtil textFont = new FontUtil(Util.textFont, 20);
    Color textColor = Util.textColor;


    void initComponent();

    void drawComponent();

    void keyListener(int keyCode, char keyTyped);

    void mouseListener(int mouseButton);

    void drawShader();

}
