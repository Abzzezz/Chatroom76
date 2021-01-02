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
    private final float xPos;
    private final float yPos;
    private final int width;
    private final int height;
    private float min;
    private float max;
    private float current;
    private float step;
    private SliderListener sliderListener;
    private float stringY;

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

    @Override
    public void initComponent() {
        this.step = width / max;
        refreshPositions();
    }

    @Override
    public void refreshPositions() {
        stringY = yPos - textFont.getHeight();
    }

    @Override
    public void drawComponent() {
        RenderUtil.drawQuad(xPos, yPos, width, height, ColorUtil.MAIN_COLOR);
        RenderUtil.drawQuad(xPos, yPos, current * step, height, ColorUtil.MAIN_COLOR);
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

    @Override
    public void drawShader() {
    }

    public interface SliderListener {
        void onSliderValueChanged(float value);
    }
}
