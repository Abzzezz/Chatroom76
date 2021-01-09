/*
 * @author Roman
 * Last modified: 09.01.21, 19:33 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.core.data;

import net.bplaced.abzzezz.core.util.Basic;

import java.io.File;

public abstract class Savable implements Basic {

    private final File file;

    public Savable(final String fileName, final String extension) {
        this.file = new File(workingDirectory, fileName.concat(".").concat(extension));
    }

    public abstract void save();

    public abstract void load();

    @Override
    public String toString() {
        return "Savable{" +
                "file=" + file +
                '}';
    }

    public File getFile() {
        return file;
    }
}
