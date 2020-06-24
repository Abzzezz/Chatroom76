package net.bplaced.abzzezz.game.dialog;

import ga.abzzezz.util.data.data.DataFormat;
import net.bplaced.abzzezz.engine.EngineCore;

import java.io.File;

public class Dialog {

    private final File configurationFile;
    private final File dialogFile;
    private final File dialogDir;
    private final String dialogName;

    public Dialog(File dialogDir) {
        this.dialogDir = dialogDir;
        this.dialogName = dialogDir.getName();
        this.configurationFile = new File(EngineCore.getInstance().getMainDir(), dialogName + ".txt");
        this.dialogFile = new File(dialogDir, dialogName + getConfig()[0]);
    }

    public String[] getConfig() {
        DataFormat<String> dataFormat = new DataFormat<>(configurationFile);
        return new String[]{dataFormat.decode("FileExtension"), dataFormat.decode("Created"), String.valueOf(dataFormat.decode("LastLine"))};
    }

    public String getDialogName() {
        return dialogName;
    }

    public File getConfigurationFile() {
        return configurationFile;
    }

    public File getDialogFile() {
        return dialogFile;
    }

    public File getDialogDir() {
        return dialogDir;
    }
}
