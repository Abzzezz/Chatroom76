/*
 * @author Roman
 * Last modified: 06.01.21, 21:59 by Roman
 *
 *
 *
 */

/*
 * @author Roman
 * Last modified: 06.01.21, 21:59 by Roman
 *
 *
 *
 */

/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.core;

import net.bplaced.abzzezz.core.ui.components.UIComponent;
import net.bplaced.abzzezz.core.ui.screen.BasicScreen;
import net.bplaced.abzzezz.core.util.render.FontUtil;
import net.bplaced.abzzezz.game.ui.component.Option;

import java.awt.*;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Basic Interface. This interface contains information, which can be given to other interfaces or classes.
 */
public interface Basic {
    /**
     * Direct link to the main core
     */
    Core OPEN_GL_CORE_INSTANCE = Core.getInstance();

    /**
     * Stack of all screens which were shown before
     */
    Stack<BasicScreen> screenStack = new Stack<>();
    /**
     * List of all ui components currently active
     */
    List<UIComponent> uiComponents = new CopyOnWriteArrayList<>();
    /**
     * List of all options currently shown
     */
    List<Option> options = new CopyOnWriteArrayList<>();
    /**
     * Text font that shall be used for all text
     */
    FontUtil textFont = new FontUtil("Terminal", 25);
    /**
     * Font for big text
     */
    FontUtil bigFont = new FontUtil("Terminal", 60);
    /**
     * Color for all text
     */
    Color textColor = Color.WHITE;
    /**
     * Background color
     */
    Color backgroundColor = Color.WHITE;
    /**
     * Main color, used for colored rectangle, etc.
     */
    Color mainColor = new Color(255, 255, 255, 75);
    /**
     * Accent color
     */
    Color accentColor = Color.GREEN;
}
