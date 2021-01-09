/*
 * @author Roman
 * Last modified: 07.01.21, 23:13 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.core.util;

import net.bplaced.abzzezz.core.ui.component.UIComponent;

import java.io.File;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

public interface Basic {

    /* ----------------- Basic information ----------------- */

    String gameName = "Chatroom76";

    String gameVersion = "alpha 1.0";

    File workingDirectory = new File(System.getProperty("LOCALAPPDATA"), gameName);

    /* ----------------- Basic UI ----------------- */

    Queue<UIComponent> componentQueue = new ArrayDeque<>();

    List<UIComponent> uiComponents = new CopyOnWriteArrayList<>();

    int xPos = 0;

}
