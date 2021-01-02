package net.bplaced.abzzezz.game.shader;

import net.bplaced.abzzezz.core.util.render.ShaderProgram;
import org.lwjgl.opengl.Display;

public class TextureShader extends ShaderProgram {

    private int sampler = -1;
    private float opacity;

    public TextureShader() {
        super("ppVertex.vert", "textureShader.frag");
    }

    @Override
    public void draw() {
        if (sampler != -1) {
            bind();
            bindTexture(sampler);
            setUniform2f("resolution", Display.getWidth(), Display.getHeight());
            setUniform1f("opacity", opacity);
            setUniform1i("tex", 0);
            this.drawFull();
            unbind();
        }
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public void setSampler(final int sampler) {
        this.sampler = sampler;
    }

}
