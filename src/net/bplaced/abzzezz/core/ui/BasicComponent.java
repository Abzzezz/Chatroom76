/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.core.ui;

import net.bplaced.abzzezz.core.Core;
import net.bplaced.abzzezz.core.util.render.FontUtil;

import java.awt.*;

import static net.bplaced.abzzezz.core.util.render.ColorUtil.*;

public interface BasicComponent {

    Core OPEN_GL_CORE_INSTANCE = Core.getInstance();
    FontUtil textFont = new FontUtil(TEXT_FONT, 20);
    FontUtil bigFont = new FontUtil(TEXT_FONT, 60);

    Color textColor = TEXT_COLOR;
    Color backgroundColor = BACKGROUND_COLOR;
    Color mainColor = MAIN_COLOR;


}
