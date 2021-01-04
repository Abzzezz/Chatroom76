/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
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
