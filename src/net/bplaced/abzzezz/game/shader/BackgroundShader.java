package net.bplaced.abzzezz.game.shader;

import net.bplaced.abzzezz.core.util.math.MathUtil;
import net.bplaced.abzzezz.core.util.render.ShaderProgram;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

public class BackgroundShader extends ShaderProgram {

    public BackgroundShader() {
        super("basicPosVertex.vert", "backgroundShader.frag");
    }

    @Override
    public void update() {
        bind();
        int offset = 300;

        setUniform2f("pos", MathUtil.mapFloat(offset, 0, Display.getWidth(), -1, 1), 0);
        setUniform1f("time", speed / 1000);
        setUniform2f("resolution", Display.getWidth(), Display.getHeight());


        int x = 600;

        float width = MathUtil.mapFloat(x, 0, Display.getWidth(), 0, 1);
        float height = MathUtil.mapFloat(x, 0, Display.getWidth(), 0, 1);

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
