package net.bplaced.abzzezz.game.handler;

import net.bplaced.abzzezz.game.shader.BackgroundShader;
import net.bplaced.abzzezz.game.shader.BlurShader;
import net.bplaced.abzzezz.game.shader.TextureShader;
import net.bplaced.abzzezz.game.util.timer.UniformCacheTimer;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class ShaderHandler {

    private final Map<Integer, Map<String, Integer>> programUniformLocationMap = new HashMap<>();

    private BackgroundShader backgroundShader;
    private TextureShader textureShader;

    public ShaderHandler() {
        final Timer timer = new Timer("Shader-uniform cache timer");
        timer.scheduleAtFixedRate(new UniformCacheTimer(), 1, TimeUnit.SECONDS.toMillis(20));
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

    public void putUniformLocation(final int program, final String uniform, final int location) {
        if (!programUniformLocationMap.containsKey(program)) {
            final Map<String, Integer> map = new HashMap<>();
            map.put(uniform, location);
            programUniformLocationMap.put(program, map);
        }
    }

    public Map<Integer, Map<String, Integer>> getProgramUniformLocationMap() {
        return programUniformLocationMap;
    }
}
