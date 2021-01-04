/*
 * @author Roman
 * Last modified: 04.01.21, 20:30 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.core.util.render;


import net.bplaced.abzzezz.core.handler.ShaderHandler;
import net.bplaced.abzzezz.core.util.DeltaTime;
import net.bplaced.abzzezz.core.util.data.FileUtil;
import net.bplaced.abzzezz.core.util.logging.LogType;
import net.bplaced.abzzezz.core.util.logging.Logger;
import net.bplaced.abzzezz.game.GameMain;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL13;

import java.io.IOException;
import java.net.URL;

import static org.lwjgl.opengl.ARBFragmentShader.GL_FRAGMENT_SHADER_ARB;
import static org.lwjgl.opengl.ARBShaderObjects.*;
import static org.lwjgl.opengl.ARBVertexShader.GL_VERTEX_SHADER_ARB;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.*;

public abstract class ShaderProgram {

    private final int program;
    private final float screenWidth;
    private final float screenHeight;
    protected float time;

    public ShaderProgram(final String vertexShader, final String fragmentShader) {
        screenWidth = Display.getWidth();
        screenHeight = Display.getHeight();

        int compiledVertex, compiledFragment;
        this.program = glCreateProgramObjectARB();

        try {
            compiledVertex = compileShader(FileUtil.readShader(GameMain.class.getResource("util/shaders/vertex/".concat(vertexShader))), GL_VERTEX_SHADER_ARB);
            compiledFragment = compileShader(FileUtil.readShader(GameMain.class.getResource("util/shaders/fragment/".concat(fragmentShader))), GL_FRAGMENT_SHADER_ARB);
        } catch (final IOException e) {
            e.printStackTrace();
            compiledFragment = 0;
            compiledVertex = 0;
        }

        setup(compiledVertex, compiledFragment);
    }

    public ShaderProgram(final URL vertexShader, final URL fragmentShader) {
        screenWidth = Display.getWidth();
        screenHeight = Display.getHeight();

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
        ShaderHandler.SHADER_HANDLER.getProgramList().add(program);
    }

    public abstract void draw();

    protected void update() {
        time += DeltaTime.deltaTime;
    }

    protected void drawFull() {
        glBegin(GL_QUADS);
        {
            glVertex2d(0, 0);
            glVertex2d(screenWidth, 0);
            glVertex2d(screenWidth, screenHeight);
            glVertex2d(0, screenHeight);
        }
        glEnd();
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

        if (glGetObjectParameteriARB(shader, GL_OBJECT_COMPILE_STATUS_ARB) == GL_FALSE) {
            System.out.println("Error when creating: " + getLogInfo(shader));
            glDeleteObjectARB(shader);
            return shader;
        }
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
        if (ShaderHandler.SHADER_HANDLER.getProgramUniformLocationMap().containsKey(program) &&
                ShaderHandler.SHADER_HANDLER.getProgramUniformLocationMap().get(program).containsKey(uniform)) {
            return ShaderHandler.SHADER_HANDLER.getProgramUniformLocationMap().get(program).get(uniform);
        } else {
            final int loc = glGetUniformLocation(program, uniform);
            if (loc == -1) return -1;
            ShaderHandler.SHADER_HANDLER.putUniformLocation(program, uniform, loc);
            return loc;
        }
    }
}
