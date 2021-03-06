/*
 * @author Roman
 * Last modified: 04.01.21, 20:35 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.core.ui.component;

import net.bplaced.abzzezz.core.util.animation.AnimationUtil;
import net.bplaced.abzzezz.core.util.animation.easing.Sine;
import net.bplaced.abzzezz.core.util.io.MouseUtil;
import net.bplaced.abzzezz.core.util.render.RenderUtil;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;


public class ColorChooser implements UIComponent {

    private final int size, lineWidth;
    private final float xPos;
    private final float yPos;
    private final float[] xy = new float[2];
    boolean clicked;
    private AnimationUtil animationUtil;
    private ColorSelectedListener colorSelectedListener;

    public ColorChooser(float xPos, float yPos, int size) {
        this.size = size;
        this.xPos = xPos;
        this.yPos = yPos;
        this.lineWidth = size / 5;
    }

    @Override
    public void refreshPositions() {

    }

    @Override
    public void initComponent() {
        this.animationUtil = new AnimationUtil(new Sine(), 0, 0, size, 1, true);
    }

    @Override
    public void drawComponent() {
        animationUtil.animate();
        if (animationUtil.velocity > animationUtil.min)
            drawColorWheel(xPos, yPos, animationUtil.getInt(), lineWidth);
        RenderUtil.drawCircle(xPos, yPos, size / 10, 2, Color.BLACK);

        if (animationUtil.velocity >= size - 10)
            RenderUtil.drawCircle(xPos + xy[0], yPos + xy[1], 5, 1, new Color(0, 0, 0, 50));
    }

    /**
     * Draw stuff
     */

    private void drawColorWheel(float xPos, float yPos, int radius, int lWidth) {
        RenderUtil.setupGL();
        glEnable(GL_LINE_SMOOTH);
        glLineWidth(lWidth);
        glBegin(GL_LINE_LOOP);
        {
            double theta = (2 * Math.PI / 360.0);
            double tangential_factor = Math.tan(theta);//calculate the tangential factor
            double radial_factor = Math.cos(theta);//calculate the radial factor
            float x = radius;//we start at angle = 0
            float y = 0;
            for (int ii = 0; ii < 360; ii++) {
                float[] hueColor = RenderUtil.getLWJGLColor(RenderUtil.getHue(ii));
                glColor4f(hueColor[0], hueColor[1], hueColor[2], hueColor[3]);
                glVertex2f(x + xPos, y + yPos);

                //calculate the tangential vector
                //remember, the radial vector is (x, y)
                //to get the tangential vector we flip those coordinates and negate one of them

                float tx = -y;
                float ty = x;

                //add the tangential vector

                x += tx * tangential_factor;
                y += ty * tangential_factor;

                //correct using the radial factor
                x *= radial_factor;
                y *= radial_factor;
            }
        }
        glEnd();
        RenderUtil.endGL();
    }

    private boolean colorChooserHovered(float x, float y) {
        return MouseUtil.mouseHovered(xPos + x, yPos + y, 1.205);
    }

    public void setColorSelectedListener(ColorSelectedListener colorSelectedListener) {
        this.colorSelectedListener = colorSelectedListener;
    }

    @Override
    public void keyListener(int keyCode, char keyTyped) {
    }

    @Override
    public void mouseListener(int mouseButton) {
        if (MouseUtil.mouseHovered(xPos, yPos, size / 10) && mouseButton == 0) {
            clicked = !clicked;
            animationUtil.reversed = !clicked;
        }

        if (clicked) {
            double theta = (2 * Math.PI / 360.0);
            double tangential_factor = Math.tan(theta);//calculate the tangential factor
            double radial_factor = Math.cos(theta);//calculate the radial factor
            float x = size;//we start at angle = 0
            float y = 0;
            for (int ii = 0; ii < 360; ii++) {
                if (colorChooserHovered(x, y)) {
                    if (colorSelectedListener != null) colorSelectedListener.onColorSelected(RenderUtil.getHue(ii));
                    xy[0] = x;
                    xy[1] = y;
                }
                float tx = -y;
                float ty = x;
                x += tx * tangential_factor;
                y += ty * tangential_factor;
                x *= radial_factor;
                y *= radial_factor;
            }
        }
    }

    @Override
    public void drawShader() {

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
        return size;
    }

    @Override
    public int getHeight() {
        return size;
    }

    public interface ColorSelectedListener {
        void onColorSelected(Color color);
    }

}
