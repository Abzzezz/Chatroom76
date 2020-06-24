/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 29.04.20, 15:01
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.engine.file;

import ga.abzzezz.util.logging.Logger;
import net.bplaced.abzzezz.engine.EngineCore;

import java.io.File;
import java.io.IOException;

public class CustomFile {

    protected File thisFile;

    /**
     * Simple custom file. Use as parent to make own files and load them automatically with the engine
     *
     * @param fileName
     */
    public CustomFile(String fileName) {
        this.thisFile = new File(EngineCore.getInstance().getMainDir(), fileName);
        if (!thisFile.exists()) {
            Logger.log("Creating new file", Logger.LogType.INFO);
            try {
                thisFile.createNewFile();
            } catch (IOException e) {
                Logger.log("Creating new file", Logger.LogType.ERROR);
                e.printStackTrace();
            }
        }
    }


    public void write() {
    }

    public void read() {
    }


}
