/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.game.shader;

import net.bplaced.abzzezz.core.util.render.ShaderProgram;
import net.bplaced.abzzezz.game.handler.Settings;
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
        setUniform1f("alpha", Settings.crtAlpha);
        drawFull();
        this.update();
        unbind();
    }
}

