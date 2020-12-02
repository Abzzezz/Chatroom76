/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 16.06.20, 15:59
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.core.util.render;


import net.bplaced.abzzezz.core.util.DeltaTime;
import net.bplaced.abzzezz.core.util.data.FileUtil;
import net.bplaced.abzzezz.core.util.logging.LogType;
import net.bplaced.abzzezz.core.util.logging.Logger;
import net.bplaced.abzzezz.game.GameMain;
import org.lwjgl.opengl.GL13;

import java.io.IOException;
import java.net.URL;

import static org.lwjgl.opengl.ARBFragmentShader.GL_FRAGMENT_SHADER_ARB;
import static org.lwjgl.opengl.ARBShaderObjects.*;
import static org.lwjgl.opengl.ARBVertexShader.GL_VERTEX_SHADER_ARB;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {

    private final int program;
    protected float speed;

    public ShaderProgram(final String vertexShader, final String fragmentShader) {
        int compiledVertex, compiledFragment;
        this.program = glCreateProgramObjectARB();

        try {
            compiledVertex = compileShader(FileUtil.readShader(GameMain.class.getResource("util/shaders/".concat(vertexShader))), GL_VERTEX_SHADER_ARB);
            compiledFragment = compileShader(FileUtil.readShader(GameMain.class.getResource("util/shaders/".concat(fragmentShader))), GL_FRAGMENT_SHADER_ARB);
        } catch (final IOException e) {
            e.printStackTrace();
            compiledFragment = 0;
            compiledVertex = 0;
        }

        setup(compiledVertex, compiledFragment);
    }

    public ShaderProgram(final URL vertexShader, final URL fragmentShader) {
        int compiledVertex, compiledFragment;
        this.program = glCreateProgramObjectARB();

        try {
            compiledVertex = compileShader(FileUtil.readShader(vertexShader), GL_VERTEX_SHADER_ARB);
            compiledFragment = compileShader(FileUtil.readShader(fragmentShader), GL_FRAGMENT_SHADER_ARB);
        } catch (final IOException e) {
            e.printStackTrace();
            compiledFragment = 0;
            compiledVertex = 0;
        }

        setup(compiledVertex, compiledFragment);
    }

    private void setup(final int compiledVertex, final int compiledFragment) {
        if (program == 0)
            return;

        glAttachObjectARB(program, compiledVertex);
        glAttachObjectARB(program, compiledFragment);

        glLinkProgramARB(program);
        glValidateProgramARB(program);

        if (glGetObjectParameteriARB(program, GL_OBJECT_LINK_STATUS_ARB) == GL_FALSE) {
            Logger.log("Shader-link:" + getLogInfo(program), LogType.ERROR);
        }

        if (glGetObjectParameteriARB(program, GL_OBJECT_VALIDATE_STATUS_ARB) == GL_FALSE) {
            Logger.log("Shader-validation:" + getLogInfo(program), LogType.ERROR);
        }

    }

    protected void render() {
        glBegin(GL_QUADS);
        {
            glVertex2d(-1.0f, 1.0f);
            glVertex2d(1.0f, 1.0f);
            glVertex2d(1.0f, -1.0f);
            glVertex2d(-1.0f, -1.0f);
        }
        glEnd();
        speed += DeltaTime.deltaTime;
    }

    public void unbind() {
        glUseProgramObjectARB(0);
    }

    public void bind() {
        glUseProgramObjectARB(program);
    }

    private String getLogInfo(int obj) {
        return glGetInfoLogARB(obj, glGetObjectParameteriARB(obj, GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }

    private int compileShader(final String shaderSource, final int shaderType) {
        int shader = glCreateShaderObjectARB(shaderType);
        if (shader == 0) return 0;
        glShaderSourceARB(shader, shaderSource);
        glCompileShaderARB(shader);
        return shader;
    }

    protected void bindTexture(final int sampler) {
        if (sampler >= 0 && sampler <= 36) {
            glActiveTexture(GL13.GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, sampler);
        }
    }

    public void setUniform1i(final String uniform, final int value) {
        final int loc = getUniformLocation(uniform);
        if (loc == -1) return;

        glUniform1i(loc, value);
    }

    public void setUniform1f(final String uniform, final float value) {
        final int loc = getUniformLocation(uniform);
        if (loc == -1) return;

        glUniform1f(loc, value);
    }

    public void setUniform2f(final String uniform, final float value0, final float value1) {
        final int loc = getUniformLocation(uniform);
        if (loc == -1) return;
        glUniform2f(loc, value0, value1);
    }

    public void setUniform3i(final String uniform, final int... values) {
        final int loc = getUniformLocation(uniform);
        if (loc == -1) return;

        glUniform3i(loc, values[0], values[1], values[2]);
    }

    public void setUniform4i(final String uniform, final int... values) {
        final int loc = getUniformLocation(uniform);
        if (loc == -1) return;

        glUniform4i(loc, values[0], values[1], values[2], values[3]);
    }

    private int getUniformLocation(final String uniform) {
        if (GameMain.INSTANCE.getShaderHandler().getProgramUniformLocationMap().containsKey(program) &&
                GameMain.INSTANCE.getShaderHandler().getProgramUniformLocationMap().get(program).containsKey(uniform)) {
            return GameMain.INSTANCE.getShaderHandler().getProgramUniformLocationMap().get(program).get(uniform);
        } else {
            final int loc = glGetUniformLocation(program, uniform);
            if (loc == -1) return -1;
            GameMain.INSTANCE.getShaderHandler().putUniformLocation(program, uniform, loc);
            return loc;
        }
    }
}
