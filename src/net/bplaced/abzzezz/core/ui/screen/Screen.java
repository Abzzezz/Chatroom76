/*
 * @author Roman
 * Last modified: 09.01.21, 19:23 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.core.ui.screen;

import net.bplaced.abzzezz.core.ui.component.UIComponent;
import net.bplaced.abzzezz.core.util.UIBasic;

public class Screen implements UIBasic {

    public static int yPos = 20;

    public void initialise() {
    }

    public void close() {
    }

    public void draw() {
        this.uiComponents.forEach(UIComponent::draw);
    }

    public void keyPressed(int keyCode, char keyCharacter) {
    }

    public void addUIComponent(final UIComponent uiComponent) {
        this.uiComponents.add(uiComponent);
        yPos += uiComponent.height();
    }

    public void clear() {
        this.uiComponents.clear();
        yPos = 20;
        this.initialise();
    }

    public void addLineBreak(int amount) {
        yPos += amount *= textFontSize;
    }
}
