package net.bplaced.abzzezz.game.shader;

import net.bplaced.abzzezz.core.util.render.ShaderProgram;
import org.lwjgl.opengl.Display;

public class TextureShader extends ShaderProgram {

    private int sampler = 0;

    public TextureShader() {
        super("basicPosVertex.vert", "textureShader.frag");
    }

    @Override
    public void render() {
        bind();
        bindTexture(sampler);
        setUniform2i("resolution", Display.getWidth(), Display.getHeight());
        setUniform1i("tex", 0);
        super.render();
        unbind();
    }

    public void setSampler(final int sampler) {
        this.sampler = sampler;
    }
}
