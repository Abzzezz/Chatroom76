/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 29.04.20, 15:03
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.engine.file;

import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private final List<CustomFile> files;

    public FileManager() {
        this.files = new ArrayList<>();
    }

    public void load() {
        files.forEach(CustomFile::read);
    }

    public void save() {
        files.forEach(CustomFile::write);
    }

    public List<CustomFile> getFiles() {
        return files;
    }
}
