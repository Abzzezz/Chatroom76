package net.bplaced.abzzezz.core.ui.components;

import net.bplaced.abzzezz.core.util.animation.AnimationUtil;
import net.bplaced.abzzezz.core.util.animation.easing.Quint;
import net.bplaced.abzzezz.core.util.render.ColorUtil;
import net.bplaced.abzzezz.core.util.render.RenderUtil;

public class Progressbar implements UIComponent {

    private final AnimationUtil animationUtil;
    private final float xPos, yPos;
    private int width, height;
    private float min, max, current;
    private float step;
    private String title;

    public Progressbar(String title, float xPos, float yPos, int width, int height, float min, float max, float current) {
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

    private float stringY, quadY;

    @Override
    public void initComponent() {
        this.step = width / max;
        refreshPositions();
    }

    @Override
    public void refreshPositions() {
        quadY = yPos + height / 4;
        stringY = yPos - height;
    }

    @Override
    public void drawComponent() {
        RenderUtil.drawQuad(xPos, yPos, width, height, ColorUtil.MAIN_COLOR);
        RenderUtil.drawQuad(xPos, quadY, current * step, height / 2, ColorUtil.MAIN_COLOR);
        textFont.drawString(title, xPos, stringY, textColor);
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


    @Override
    public float getXPos() {
        return xPos;
    }

    @Override
    public float getYPos() {
        return yPos;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
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