package net.bplaced.abzzezz.game.shader;

import net.bplaced.abzzezz.core.util.render.ShaderProgram;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

public class BackgroundShader extends ShaderProgram {


    final float width = 1.0f;//MathUtil.mapFloat(600, 0, Display.getWidth(), 0, 1);
    final float height = 1.0f;// MathUtil.mapFloat(600, 0, Display.getHeight(), 0, 1);

    public BackgroundShader() {
        super("basicPosVertex.vert", "backgroundShader.frag");
    }

    @Override
    public void draw() {
        bind();
        // setUniform2f("pos", MathUtil.mapFloat(offset, 0, Display.getWidth(), -1, 1), 0);
        setUniform1f("time", speed / 1000);
        setUniform2f("resolution", Display.getWidth(), Display.getHeight());

        glBegin(GL_POLYGON);
        {
            glVertex2d(-width, height);
            glVertex2d(width, height);
            glVertex2d(width, -height);
            glVertex2d(-width, -height);
        }
        glEnd();
        super.update();
        unbind();
    }
}
