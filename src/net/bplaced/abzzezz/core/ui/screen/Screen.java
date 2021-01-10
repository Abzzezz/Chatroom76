/*
 * @author Roman
 * Last modified: 09.01.21, 19:23 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.core.ui.screen;

import net.bplaced.abzzezz.core.ui.component.UIComponent;
import net.bplaced.abzzezz.core.ui.component.components.OptionComponent;
import net.bplaced.abzzezz.core.util.UIBasic;

public class Screen implements UIBasic {

    public static int currentY = 20;
    private final int minY = 20;

    public void initialise() {
    }

    public void close() {
    }


    public void update() {
    }

    public void keyPressed(int keyCode, char keyCharacter) {
    }


    /* ----------------- UI Components ----------------- */

    public void addUIComponent(final UIComponent uiComponent) {
        this.uiComponents.add(uiComponent);
        currentY += uiComponent.height();
    }

    public void addOption(final OptionComponent optionComponent) {
        this.activeOptions.add(optionComponent);
        currentY += optionComponent.height();
    }

    public void clear() {
        this.uiComponents.clear();
        this.activeOptions.clear();
        currentY = minY;
        this.initialise();
    }

    public void addLineBreak(int amount) {
        currentY += amount * textFontSize;
    }

}
