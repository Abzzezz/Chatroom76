/*
 * @author Roman
 * Last modified: 09.01.21, 19:29 by Roman
 *
 *
 *
 */

/*
 * @author Roman
 * Last modified: 09.01.21, 19:23 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.core.ui.component;

import net.bplaced.abzzezz.core.util.UIBasic;

public interface UIComponent extends UIBasic {

    void draw();

    void setYStack(final int increment);

    int yPos();

    int height();

}
