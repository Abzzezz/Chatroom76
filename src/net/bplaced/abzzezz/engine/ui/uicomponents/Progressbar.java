package net.bplaced.abzzezz.engine.ui.uicomponents;

import net.bplaced.abzzezz.engine.utils.RenderUtil;
import net.bplaced.abzzezz.engine.utils.ColorUtil;
import net.bplaced.abzzezz.engine.utils.animation.AnimationUtil;
import net.bplaced.abzzezz.engine.utils.animation.easing.Quint;

public class Progressbar implements UIComponent {

    private final AnimationUtil animationUtil;

    private float min, max, current;
    private final float xPos, yPos, width, height;
    private float step;
    private String title;

    public Progressbar(String title, float xPos, float yPos, float width, float height, float min, float max, float current) {
        this.min = min;
        this.max = max;
        this.current = current;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.title = title;
        this.animationUtil = new AnimationUtil(Quint.class, current, min, max, step, true, false);
    }

    @Override
    public void initComponent() {
        this.step = width / max;
    }

    @Override
    public void drawComponent() {
        RenderUtil.drawQuad(xPos, yPos, width, height, ColorUtil.MAIN_COLOR);
        RenderUtil.drawQuad(xPos, yPos + height / 4, current * step, height / 2, ColorUtil.MAIN_COLOR);
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

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
        this.step = width / max;
    }

    public float getCurrent() {
        return current;
    }

    public void setCurrent(float current) {
        this.current = current;
    }

    public float getxPos() {
        return xPos;
    }

    public float getyPos() {
        return yPos;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getStep() {
        return step;
    }

    public void setStep(float step) {
        this.step = step;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}