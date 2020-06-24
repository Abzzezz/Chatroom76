package net.bplaced.abzzezz.game.files;

import ga.abzzezz.util.data.data.DataFormat;
import ga.abzzezz.util.data.data.DataObject;
import net.bplaced.abzzezz.engine.file.CustomFile;
import net.bplaced.abzzezz.game.MainClass;

public class SettingsFile extends CustomFile {

    /**
     * Simple custom file. Use as parent to make own files and load them automatically with the engine
     *
     * @param
     */
    public SettingsFile() {
        super("Settings.data");
    }

    @Override
    public void read() {
        if (!(thisFile.length() == 0)) {
            DataFormat dataFormat = new DataFormat(thisFile);
            MainClass.getInstance().getSettingsHandler().setVolume((float) dataFormat.decode("volume"));
            //  MainClass.getInstance().getSettingsHandler().setResolution((Integer[]) dataFormat.decodeToArray("resolution"));
        }
        super.read();
    }

    @Override
    public void write() {
        DataObject dataObject = new DataObject();
        dataObject.addObject("volume", MainClass.getInstance().getSettingsHandler().getVolume());
        dataObject.addArray("resolution", MainClass.getInstance().getSettingsHandler().getResolution());
        DataFormat.formatData(dataObject, thisFile, false, false);
        super.write();
    }
}
