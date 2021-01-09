/*
 * @author Roman
 * Last modified: 09.01.21, 19:33 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.core.handler;

import net.bplaced.abzzezz.core.data.Savable;
import net.bplaced.abzzezz.core.util.Basic;
import net.bplaced.abzzezz.core.util.log.LogType;
import net.bplaced.abzzezz.core.util.log.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SavableHandler implements Basic {

    public static final SavableHandler SAVABLE_HANDLER = new SavableHandler();

    private final List<Savable> savableList = new ArrayList<>();


    public SavableHandler() {
        if (!workingDirectory.exists()) Logger.log("Creating working directory: " + workingDirectory, LogType.INFO);
    }

    public void loadAll() {
        savableList.forEach(Savable::load);
    }

    public void saveAll() {
        savableList.forEach(Savable::save);
    }

    public void addSavable(final Savable... savables) {
        getSavableList().addAll(Arrays.asList(savables));
    }

    public List<Savable> getSavableList() {
        return savableList;
    }
}
