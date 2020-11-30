package net.bplaced.abzzezz.game.util.timer;

import net.bplaced.abzzezz.core.util.logging.LogType;
import net.bplaced.abzzezz.core.util.logging.Logger;
import net.bplaced.abzzezz.game.GameMain;

import java.util.TimerTask;

public class UniformCacheTimer extends TimerTask {
    @Override
    public void run() {
        GameMain.INSTANCE.getShaderHandler().getProgramUniformLocationMap().clear();
        Logger.log("Clearing uniform cache", LogType.INFO);
    }
}
