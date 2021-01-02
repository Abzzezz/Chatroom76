package net.bplaced.abzzezz.game.shader;

import net.bplaced.abzzezz.core.util.render.ShaderProgram;
import org.lwjgl.opengl.Display;

public class ExitBackgroundShader extends ShaderProgram {


    private final float width;
    private final float height;

    public ExitBackgroundShader() {
        super("ppVertex.vert", "background2Shader.frag");
        width = Display.getWidth();
        height = Display.getHeight();
    }

    @Override
    public void draw() {
        bind();
        setUniform1f("time", time / 1000);
        setUniform2f("resolution", width, height);
        drawFull();
        super.update();
        unbind();
    }
}
