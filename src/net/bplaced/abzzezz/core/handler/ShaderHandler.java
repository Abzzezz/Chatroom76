/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.core.handler;

import net.bplaced.abzzezz.core.util.render.ShaderProgram;
import net.bplaced.abzzezz.core.util.clock.UniformCacheTimer;
import org.lwjgl.opengl.ARBShaderObjects;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class ShaderHandler {

    public static final ShaderHandler SHADER_HANDLER = new ShaderHandler();

    private final Map<Integer, Map<String, Integer>> programUniformLocationMap = new HashMap<>();
    private final List<Integer> programList = new ArrayList<>();

    private ShaderProgram backgroundShader;

    public ShaderHandler() {
        final Timer timer = new Timer("Shader-uniform cache timer");
        timer.scheduleAtFixedRate(new UniformCacheTimer(), 1, TimeUnit.SECONDS.toMillis(20));
    }

    public void setupShaders() {

    }

    public void deletePrograms() {
        getProgramList().forEach(ARBShaderObjects::glDeleteObjectARB);
    }

    public ShaderProgram getBackgroundShader() {
        return backgroundShader;
    }

    public void setBackgroundShader(ShaderProgram backgroundShader) {
        this.backgroundShader = backgroundShader;
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

    public List<Integer> getProgramList() {
        return programList;
    }
}
