package net.bplaced.abzzezz.game.shader;

import net.bplaced.abzzezz.core.util.math.MathUtil;
import net.bplaced.abzzezz.core.util.render.ShaderProgram;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

public class BlurShader extends ShaderProgram {

    private final float width, height;
    private final float transformedX, transformedY, transformedWidth, transformedHeight;

    public BlurShader(float xPos, float yPos, final float width, final float height) {
        super("basicPosVertex.vert", "blur.frag");
        this.width = width;
        this.height = height;

        final int posMin = -1, posMax = 1;
        final int screenWidth = Display.getWidth();
        final int screenHeight = Display.getHeight();

        this.transformedWidth = MathUtil.mapFloat(width, 0, screenWidth, 0, 1);
        this.transformedHeight = MathUtil.mapFloat(screenHeight / height, 0, screenHeight, 0, 1);

        this.transformedX = MathUtil.mapFloat(xPos, 0, screenWidth, posMin, posMax) + transformedWidth;
        this.transformedY = -MathUtil.mapFloat(yPos, 0, screenHeight, posMin, posMax) - transformedHeight;
    }

    @Override
    public void draw() {
        bind();
        setUniform2f("pos", transformedX, transformedY);
        setUniform2f("resolution", width, height);
        setUniform1f("tex", 0f);

        glBegin(GL_POLYGON);
        {
            glVertex2d(-transformedWidth, transformedHeight);
            glVertex2d(transformedWidth, transformedHeight);
            glVertex2d(transformedWidth, -transformedHeight);
            glVertex2d(-transformedWidth, -transformedHeight);
        }
        glEnd();

        unbind();
    }
}

