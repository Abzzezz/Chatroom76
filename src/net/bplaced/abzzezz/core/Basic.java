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

import net.bplaced.abzzezz.core.ui.BasicScreen;
import net.bplaced.abzzezz.core.util.render.FontUtil;

import java.awt.*;
import java.util.Stack;

import static net.bplaced.abzzezz.core.util.render.ColorUtil.*;

public interface Basic {


    Stack<BasicScreen> screens = new Stack<>();

    Core OPEN_GL_CORE_INSTANCE = Core.getInstance();
    FontUtil textFont = new FontUtil(TEXT_FONT, 25);
    FontUtil bigFont = new FontUtil(TEXT_FONT, 60);

    Color textColor = TEXT_COLOR;
    Color backgroundColor = BACKGROUND_COLOR;
    Color mainColor = MAIN_COLOR;


}
