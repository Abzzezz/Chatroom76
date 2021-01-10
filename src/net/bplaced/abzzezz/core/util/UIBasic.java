/*
 * @author Roman
 * Last modified: 09.01.21, 22:10 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.core.util;

import net.bplaced.abzzezz.core.util.render.FontUtil;

import java.awt.*;

public interface UIBasic extends Basic {

    /* ----------------- Look and feel ----------------- */

    String defaultFont = "Terminal";

    int textFontSize = 30;

    FontUtil textFont = new FontUtil(defaultFont, "ttf", textFontSize);

    Color textColor = Color.WHITE;

    Color mainColor = new Color(255, 255, 255, 75);

    Color accentColor = Color.GREEN;
}
