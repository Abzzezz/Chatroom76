package net.bplaced.abzzezz.game.shader;

import net.bplaced.abzzezz.core.util.render.ShaderProgram;
import org.lwjgl.opengl.Display;

public class BackgroundShader extends ShaderProgram {


    private final float width;//MathUtil.mapFloat(600, 0, Display.getWidth(), 0, 1);
    private final float height;// MathUtil.mapFloat(600, 0, Display.getHeight(), 0, 1);

    public BackgroundShader() {
        super("basicPosVertex.vert", "backgroundShader.frag");
        width = Display.getWidth();
        height = Display.getHeight();
    }

    @Override
    public void draw() {
        bind();
        setUniform1f("time", speed / 1000);
        setUniform2f("resolution", width, height);

        drawFull();
        super.update();
        unbind();
    }
}
