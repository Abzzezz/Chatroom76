package net.bplaced.abzzezz.engine.ui.uicomponents;

import net.bplaced.abzzezz.engine.utils.RenderUtil;
import net.bplaced.abzzezz.engine.utils.Util;
import net.bplaced.abzzezz.engine.utils.animation.AnimationUtil;
import net.bplaced.abzzezz.engine.utils.animation.easing.Quint;

public class Progressbar implements UIComponent {

    private final AnimationUtil animationUtil;

    private float min, max, current;
    private final float xPos, yPos, width, height;
    private float step;
    private final String title;

    public Progressbar(String title, float xPos, float yPos, float width, float height, float min, float max, float current) {
        this.min = min;
        this.max = max;
        this.current = current;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.title = title;
        this.step = width / max;
        this.animationUtil = new AnimationUtil(Quint.class, current, min, max, step, true, false);
    }

    @Override
    public void initComponent() {

    }

    @Override
    public void drawComponent() {
        RenderUtil.drawQuad(xPos, yPos, width, height, Util.mainColor);
        RenderUtil.drawQuad(xPos, yPos + height / 4, current * step, height / 2, Util.TRANSPARENT_WHITE_75);
        textFont.drawString(title, xPos, yPos - height , textColor);
    }

    @Override
    public void keyListener(int keyCode, char keyTyped) {

    }

    @Override
    public void mouseListener(int mouseButton) {

    }

    @Override
    public void drawShader() {

    }

    public void increment(final float inc) {
        if (current * step < width && current < max)
            current += inc;
    }
}