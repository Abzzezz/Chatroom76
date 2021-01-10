/*
 * @author Roman
 * Last modified: 10.01.21, 21:58 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.shader;

import net.bplaced.abzzezz.core.util.render.ShaderProgram;
import org.lwjgl.opengl.Display;

public class CRTShader extends ShaderProgram {

    public CRTShader() {
        super("ppVertex.vert", "crtShader.frag");
    }

    @Override
    public void draw() {
        bind();
        setUniform2f("resolution", Display.getWidth(), Display.getHeight());
        setUniform1f("time", time);
        setUniform1f("alpha", 0.3f);
        drawFull();
        this.update();
        unbind();
    }
}

