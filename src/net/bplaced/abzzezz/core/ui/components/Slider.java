/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 24.06.20, 15:29
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.core.ui.components;

import net.bplaced.abzzezz.core.util.io.MouseUtil;
import net.bplaced.abzzezz.core.util.math.MathUtil;
import net.bplaced.abzzezz.core.util.render.ColorUtil;
import net.bplaced.abzzezz.core.util.render.RenderUtil;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Slider implements UIComponent {

    private final String text;
    private float min, max, current, xPos, yPos, step;
    private int width, height;
    private SliderListener sliderListener;

    public Slider(String text, float xPos, float yPos, int width, int height, float min, float max, float current) {
        this.text = text;
        this.min = min;
        this.max = max;
        this.current = current;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    public Slider(String text, float xPos, float yPos, int width, int height, float min, float max) {
        this.text = text;
        this.min = min;
        this.max = max;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    private float innerQuadY, stringY;

    @Override
    public void initComponent() {
        this.step = width / max;
        refreshPositions();
    }

    @Override
    public void refreshPositions() {
        innerQuadY = yPos + height / 4;
        stringY = yPos - textFont.getHeight();
    }

    @Override
    public void drawComponent() {
        RenderUtil.drawQuad(xPos, yPos, width, height, ColorUtil.MAIN_COLOR);
        RenderUtil.drawQuad(xPos, yPos + height / 4, current * step, height / 2, ColorUtil.MAIN_COLOR);
        textFont.drawString(text + ":" + Math.round(current), xPos, stringY, textColor);
    }

    @Override
    public void keyListener(int keyCode, char keyTyped) {
        if (keyCode == Keyboard.KEY_LEFT) {
            current -= step;
            if (sliderListener != null) sliderListener.onSliderValueChanged(current);
        } else if (keyCode == Keyboard.KEY_RIGHT) {
            current += step;
            if (sliderListener != null) sliderListener.onSliderValueChanged(current);
        }
        this.current = MathUtil.clamp(current, min, max);
    }

    @Override
    public void mouseListener(int mouseButton) {
        if (MouseUtil.mouseHovered(xPos, yPos, width, height)) {
            current = min + ((Mouse.getX() - xPos) / width) * (max - min);
            if (sliderListener != null) sliderListener.onSliderValueChanged(current);
        }
    }

    public void setSliderListener(net.bplaced.abzzezz.core.ui.components.Slider.SliderListener sliderListener) {
        this.sliderListener = sliderListener;
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

    public void setxPos(float xPos) {
        this.xPos = xPos;
    }

    public float getyPos() {
        return yPos;
    }

    public void setyPos(float yPos) {
        this.yPos = yPos;
    }

    public float getStep() {
        return step;
    }

    public void setStep(float step) {
        this.step = step;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void drawShader() {
    }

    public interface SliderListener {
        void onSliderValueChanged(float value);
    }
}
