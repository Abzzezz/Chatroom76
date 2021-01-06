/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.core.ui.components;

import net.bplaced.abzzezz.core.util.io.MouseUtil;
import net.bplaced.abzzezz.core.util.math.MathUtil;
import net.bplaced.abzzezz.core.util.render.ColorUtil;
import net.bplaced.abzzezz.core.util.render.RenderUtil;
import org.lwjgl.input.Mouse;

public class Slider implements UIComponent {

    private final String sliderTitle;
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
    private String displayText;

    public Slider(String sliderTitle, float xPos, float yPos, int width, int height, float min, float max, float current) {
        this.sliderTitle = sliderTitle;
        this.min = min;
        this.max = max;
        this.current = current;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.displayText = formatValue();
    }

    public Slider(String title, float xPos, float yPos, int width, int height, float min, float max) {
        this.sliderTitle = title;
        this.min = min;
        this.max = max;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.displayText = formatValue();
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
        textFont.drawString(displayText, xPos, stringY, textColor);
    }

    @Override
    public void keyListener(int keyCode, char keyTyped) {
    }

    @Override
    public void mouseListener(int mouseButton) {
        if (MouseUtil.mouseHovered(xPos, yPos, width, height)) {
            current = min + ((Mouse.getX() - xPos) / width) * (max - min);
            if (sliderListener != null) sliderListener.onSliderValueChanged(current);
            displayText = formatValue();
        }
    }

    private String formatValue() {
        return sliderTitle + ":" + MathUtil.NUMBER_FORMAT_TWO_DECIMALS.format(current);
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
