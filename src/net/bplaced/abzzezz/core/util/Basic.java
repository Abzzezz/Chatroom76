/*
 * @author Roman
 * Last modified: 07.01.21, 23:13 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.core.util;

import net.bplaced.abzzezz.core.ui.component.UIComponent;
import net.bplaced.abzzezz.core.util.render.FontUtil;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

public interface Basic {

    /* ----------------- Basic information ----------------- */

    String gameName = "Chatroom76";

    String gameVersion = "alpha 1.0";

    /* ----------------- Basic UI ----------------- */

    Queue<UIComponent> componentQueue = new ArrayDeque<>();

    List<UIComponent> uiComponents = new CopyOnWriteArrayList<>();

    int xPos = 0;

    /* ----------------- Look and feel ----------------- */

    String defaultFont = "Terminal";

    int textFontSize = 30;

    FontUtil textFont = new FontUtil(defaultFont, "ttf", textFontSize);

    Color textColor = Color.WHITE;

    Color mainColor = new Color(255, 255, 255, 75);

}
