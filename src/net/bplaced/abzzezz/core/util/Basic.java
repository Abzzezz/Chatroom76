/*
 * @author Roman
 * Last modified: 07.01.21, 23:13 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.core.util;

import net.bplaced.abzzezz.core.ui.component.UIComponent;
import net.bplaced.abzzezz.core.ui.component.components.OptionComponent;
import net.bplaced.abzzezz.core.ui.screen.Screen;

import java.io.File;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public interface Basic {

    /* ----------------- Basic information ----------------- */

    String gameName = "Chatroom76";

    String gameVersion = "alpha 1.0";

    File workingDirectory = new File(System.getProperty("user.home"), gameName);

    /* ----------------- Basic UI ----------------- */

    Queue<UIComponent> componentQueue = new ArrayDeque<>();

    Stack<Screen> previousScreens = new Stack<>();

    List<UIComponent> uiComponents = new CopyOnWriteArrayList<>();

    List<OptionComponent> activeOptions = new ArrayList<>();

    /* ----------------- Positions UI ----------------- */

    int xPos = 0;

}
