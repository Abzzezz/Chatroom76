package net.bplaced.abzzezz.game.handler;

import net.bplaced.abzzezz.game.shader.BackgroundShader;
import net.bplaced.abzzezz.game.shader.TextureShader;

public class ShaderHandler {

    private BackgroundShader backgroundShader;
    private TextureShader textureShader;

    public ShaderHandler() {
    }

    public void setupShaders() {
        this.backgroundShader = new BackgroundShader();
        this.textureShader = new TextureShader();

    }

    public TextureShader getTextureShader() {
        return textureShader;
    }

    public BackgroundShader getBackgroundShader() {
        return backgroundShader;
    }
}
