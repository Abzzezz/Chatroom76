/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.game.util.timer;

import net.bplaced.abzzezz.core.handler.ShaderHandler;
import net.bplaced.abzzezz.core.util.logging.LogType;
import net.bplaced.abzzezz.core.util.logging.Logger;

import java.util.TimerTask;

public class UniformCacheTimer extends TimerTask {
    @Override
    public void run() {
        ShaderHandler.SHADER_HANDLER.getProgramUniformLocationMap().clear();
        Logger.log("Clearing uniform cache", LogType.INFO);
    }
}
