package net.bplaced.abzzezz.game.shader;

import net.bplaced.abzzezz.core.util.render.ShaderProgram;
import org.lwjgl.opengl.Display;

public class BackgroundShader extends ShaderProgram {

    public BackgroundShader() {
        super("basicPosVertex.vert", "backgroundShader.frag");
    }

    @Override
    public void render() {
        bind();
        setUniform1f("time", speed);
        setUniform2f("resolution", Display.getWidth(), Display.getHeight());
        super.render();
        unbind();
    }
}
