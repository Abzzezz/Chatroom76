/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 29.04.20, 15:03
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.core.handler;

import net.bplaced.abzzezz.core.file.BasicFile;

import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private final List<BasicFile> files;

    public FileHandler() {
        this.files = new ArrayList<>();
    }

    public void load() {
        files.forEach(BasicFile::read);
    }

    public void save() {
        files.forEach(BasicFile::write);
    }

    public List<BasicFile> getFiles() {
        return files;
    }
}
