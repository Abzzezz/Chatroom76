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


import net.bplaced.abzzezz.core.util.logging.LogType;
import net.bplaced.abzzezz.core.util.logging.Logger;
import org.lwjgl.opengl.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import static org.lwjgl.opengl.GL11.*;

public class GLSLShaderUtil {

    public int program, texture = -1;
    private float speed;
    private boolean shader;


    public GLSLShaderUtil(String vertexShader, String fragmentShader) {
        int vertexShader1, fragmentShader1;
        this.program = ARBShaderObjects.glCreateProgramObjectARB();

        try {
            vertexShader1 = createShader(vertexShader, ARBVertexShader.GL_VERTEX_SHADER_ARB);
            fragmentShader1 = createShader(fragmentShader, ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
        } catch (Exception exc) {
            exc.printStackTrace();
            return;
        }
        setup(vertexShader1, fragmentShader1);
    }

    public GLSLShaderUtil(URL vertexShader, URL fragmentShader) {
        int vertexShader1, fragmentShader1;
        this.program = ARBShaderObjects.glCreateProgramObjectARB();

        try {
            vertexShader1 = createShader(getShaderByUrl(vertexShader), ARBVertexShader.GL_VERTEX_SHADER_ARB);
            fragmentShader1 = createShader(getShaderByUrl(fragmentShader), ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
        } catch (Exception exc) {
            exc.printStackTrace();
            return;
        }
        setup(vertexShader1, fragmentShader1);
    }

    private void setup(int vertexShader, int fragmentShader) {
        if (program == 0)
            return;


        ARBShaderObjects.glAttachObjectARB(program, vertexShader);
        ARBShaderObjects.glAttachObjectARB(program, fragmentShader);

        ARBShaderObjects.glLinkProgramARB(program);
        ARBShaderObjects.glValidateProgramARB(program);

        if (ARBShaderObjects.glGetObjectParameteriARB(program,
                ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL_FALSE) {
            Logger.log(getLogInfo(program), LogType.ERROR);
            return;
        }

        if (ARBShaderObjects.glGetObjectParameteriARB(program,
                ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL_FALSE) {
            Logger.log(getLogInfo(program), LogType.ERROR);
            return;
        }


        shader = true;

    }

    private String getShaderByUrl(URL shaderURL) {
        final StringBuilder stringBuilder = new StringBuilder();

        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(shaderURL.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public void draw() {
        if (shader)
            ARBShaderObjects.glUseProgramObjectARB(program);
        speed += 0.01F;

        GL20.glUniform1f(GL20.glGetUniformLocation(program, "time"), this.speed);
        GL20.glUniform2f(GL20.glGetUniformLocation(program, "resolution"), Display.getWidth(), Display.getHeight());

        if (texture != -1) {
            //TODO: Move to method to bind one time
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            glBindTexture(GL11.GL_TEXTURE_2D, texture);
            GL20.glUniform1i(GL20.glGetUniformLocation(program, "tex"), 0);
        }

        glBegin(GL_QUADS);
        {
            glVertex2d(-1.0f, 1.0f);
            glVertex2d(1.0f, 1.0f);
            glVertex2d(1.0f, -1.0f);
            glVertex2d(-1.0f, -1.0f);
        }
        glEnd();


        if (shader) ARBShaderObjects.glUseProgramObjectARB(0);
    }

    private int createShader(String shaderSource, int shaderType) {
        int shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
        if (shader == 0) return 0;
        ARBShaderObjects.glShaderSourceARB(shader, shaderSource);
        ARBShaderObjects.glCompileShaderARB(shader);
        return shader;
    }

    private String getLogInfo(int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }
}
