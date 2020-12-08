package net.bplaced.abzzezz.game.shader;

import net.bplaced.abzzezz.core.util.render.ShaderProgram;

import static org.lwjgl.opengl.GL11.*;

public class BlurShader extends ShaderProgram {

    private final float width, height;
    private final float xPos, yPos;

    public BlurShader(float xPos, float yPos, final float width, final float height) {
        super("basicPosVertex.vert", "blur.frag");
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw() {
        bind();
        setUniform2f("resolution", width, height);
        setUniform1i("tex", 0);

        glBegin(GL_QUADS);
        {
            glVertex2d(xPos, yPos);
            glVertex2d(xPos + width, yPos);
            glVertex2d(xPos + width, yPos + height);
            glVertex2d(xPos, yPos + height);
        }
        glEnd();
        unbind();
    }
}

