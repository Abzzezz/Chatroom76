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
import net.bplaced.abzzezz.core.util.animation.AnimationUtil;
import net.bplaced.abzzezz.core.util.animation.easing.Quint;

public class Screen implements UIBasic {

    public static int currentY = 20;
    private final AnimationUtil slide = new AnimationUtil(new Quint(), 0, 0, currentY, 1, false);

    public void initialise() {
    }

    public void close() {
    }

    public void draw() {
        this.slide.animate();
        if (slide.time < slide.max)
            currentY = slide.getInt();

        this.uiComponents.forEach(UIComponent::draw);
    }

    public void keyPressed(int keyCode, char keyCharacter) {
    }

    public void addUIComponent(final UIComponent uiComponent) {
        this.uiComponents.add(uiComponent);
        final int yPos = currentY += uiComponent.height();
        setSlide(yPos);
    }

    public void clear() {
        this.uiComponents.clear();
        setSlide(20);
        this.initialise();
    }

    public void addLineBreak(int amount) {
        setSlide(currentY + amount * textFontSize);
    }

    private void setSlide(int max) {
        slide.setMax(max);
    }
}
